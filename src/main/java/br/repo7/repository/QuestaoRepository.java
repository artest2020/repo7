package br.repo7.repository;

import br.repo7.domain.Questao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Questao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Long> {

}
