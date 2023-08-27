package com.br.VotaCoop.domain.exception;

import jakarta.persistence.EntityNotFoundException;

import java.io.Serial;

public class SessaoVotacaoEncerradaException extends EntityNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

	public SessaoVotacaoEncerradaException(String mensagem) {
            super(mensagem);
        }
}
