package com.br.VotaCoop.domain.exception;


import java.io.Serial;

public class AssociadoNotFoundException extends EntidadeNaoEncontradaException {

    @Serial
    private static final long serialVersionUID = 1L;

	public AssociadoNotFoundException(String mensagem) {
            super(mensagem);
        }
}
