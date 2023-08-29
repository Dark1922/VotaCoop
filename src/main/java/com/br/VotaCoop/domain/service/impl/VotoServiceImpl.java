package com.br.VotaCoop.domain.service.impl;

import com.br.VotaCoop.api.assembler.VotoModelAssembler;
import com.br.VotaCoop.api.dto.Input.VotoInput;
import com.br.VotaCoop.api.dto.VotoDTO;
import com.br.VotaCoop.domain.exception.SessaoVotacaoEncerradaException;
import com.br.VotaCoop.domain.exception.ValidationException;
import com.br.VotaCoop.domain.exception.VotoNotFoundException;
import com.br.VotaCoop.domain.model.Associado;
import com.br.VotaCoop.domain.model.SessaoVotacao;
import com.br.VotaCoop.domain.model.Voto;
import com.br.VotaCoop.domain.repository.VotoRepository;
import com.br.VotaCoop.domain.service.AssociadoService;
import com.br.VotaCoop.domain.service.SessaoVotacaoService;
import com.br.VotaCoop.domain.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotoServiceImpl implements VotoService {

    private static final String MSG_VOTO_NAO_ENCONTRADO = "Não existe um cadastro de Voto com código %d";
    private static final String MSG_VALIDACAO_VOTO= "O associado já votou nesta sessão.";
    private static final String MSG_SESSAO_VOTACAO_ENCERRADA = "A sessão de votação está fechada ou encerrada.";

    private VotoRepository votoRepository;
    private VotoModelAssembler votoModelAssembler;
    private AssociadoService associadoService;
    @Autowired
    @Lazy
    private SessaoVotacaoService sessaoVotacaoService;

    @Autowired
    public VotoServiceImpl(VotoRepository votoRepository,
                           VotoModelAssembler votoModelAssembler,
                           AssociadoService associadoService) {
        this.votoRepository = votoRepository;
        this.votoModelAssembler = votoModelAssembler;
        this.associadoService = associadoService;
    }


    @Override
    public VotoDTO findById(Long idVoto) {
        return votoModelAssembler.toModel(buscarOuFalhar(idVoto));
    }

    @Override
    public VotoDTO saveVoto(VotoInput votoInput) {

        if (!sessaoVotacaoService.isSessaoVotacaoAberta(votoInput.getIdSessaoVotacao())) {
            throw new SessaoVotacaoEncerradaException(MSG_SESSAO_VOTACAO_ENCERRADA);
        }

        if (votoRepository.existsBySessaoVotacaoIdAndAssociadoId(votoInput.getIdSessaoVotacao(), votoInput.getIdAssociado())) {
            throw new ValidationException(MSG_VALIDACAO_VOTO);
        }

        Voto voto = new Voto();

        SessaoVotacao sessaoVotacao =  sessaoVotacaoService.buscarOuFalhar(votoInput.getIdSessaoVotacao());
        Associado associado = associadoService.buscarOuFalhar(votoInput.getIdAssociado());

        voto.setSessaoVotacao(sessaoVotacao);
        voto.setAssociado(associado);
        voto.setDataVoto(LocalDateTime.now());
        voto.setValor(votoInput.getValor());

            return votoModelAssembler.toModel(votoRepository.save(voto));
        }



    public Voto buscarOuFalhar(Long id) {
        return votoRepository.findById(id)
                .orElseThrow(() -> new VotoNotFoundException(String.format(MSG_VOTO_NAO_ENCONTRADO, id)));
    }

    public void setSessaoVotacaoService(SessaoVotacaoService sessaoVotacaoService) {
        this.sessaoVotacaoService = sessaoVotacaoService;
    }

}
