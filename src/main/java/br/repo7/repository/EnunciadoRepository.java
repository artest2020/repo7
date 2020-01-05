package br.repo7.repository;

import br.repo7.domain.Enunciado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Enunciado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnunciadoRepository extends JpaRepository<Enunciado, Long> {

}
