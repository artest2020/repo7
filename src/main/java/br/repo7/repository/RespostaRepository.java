package br.repo7.repository;

import br.repo7.domain.Resposta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Resposta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long> {

}
