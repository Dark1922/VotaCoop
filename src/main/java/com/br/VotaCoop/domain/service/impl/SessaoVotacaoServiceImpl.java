package com.br.VotaCoop.domain.service.impl;

import com.br.VotaCoop.api.assembler.SessaoVotacaoInputDissasembler;
import com.br.VotaCoop.api.assembler.SessaoVotacaoModelAssembler;
import com.br.VotaCoop.api.dto.Input.SessaoVotacaoInput;
import com.br.VotaCoop.api.dto.SessaoVotacaoDTO;
import com.br.VotaCoop.domain.exception.SessaoVotacaoNotFoundException;
import com.br.VotaCoop.domain.exception.VotoNotFoundException;
import com.br.VotaCoop.domain.model.Pauta;
import com.br.VotaCoop.domain.model.SessaoVotacao;
import com.br.VotaCoop.domain.repository.SessaoVotacaoRepository;
import com.br.VotaCoop.domain.service.PautaService;
import com.br.VotaCoop.domain.service.SessaoVotacaoService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoVotacaoServiceImpl implements SessaoVotacaoService {

    private static final String MSG_VOTO_NAO_ENCONTRADO = "Não existe um cadastro de Sessão Votação com código %d";
    private static final String ABERTA = "aberta";
    private static final String FECHADA = "fechada";

    private SessaoVotacaoRepository sessaoVotacaoRepository;
    private SessaoVotacaoInputDissasembler sessaoVotacaoInputDissasembler;
    private SessaoVotacaoModelAssembler sessaoVotacaoModelAssembler;
    private PautaService pautaService;


    public SessaoVotacaoServiceImpl(SessaoVotacaoRepository sessaoVotacaoRepository,
                                    SessaoVotacaoInputDissasembler sessaoVotacaoInputDissasembler,
                                    SessaoVotacaoModelAssembler sessaoVotacaoModelAssembler,
                                    PautaService pautaService) {
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.sessaoVotacaoInputDissasembler = sessaoVotacaoInputDissasembler;
        this.sessaoVotacaoModelAssembler = sessaoVotacaoModelAssembler;
        this.pautaService = pautaService;
    }

    @Override
    public SessaoVotacaoDTO findById(Long idSessaoVotacao) {
        return sessaoVotacaoModelAssembler.toModel(buscarOuFalhar(idSessaoVotacao));
    }

    @Override
    public SessaoVotacao iniciarSessao(SessaoVotacaoInput input) {
        Pauta pautaExistente = pautaService.buscarOuFalhar(input.getIdPauta());
        SessaoVotacao novaSessao = sessaoVotacaoInputDissasembler.toDomainObject(input);

        novaSessao.setPauta(pautaExistente);
        novaSessao.setDataInicio(LocalDateTime.now());
        novaSessao.setStatus(ABERTA);

        return sessaoVotacaoRepository.save(novaSessao);
    }

    @Override
    public boolean isSessaoVotacaoAberta(Long sessaoId) {
        SessaoVotacao sessaoVotacao = buscarOuFalhar(sessaoId);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sessionEndTime = sessaoVotacao.getDataInicio().plusMinutes(sessaoVotacao.getDuracao());

        if (now.isAfter(sessionEndTime)) {
            encerrarSessao(sessaoVotacao.getId());
            throw new SessaoVotacaoNotFoundException("A sessão de votação já foi encerrada.");
        }

        return ABERTA.equals(sessaoVotacao.getStatus());
    }

    @Transactional
    public void encerrarSessao(Long sessaoId) {
        SessaoVotacao sessao = buscarOuFalhar(sessaoId);

        if (sessao.getDataInicio().plusMinutes(sessao.getDuracao()).isBefore(LocalDateTime.now())) {
            sessao.setStatus(FECHADA);
            sessaoVotacaoRepository.save(sessao);
        }
    }
    @Scheduled(cron = "0 */1 * * * *")
    public void encerrarSessoesExpiradas() {
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
}
