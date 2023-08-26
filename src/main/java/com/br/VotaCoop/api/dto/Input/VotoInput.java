package com.br.VotaCoop.api.dto.Input;

import com.br.VotaCoop.domain.model.Associado;
import com.br.VotaCoop.domain.model.SessaoVotacao;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class VotoInput {
    private SessaoVotacao sessaoVotacao;
    private Associado associado;
    private String valor;
}
