package br.repo7.web.rest;

import br.repo7.domain.Instituicao;
import br.repo7.repository.InstituicaoRepository;
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
 * REST controller for managing {@link br.repo7.domain.Instituicao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InstituicaoResource {

    private final Logger log = LoggerFactory.getLogger(InstituicaoResource.class);

    private static final String ENTITY_NAME = "instituicao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstituicaoRepository instituicaoRepository;

    public InstituicaoResource(InstituicaoRepository instituicaoRepository) {
        this.instituicaoRepository = instituicaoRepository;
    }

    /**
     * {@code POST  /instituicaos} : Create a new instituicao.
     *
     * @param instituicao the instituicao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instituicao, or with status {@code 400 (Bad Request)} if the instituicao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instituicaos")
    public ResponseEntity<Instituicao> createInstituicao(@Valid @RequestBody Instituicao instituicao) throws URISyntaxException {
        log.debug("REST request to save Instituicao : {}", instituicao);
        if (instituicao.getId() != null) {
            throw new BadRequestAlertException("A new instituicao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Instituicao result = instituicaoRepository.save(instituicao);
        return ResponseEntity.created(new URI("/api/instituicaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instituicaos} : Updates an existing instituicao.
     *
     * @param instituicao the instituicao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instituicao,
     * or with status {@code 400 (Bad Request)} if the instituicao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instituicao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instituicaos")
    public ResponseEntity<Instituicao> updateInstituicao(@Valid @RequestBody Instituicao instituicao) throws URISyntaxException {
        log.debug("REST request to update Instituicao : {}", instituicao);
        if (instituicao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Instituicao result = instituicaoRepository.save(instituicao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instituicao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instituicaos} : get all the instituicaos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instituicaos in body.
     */
    @GetMapping("/instituicaos")
    public List<Instituicao> getAllInstituicaos() {
        log.debug("REST request to get all Instituicaos");
        return instituicaoRepository.findAll();
    }

    /**
     * {@code GET  /instituicaos/:id} : get the "id" instituicao.
     *
     * @param id the id of the instituicao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instituicao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instituicaos/{id}")
    public ResponseEntity<Instituicao> getInstituicao(@PathVariable Long id) {
        log.debug("REST request to get Instituicao : {}", id);
        Optional<Instituicao> instituicao = instituicaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(instituicao);
    }

    /**
     * {@code DELETE  /instituicaos/:id} : delete the "id" instituicao.
     *
     * @param id the id of the instituicao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instituicaos/{id}")
    public ResponseEntity<Void> deleteInstituicao(@PathVariable Long id) {
        log.debug("REST request to delete Instituicao : {}", id);
        instituicaoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
