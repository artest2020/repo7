package br.repo7.repository;

import br.repo7.domain.Pergunta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pergunta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

}
