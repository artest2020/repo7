package br.repo7.repository;

import br.repo7.domain.Alternativa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Alternativa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlternativaRepository extends JpaRepository<Alternativa, Long> {

}
