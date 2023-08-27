package com.br.VotaCoop.api.dto.Input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class VotoInput {

    @NotNull
    private Long idSessaoVotacao;

    @NotNull
    private Long idAssociado;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^(Sim|Não)$", message = "O valor do voto deve ser 'Sim' ou 'Não'")
    private String valor;

    public Long getIdSessaoVotacao() {
        return idSessaoVotacao;
    }

    public void setIdSessaoVotacao(Long idSessaoVotacao) {
        this.idSessaoVotacao = idSessaoVotacao;
    }

    public Long getIdAssociado() {
        return idAssociado;
    }

    public void setIdAssociado(Long idAssociado) {
        this.idAssociado = idAssociado;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
