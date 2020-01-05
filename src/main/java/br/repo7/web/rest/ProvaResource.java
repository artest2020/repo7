package br.repo7.web.rest;

import br.repo7.domain.Prova;
import br.repo7.repository.ProvaRepository;
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
 * REST controller for managing {@link br.repo7.domain.Prova}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProvaResource {

    private final Logger log = LoggerFactory.getLogger(ProvaResource.class);

    private static final String ENTITY_NAME = "prova";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvaRepository provaRepository;

    public ProvaResource(ProvaRepository provaRepository) {
        this.provaRepository = provaRepository;
    }

    /**
     * {@code POST  /prova} : Create a new prova.
     *
     * @param prova the prova to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prova, or with status {@code 400 (Bad Request)} if the prova has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prova")
    public ResponseEntity<Prova> createProva(@Valid @RequestBody Prova prova) throws URISyntaxException {
        log.debug("REST request to save Prova : {}", prova);
        if (prova.getId() != null) {
            throw new BadRequestAlertException("A new prova cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prova result = provaRepository.save(prova);
        return ResponseEntity.created(new URI("/api/prova/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prova} : Updates an existing prova.
     *
     * @param prova the prova to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prova,
     * or with status {@code 400 (Bad Request)} if the prova is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prova couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prova")
    public ResponseEntity<Prova> updateProva(@Valid @RequestBody Prova prova) throws URISyntaxException {
        log.debug("REST request to update Prova : {}", prova);
        if (prova.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prova result = provaRepository.save(prova);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prova.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prova} : get all the prova.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prova in body.
     */
    @GetMapping("/prova")
    public List<Prova> getAllProva() {
        log.debug("REST request to get all Prova");
        return provaRepository.findAll();
    }

    /**
     * {@code GET  /prova/:id} : get the "id" prova.
     *
     * @param id the id of the prova to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prova, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prova/{id}")
    public ResponseEntity<Prova> getProva(@PathVariable Long id) {
        log.debug("REST request to get Prova : {}", id);
        Optional<Prova> prova = provaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prova);
    }

    /**
     * {@code DELETE  /prova/:id} : delete the "id" prova.
     *
     * @param id the id of the prova to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prova/{id}")
    public ResponseEntity<Void> deleteProva(@PathVariable Long id) {
        log.debug("REST request to delete Prova : {}", id);
        provaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
