package com.br.VotaCoop.api.dto.Input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SessaoVotacaoInput {

    @Schema(example = "1")
    private long idPauta;

    @Schema(example = "3")
    private Integer duracao;
}
