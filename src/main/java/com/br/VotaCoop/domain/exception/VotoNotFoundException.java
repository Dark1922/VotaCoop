package com.br.VotaCoop.domain.exception;

import jakarta.persistence.EntityNotFoundException;

import java.io.Serial;

public class VotoNotFoundException extends EntityNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

	public VotoNotFoundException(String mensagem) {
            super(mensagem);
        }
}
