package com.br.VotaCoop.api.dto.Input;

import lombok.Data;

@Data
public class SessaoVotacaoInput {
    private long idPauta;
    private Integer duracao;

    public long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(long idPauta) {
        this.idPauta = idPauta;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

}
