package com.br.VotaCoop.api.dto.Input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotoInput {

    @Schema(example = "2")
    @NotNull
    private Long idSessaoVotacao;

    @Schema(example = "1")
    @NotNull
    private Long idAssociado;

    @Schema(example = "Não")
    @NotBlank
    @NotNull
    @Pattern(regexp = "^(Sim|Não)$", message = "O valor do voto deve ser 'Sim' ou 'Não'")
    private String valor;

}
