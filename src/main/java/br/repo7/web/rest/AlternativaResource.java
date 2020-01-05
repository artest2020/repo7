package br.repo7.web.rest;

import br.repo7.domain.Alternativa;
import br.repo7.repository.AlternativaRepository;
import br.repo7.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.repo7.domain.Alternativa}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AlternativaResource {

    private final Logger log = LoggerFactory.getLogger(AlternativaResource.class);

    private static final String ENTITY_NAME = "alternativa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlternativaRepository alternativaRepository;

    public AlternativaResource(AlternativaRepository alternativaRepository) {
        this.alternativaRepository = alternativaRepository;
    }

    /**
     * {@code POST  /alternativas} : Create a new alternativa.
     *
     * @param alternativa the alternativa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alternativa, or with status {@code 400 (Bad Request)} if the alternativa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alternativas")
    public ResponseEntity<Alternativa> createAlternativa(@Valid @RequestBody Alternativa alternativa) throws URISyntaxException {
        log.debug("REST request to save Alternativa : {}", alternativa);
        if (alternativa.getId() != null) {
            throw new BadRequestAlertException("A new alternativa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Alternativa result = alternativaRepository.save(alternativa);
        return ResponseEntity.created(new URI("/api/alternativas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alternativas} : Updates an existing alternativa.
     *
     * @param alternativa the alternativa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alternativa,
     * or with status {@code 400 (Bad Request)} if the alternativa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alternativa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alternativas")
    public ResponseEntity<Alternativa> updateAlternativa(@Valid @RequestBody Alternativa alternativa) throws URISyntaxException {
        log.debug("REST request to update Alternativa : {}", alternativa);
        if (alternativa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Alternativa result = alternativaRepository.save(alternativa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alternativa.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alternativas} : get all the alternativas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alternativas in body.
     */
    @GetMapping("/alternativas")
    public List<Alternativa> getAllAlternativas() {
        log.debug("REST request to get all Alternativas");
        return alternativaRepository.findAll();
    }

    /**
     * {@code GET  /alternativas/:id} : get the "id" alternativa.
     *
     * @param id the id of the alternativa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alternativa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alternativas/{id}")
    public ResponseEntity<Alternativa> getAlternativa(@PathVariable Long id) {
        log.debug("REST request to get Alternativa : {}", id);
        Optional<Alternativa> alternativa = alternativaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(alternativa);
    }

    /**
     * {@code DELETE  /alternativas/:id} : delete the "id" alternativa.
     *
     * @param id the id of the alternativa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alternativas/{id}")
    public ResponseEntity<Void> deleteAlternativa(@PathVariable Long id) {
        log.debug("REST request to delete Alternativa : {}", id);
        alternativaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
