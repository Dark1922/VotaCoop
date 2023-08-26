package com.br.VotaCoop.dto;

import com.br.VotaCoop.model.Associado;
import com.br.VotaCoop.model.SessaoVotacao;
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
