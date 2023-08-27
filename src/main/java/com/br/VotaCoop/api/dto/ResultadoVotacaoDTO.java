package com.br.VotaCoop.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoVotacaoDTO {

    private String nomePauta;
    private Long totalVotosSim;
    private Long totalVotosNao;
    private String resultado;
    private Long totalVotos;
}
