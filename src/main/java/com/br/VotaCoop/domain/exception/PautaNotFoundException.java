package com.br.VotaCoop.domain.exception;


import java.io.Serial;

public class PautaNotFoundException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

	public PautaNotFoundException(String mensagem) {
            super(mensagem);
        }
}
