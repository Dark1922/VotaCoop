package com.br.VotaCoop.domain.exception;

import jakarta.persistence.EntityNotFoundException;

import java.io.Serial;

public class PautaNotFoundException extends NegocioException {

    @Serial
    private static final long serialVersionUID = 1L;

	public PautaNotFoundException(String mensagem) {
            super(mensagem);
        }
}
