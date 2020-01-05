package br.repo7.web.rest;

import br.repo7.domain.Frase;
import br.repo7.repository.FraseRepository;
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
 * REST controller for managing {@link br.repo7.domain.Frase}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FraseResource {

    private final Logger log = LoggerFactory.getLogger(FraseResource.class);

    private static final String ENTITY_NAME = "frase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FraseRepository fraseRepository;

    public FraseResource(FraseRepository fraseRepository) {
        this.fraseRepository = fraseRepository;
    }

    /**
     * {@code POST  /frases} : Create a new frase.
     *
     * @param frase the frase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new frase, or with status {@code 400 (Bad Request)} if the frase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/frases")
    public ResponseEntity<Frase> createFrase(@RequestBody Frase frase) throws URISyntaxException {
        log.debug("REST request to save Frase : {}", frase);
        if (frase.getId() != null) {
            throw new BadRequestAlertException("A new frase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Frase result = fraseRepository.save(frase);
        return ResponseEntity.created(new URI("/api/frases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /frases} : Updates an existing frase.
     *
     * @param frase the frase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frase,
     * or with status {@code 400 (Bad Request)} if the frase is not valid,
     * or with status {@code 500 (Internal Server Error)} if the frase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/frases")
    public ResponseEntity<Frase> updateFrase(@RequestBody Frase frase) throws URISyntaxException {
        log.debug("REST request to update Frase : {}", frase);
        if (frase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Frase result = fraseRepository.save(frase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, frase.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /frases} : get all the frases.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of frases in body.
     */
    @GetMapping("/frases")
    public List<Frase> getAllFrases() {
        log.debug("REST request to get all Frases");
        return fraseRepository.findAll();
    }

    /**
     * {@code GET  /frases/:id} : get the "id" frase.
     *
     * @param id the id of the frase to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the frase, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/frases/{id}")
    public ResponseEntity<Frase> getFrase(@PathVariable Long id) {
        log.debug("REST request to get Frase : {}", id);
        Optional<Frase> frase = fraseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(frase);
    }

    /**
     * {@code DELETE  /frases/:id} : delete the "id" frase.
     *
     * @param id the id of the frase to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/frases/{id}")
    public ResponseEntity<Void> deleteFrase(@PathVariable Long id) {
        log.debug("REST request to delete Frase : {}", id);
        fraseRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
