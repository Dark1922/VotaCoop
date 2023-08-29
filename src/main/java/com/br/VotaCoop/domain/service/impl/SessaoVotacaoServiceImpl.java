package com.br.VotaCoop.domain.service.impl;

import com.br.VotaCoop.api.assembler.SessaoVotacaoInputDissasembler;
import com.br.VotaCoop.api.assembler.SessaoVotacaoModelAssembler;
import com.br.VotaCoop.api.dto.Input.SessaoVotacaoInput;
import com.br.VotaCoop.api.dto.ResultadoVotacaoDTO;
import com.br.VotaCoop.api.dto.SessaoVotacaoDTO;
import com.br.VotaCoop.core.rabbitmq.MessageProducer;
import com.br.VotaCoop.domain.exception.NegocioException;
import com.br.VotaCoop.domain.exception.ValidationException;
import com.br.VotaCoop.domain.exception.VotoNotFoundException;
import com.br.VotaCoop.domain.model.Pauta;
import com.br.VotaCoop.domain.model.SessaoVotacao;
import com.br.VotaCoop.domain.repository.SessaoVotacaoRepository;
import com.br.VotaCoop.domain.service.PautaService;
import com.br.VotaCoop.domain.service.SessaoVotacaoService;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoVotacaoServiceImpl implements SessaoVotacaoService {

    private static final String MSG_VOTO_NAO_ENCONTRADO = "Não existe um cadastro de Sessão Votação com código %d";
    private static final String ABERTA = "aberta";
    private static final String FECHADA = "fechada";
    private static final String SESSAO_ENCERRADA = "A sessão de votação já foi encerrada.";
    private static final String MSG_CONSULTA_SEM_REGISTRO = "Não há resultados de votação para esta pauta";
    private static final String MSG_JA_EXISTE_SESSAO = "Já existe uma sessão de votação aberta para esta pauta.";


    private SessaoVotacaoRepository sessaoVotacaoRepository;
    private SessaoVotacaoInputDissasembler sessaoVotacaoInputDissasembler;
    private SessaoVotacaoModelAssembler sessaoVotacaoModelAssembler;
    private PautaService pautaService;
    private MessageProducer messageProducer;

    public SessaoVotacaoServiceImpl(SessaoVotacaoRepository sessaoVotacaoRepository,
                                    SessaoVotacaoInputDissasembler sessaoVotacaoInputDissasembler,
                                    SessaoVotacaoModelAssembler sessaoVotacaoModelAssembler,
                                    PautaService pautaService,
                                    MessageProducer messageProducer) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.sessaoVotacaoInputDissasembler = sessaoVotacaoInputDissasembler;
        this.sessaoVotacaoModelAssembler = sessaoVotacaoModelAssembler;
        this.pautaService = pautaService;
        this.messageProducer = messageProducer;
    }

    @Override
    public SessaoVotacaoDTO findById(Long idSessaoVotacao) {
        return sessaoVotacaoModelAssembler.toModel(buscarOuFalhar(idSessaoVotacao));
    }

    @Override
    public SessaoVotacao iniciarSessao(SessaoVotacaoInput input) {

        // Verifica se a pauta existe
        Pauta pautaExistente = pautaService.buscarOuFalhar(input.getIdPauta());

        // Verifica se já existe uma sessão de votação aberta para a pauta
        Optional<SessaoVotacao> sessaoExistente = Optional.ofNullable(sessaoVotacaoRepository.findByPautaIdAndStatus(input.getIdPauta(), ABERTA));
        if (sessaoExistente.isPresent()) {
            throw new ValidationException(MSG_JA_EXISTE_SESSAO);
        }

        // Cria uma nova instância de SessaoVotacao
        SessaoVotacao novaSessao = new SessaoVotacao();
        novaSessao.setPauta(pautaExistente);
        novaSessao.setDataInicio(LocalDateTime.now());
        novaSessao.setStatus(ABERTA);
        novaSessao.setDuracao(input.getDuracao() == null ? 1 : input.getDuracao());

        return sessaoVotacaoRepository.save(novaSessao);
    }


    @Override
    public boolean isSessaoVotacaoAberta(Long sessaoId) {
        SessaoVotacao sessaoVotacao = buscarOuFalhar(sessaoId);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sessionEndTime = sessaoVotacao.getDataInicio().plusMinutes(sessaoVotacao.getDuracao());

        if (now.isAfter(sessionEndTime)) {
            throw new ValidationException(SESSAO_ENCERRADA);
        }

        return ABERTA.equals(sessaoVotacao.getStatus());
    }

    @Transactional
    public void encerrarSessao(Long sessaoId) throws Exception {
        SessaoVotacao sessao = buscarOuFalhar(sessaoId);

        if (sessao.getDataInicio().plusMinutes(sessao.getDuracao()).isBefore(LocalDateTime.now())) {
            sessao.setStatus(FECHADA);
            sessaoVotacaoRepository.save(sessao);
            messageProducer.sendMessage(getResultadoVotacao(sessaoId));
        }
    }

    /*Verifica a cada 1 minuto todas sessão de votação em aberta se já passou o tempo de duração para colocar como fechada */
    @Scheduled(cron = "0 */1 * * * *")
    public void encerrarSessoesExpiradas() throws Exception {
        List<SessaoVotacao> sessoesAtivas = sessaoVotacaoRepository.findByStatusContaining(ABERTA);
        for (SessaoVotacao sessao : sessoesAtivas) {
            if (sessao.getDataInicio().plusMinutes(sessao.getDuracao()).isBefore(LocalDateTime.now())) {
                encerrarSessao(sessao.getId());
            }
        }
    }

    public SessaoVotacao buscarOuFalhar(Long id) {
        return sessaoVotacaoRepository.findById(id)
                .orElseThrow(() -> new VotoNotFoundException(String.format(MSG_VOTO_NAO_ENCONTRADO, id)));
    }

    public ResultadoVotacaoDTO getResultadoVotacao(Long idSessaoVotacao) {
        Tuple tupleResultado = sessaoVotacaoRepository.getResultadoVotacao(idSessaoVotacao);
        if (tupleResultado != null) {
            return convertToDto(tupleResultado);
        }
        throw new NegocioException(MSG_CONSULTA_SEM_REGISTRO);
    }

    public ResultadoVotacaoDTO convertToDto(Tuple tuple) {
        return ResultadoVotacaoDTO.builder()
                .nomePauta(getStringValue(tuple, "nomePauta"))
                .totalVotosSim(getLongValue(tuple, "totalVotosSim"))
                .totalVotosNao(getLongValue(tuple, "totalVotosNao"))
                .totalVotos(getLongValue(tuple, "totalVotos"))
                .resultado(getStringValue(tuple, "resultado"))
                .build();
    }

    private String getStringValue(Tuple tuple, String key) {
        return Optional.ofNullable(tuple.get(key, String.class)).orElse("");
    }

    private Long getLongValue(Tuple tuple, String key) {
        return Optional.ofNullable(tuple.get(key, Long.class)).orElse(0L);
    }

}
