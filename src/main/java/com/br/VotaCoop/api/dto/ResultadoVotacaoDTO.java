package com.br.VotaCoop.api.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoVotacaoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nomePauta;
    private Long totalVotosSim;
    private Long totalVotosNao;
    private String resultado;
    private Long totalVotos;
}
