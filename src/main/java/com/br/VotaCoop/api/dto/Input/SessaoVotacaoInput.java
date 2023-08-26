package com.br.VotaCoop.api.dto.Input;

import com.br.VotaCoop.domain.model.Pauta;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SessaoVotacaoInput {
    private Pauta pauta;
    private Integer duracao;
    private String status;
}
