package br.repo7.web.rest;

import br.repo7.domain.ResolucaoProva;
import br.repo7.repository.ResolucaoProvaRepository;
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
 * REST controller for managing {@link br.repo7.domain.ResolucaoProva}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResolucaoProvaResource {

    private final Logger log = LoggerFactory.getLogger(ResolucaoProvaResource.class);

    private static final String ENTITY_NAME = "resolucaoProva";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResolucaoProvaRepository resolucaoProvaRepository;

    public ResolucaoProvaResource(ResolucaoProvaRepository resolucaoProvaRepository) {
        this.resolucaoProvaRepository = resolucaoProvaRepository;
    }

    /**
     * {@code POST  /resolucao-prova} : Create a new resolucaoProva.
     *
     * @param resolucaoProva the resolucaoProva to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resolucaoProva, or with status {@code 400 (Bad Request)} if the resolucaoProva has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resolucao-prova")
    public ResponseEntity<ResolucaoProva> createResolucaoProva(@RequestBody ResolucaoProva resolucaoProva) throws URISyntaxException {
        log.debug("REST request to save ResolucaoProva : {}", resolucaoProva);
        if (resolucaoProva.getId() != null) {
            throw new BadRequestAlertException("A new resolucaoProva cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResolucaoProva result = resolucaoProvaRepository.save(resolucaoProva);
        return ResponseEntity.created(new URI("/api/resolucao-prova/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resolucao-prova} : Updates an existing resolucaoProva.
     *
     * @param resolucaoProva the resolucaoProva to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resolucaoProva,
     * or with status {@code 400 (Bad Request)} if the resolucaoProva is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resolucaoProva couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resolucao-prova")
    public ResponseEntity<ResolucaoProva> updateResolucaoProva(@RequestBody ResolucaoProva resolucaoProva) throws URISyntaxException {
        log.debug("REST request to update ResolucaoProva : {}", resolucaoProva);
        if (resolucaoProva.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResolucaoProva result = resolucaoProvaRepository.save(resolucaoProva);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resolucaoProva.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resolucao-prova} : get all the resolucaoProva.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resolucaoProva in body.
     */
    @GetMapping("/resolucao-prova")
    public List<ResolucaoProva> getAllResolucaoProva() {
        log.debug("REST request to get all ResolucaoProva");
        return resolucaoProvaRepository.findAll();
    }

    /**
     * {@code GET  /resolucao-prova/:id} : get the "id" resolucaoProva.
     *
     * @param id the id of the resolucaoProva to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resolucaoProva, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resolucao-prova/{id}")
    public ResponseEntity<ResolucaoProva> getResolucaoProva(@PathVariable Long id) {
        log.debug("REST request to get ResolucaoProva : {}", id);
        Optional<ResolucaoProva> resolucaoProva = resolucaoProvaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resolucaoProva);
    }

    /**
     * {@code DELETE  /resolucao-prova/:id} : delete the "id" resolucaoProva.
     *
     * @param id the id of the resolucaoProva to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resolucao-prova/{id}")
    public ResponseEntity<Void> deleteResolucaoProva(@PathVariable Long id) {
        log.debug("REST request to delete ResolucaoProva : {}", id);
        resolucaoProvaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
