package com.br.VotaCoop.domain.exception;

import jakarta.persistence.EntityNotFoundException;

import java.io.Serial;

public class AssociadoNotFoundException extends EntityNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

	public AssociadoNotFoundException(String mensagem) {
            super(mensagem);
        }
}
