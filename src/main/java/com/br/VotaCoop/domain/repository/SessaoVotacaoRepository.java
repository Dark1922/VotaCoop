package com.br.VotaCoop.domain.repository;

import com.br.VotaCoop.domain.model.SessaoVotacao;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

    List<SessaoVotacao> findByStatusContaining(String status);

    @Query(value =
            "SELECT \n" +
                    "    p.tema AS nomePauta,\n" +
                    "    SUM(CASE WHEN v.valor = 'Sim' THEN 1 ELSE 0 END) AS totalVotosSim,\n" +
                    "    SUM(CASE WHEN v.valor LIKE 'N%' THEN 1 ELSE 0 END) AS totalVotosNao, \n" +
                    "    COUNT(v.valor) AS totalVotos, -- Adicionando o total de votos\n" +
                    "    CASE\n" +
                    "        WHEN SUM(CASE WHEN v.valor = 'Sim' THEN 1 ELSE 0 END) > SUM(CASE WHEN v.valor LIKE 'N%' THEN 1 ELSE 0 END) THEN 'Aprovada'\n" +
                    "        ELSE 'Rejeitada'\n" +
                    "    END AS resultado\n" +
                    "FROM pauta p\n" +
                    "JOIN sessao_votacao sv ON sv.id_pauta = p.id\n" +
                    "JOIN voto v ON v.id_sessao = sv.id\n" +
                    "WHERE sv.id = ?\n" +
                    "GROUP BY p.tema;",
            nativeQuery = true)
    Tuple getResultadoVotacao(@Param("idSessaoVotacao") Long idSessao);
    SessaoVotacao findByPautaIdAndStatus(Long idPauta, String status);


}
