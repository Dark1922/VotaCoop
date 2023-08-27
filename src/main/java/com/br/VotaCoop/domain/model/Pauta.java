package com.br.VotaCoop.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "pauta")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "tema", nullable = false)
    private String tema;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
}
