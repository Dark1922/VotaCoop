package com.br.VotaCoop.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "voto")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sessao", nullable = false)
    private SessaoVotacao sessaoVotacao;

    @ManyToOne
    @JoinColumn(name = "id_associado", nullable = false)
    private Associado associado;

    @Column(name = "valor", nullable = false)
    private String valor;

    @Column(name = "data_voto", nullable = false)
    private LocalDateTime dataVoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SessaoVotacao getSessaoVotacao() {
        return sessaoVotacao;
    }

    public void setSessaoVotacao(SessaoVotacao sessaoVotacao) {
        this.sessaoVotacao = sessaoVotacao;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataVoto() {
        return dataVoto;
    }

    public void setDataVoto(LocalDateTime dataVoto) {
        this.dataVoto = dataVoto;
    }
}
