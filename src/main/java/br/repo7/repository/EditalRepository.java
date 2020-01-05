package br.repo7.repository;

import br.repo7.domain.Edital;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Edital entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EditalRepository extends JpaRepository<Edital, Long> {

}
