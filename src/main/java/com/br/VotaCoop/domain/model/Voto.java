package com.br.VotaCoop.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_associado", nullable = false)
    private Associado associado;

    @Column(name = "valor", nullable = false)
    private String valor;

    @CreationTimestamp
    @Column(name = "data_voto", updatable = false, nullable = false)
    private LocalDateTime dataVoto;

}
