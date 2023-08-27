package com.br.VotaCoop.api.dto.Input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotoInput {

    @NotNull
    private Long idSessaoVotacao;

    @NotNull
    private Long idAssociado;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^(Sim|Não)$", message = "O valor do voto deve ser 'Sim' ou 'Não'")
    private String valor;

}
