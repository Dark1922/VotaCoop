package com.br.VotaCoop.domain.repository;

import com.br.VotaCoop.domain.model.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Integer> {
}
