package com.br.VotaCoop.api.dto.Input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Setter
@Getter
public class AssociadoInput {

    @Schema(example = "João Victor")
    @NotBlank
    @NotNull
    private String nome;

    @Schema(example = "00000000000")
    @NotBlank
    @NotNull
    @CPF
    private String cpf;
}
