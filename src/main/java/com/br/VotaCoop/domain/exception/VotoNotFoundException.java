package com.br.VotaCoop.domain.exception;


import java.io.Serial;

public class VotoNotFoundException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

	public VotoNotFoundException(String mensagem) {
            super(mensagem);
        }
}
