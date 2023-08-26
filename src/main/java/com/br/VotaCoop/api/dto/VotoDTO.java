package com.br.VotaCoop.api.dto;

import com.br.VotaCoop.domain.model.Associado;
import com.br.VotaCoop.domain.model.SessaoVotacao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VotoDTO {
    private Long id;
    private SessaoVotacao sessaoVotacao;
    private Associado associado;
    private String valor;
    private LocalDateTime dataVoto;
}
