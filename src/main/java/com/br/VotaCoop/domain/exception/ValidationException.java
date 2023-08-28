package com.br.VotaCoop.domain.exception;

public class ValidationException extends NegocioException {

    public ValidationException(String message) {
        super(message);
    }
}