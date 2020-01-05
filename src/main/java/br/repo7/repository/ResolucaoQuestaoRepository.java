package br.repo7.repository;

import br.repo7.domain.ResolucaoQuestao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResolucaoQuestao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResolucaoQuestaoRepository extends JpaRepository<ResolucaoQuestao, Long> {

}
