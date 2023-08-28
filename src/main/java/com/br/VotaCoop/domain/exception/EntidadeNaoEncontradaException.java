package com.br.VotaCoop.domain.exception;

import java.io.Serial;

public class EntidadeNaoEncontradaException extends NegocioException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String msg) {
        super(msg);
    }

}
