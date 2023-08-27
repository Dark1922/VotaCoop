package com.br.VotaCoop.domain.service;

import com.br.VotaCoop.api.dto.Input.SessaoVotacaoInput;
import com.br.VotaCoop.api.dto.ResultadoVotacaoDTO;
import com.br.VotaCoop.api.dto.SessaoVotacaoDTO;
import com.br.VotaCoop.domain.model.SessaoVotacao;


public interface SessaoVotacaoService {
     SessaoVotacaoDTO findById(Long idSessaoVotacao);
     SessaoVotacao iniciarSessao(SessaoVotacaoInput input);
     boolean isSessaoVotacaoAberta(Long sessaoId);
     SessaoVotacao buscarOuFalhar(Long id);
     ResultadoVotacaoDTO getResultadoVotacao(Long idSessaoVotacao);
}
