package com.br.VotaCoop.domain.repository;

import com.br.VotaCoop.domain.model.Pauta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    @Query("select p from Pauta p where upper(trim(p.tema)) like %?1%")
    Page<Pauta> findByTemaContaining(String tema, Pageable pageable);
}
