package com.br.VotaCoop.domain.repository;

import com.br.VotaCoop.domain.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsBySessaoVotacaoIdAndAssociadoId(Long sessaoVotacaoId, Long associadoId);
}
