package br.repo7.web.rest;

import br.repo7.domain.Pergunta;
import br.repo7.repository.PerguntaRepository;
import br.repo7.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.repo7.domain.Pergunta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PerguntaResource {

    private final Logger log = LoggerFactory.getLogger(PerguntaResource.class);

    private static final String ENTITY_NAME = "pergunta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerguntaRepository perguntaRepository;

    public PerguntaResource(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    /**
     * {@code POST  /perguntas} : Create a new pergunta.
     *
     * @param pergunta the pergunta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pergunta, or with status {@code 400 (Bad Request)} if the pergunta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perguntas")
    public ResponseEntity<Pergunta> createPergunta(@RequestBody Pergunta pergunta) throws URISyntaxException {
        log.debug("REST request to save Pergunta : {}", pergunta);
        if (pergunta.getId() != null) {
            throw new BadRequestAlertException("A new pergunta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pergunta result = perguntaRepository.save(pergunta);
        return ResponseEntity.created(new URI("/api/perguntas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /perguntas} : Updates an existing pergunta.
     *
     * @param pergunta the pergunta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pergunta,
     * or with status {@code 400 (Bad Request)} if the pergunta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pergunta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perguntas")
    public ResponseEntity<Pergunta> updatePergunta(@RequestBody Pergunta pergunta) throws URISyntaxException {
        log.debug("REST request to update Pergunta : {}", pergunta);
        if (pergunta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pergunta result = perguntaRepository.save(pergunta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pergunta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /perguntas} : get all the perguntas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perguntas in body.
     */
    @GetMapping("/perguntas")
    public List<Pergunta> getAllPerguntas() {
        log.debug("REST request to get all Perguntas");
        return perguntaRepository.findAll();
    }

    /**
     * {@code GET  /perguntas/:id} : get the "id" pergunta.
     *
     * @param id the id of the pergunta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pergunta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perguntas/{id}")
    public ResponseEntity<Pergunta> getPergunta(@PathVariable Long id) {
        log.debug("REST request to get Pergunta : {}", id);
        Optional<Pergunta> pergunta = perguntaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pergunta);
    }

    /**
     * {@code DELETE  /perguntas/:id} : delete the "id" pergunta.
     *
     * @param id the id of the pergunta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perguntas/{id}")
    public ResponseEntity<Void> deletePergunta(@PathVariable Long id) {
        log.debug("REST request to delete Pergunta : {}", id);
        perguntaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
