package com.br.VotaCoop.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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

}
