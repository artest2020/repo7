package br.repo7.repository;

import br.repo7.domain.Frase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Frase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FraseRepository extends JpaRepository<Frase, Long> {

}
