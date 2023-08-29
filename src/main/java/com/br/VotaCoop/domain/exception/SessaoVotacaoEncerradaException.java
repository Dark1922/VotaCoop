package com.br.VotaCoop.domain.exception;


import java.io.Serial;

public class SessaoVotacaoEncerradaException extends NegocioException {

    @Serial
    private static final long serialVersionUID = 1L;

	public SessaoVotacaoEncerradaException(String mensagem) {
            super(mensagem);
        }
}
