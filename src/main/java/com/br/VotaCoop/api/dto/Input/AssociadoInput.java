package com.br.VotaCoop.api.dto.Input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssociadoInput {
    @NotBlank
    @NotNull
    private String nome;
    @NotBlank
    @NotNull
    private String cpf;
}
