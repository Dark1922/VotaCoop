package com.br.VotaCoop.domain.exception;

import jakarta.persistence.EntityNotFoundException;

import java.io.Serial;

public class SessaoVotacaoNotFoundException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

	public SessaoVotacaoNotFoundException(String mensagem) {
            super(mensagem);
        }
}
