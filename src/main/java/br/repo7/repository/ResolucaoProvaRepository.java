package br.repo7.repository;

import br.repo7.domain.ResolucaoProva;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResolucaoProva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResolucaoProvaRepository extends JpaRepository<ResolucaoProva, Long> {

}
