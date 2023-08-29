package com.br.VotaCoop.api.dto.Input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PautaInput {

    @Schema(example = "Midia digital")
    @NotBlank
    @NotNull
    private String tema;

    @Schema(example = "danos a comunidade")
    @NotBlank
    @NotNull
    private String descricao;
}
