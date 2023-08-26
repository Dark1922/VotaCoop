package com.br.VotaCoop.repository;

import com.br.VotaCoop.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Integer> {
}
