package com.br.VotaCoop.dto;

import com.br.VotaCoop.model.Pauta;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class SessaoVotacaoDTO {
    private Long id;
    private Pauta pauta;
    private LocalDateTime dataInicio;
    private Integer duracao;
    private String status;
}
