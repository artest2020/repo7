package br.repo7.web.rest;

import br.repo7.domain.Edital;
import br.repo7.repository.EditalRepository;
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
 * REST controller for managing {@link br.repo7.domain.Edital}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EditalResource {

    private final Logger log = LoggerFactory.getLogger(EditalResource.class);

    private static final String ENTITY_NAME = "edital";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EditalRepository editalRepository;

    public EditalResource(EditalRepository editalRepository) {
        this.editalRepository = editalRepository;
    }

    /**
     * {@code POST  /editals} : Create a new edital.
     *
     * @param edital the edital to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new edital, or with status {@code 400 (Bad Request)} if the edital has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/editals")
    public ResponseEntity<Edital> createEdital(@RequestBody Edital edital) throws URISyntaxException {
        log.debug("REST request to save Edital : {}", edital);
        if (edital.getId() != null) {
            throw new BadRequestAlertException("A new edital cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Edital result = editalRepository.save(edital);
        return ResponseEntity.created(new URI("/api/editals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /editals} : Updates an existing edital.
     *
     * @param edital the edital to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated edital,
     * or with status {@code 400 (Bad Request)} if the edital is not valid,
     * or with status {@code 500 (Internal Server Error)} if the edital couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/editals")
    public ResponseEntity<Edital> updateEdital(@RequestBody Edital edital) throws URISyntaxException {
        log.debug("REST request to update Edital : {}", edital);
        if (edital.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Edital result = editalRepository.save(edital);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, edital.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /editals} : get all the editals.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of editals in body.
     */
    @GetMapping("/editals")
    public List<Edital> getAllEditals() {
        log.debug("REST request to get all Editals");
        return editalRepository.findAll();
    }

    /**
     * {@code GET  /editals/:id} : get the "id" edital.
     *
     * @param id the id of the edital to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the edital, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/editals/{id}")
    public ResponseEntity<Edital> getEdital(@PathVariable Long id) {
        log.debug("REST request to get Edital : {}", id);
        Optional<Edital> edital = editalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(edital);
    }

    /**
     * {@code DELETE  /editals/:id} : delete the "id" edital.
     *
     * @param id the id of the edital to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/editals/{id}")
    public ResponseEntity<Void> deleteEdital(@PathVariable Long id) {
        log.debug("REST request to delete Edital : {}", id);
        editalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
