package com.br.VotaCoop.domain.exception;


import java.io.Serial;

public class SessaoVotacaoNotFoundException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

	public SessaoVotacaoNotFoundException(String mensagem) {
            super(mensagem);
        }
}
