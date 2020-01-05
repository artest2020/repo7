package br.repo7.repository;

import br.repo7.domain.Prova;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Prova entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProvaRepository extends JpaRepository<Prova, Long> {

}
