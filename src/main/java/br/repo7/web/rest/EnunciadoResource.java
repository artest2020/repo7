package br.repo7.web.rest;

import br.repo7.domain.Enunciado;
import br.repo7.repository.EnunciadoRepository;
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
 * REST controller for managing {@link br.repo7.domain.Enunciado}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EnunciadoResource {

    private final Logger log = LoggerFactory.getLogger(EnunciadoResource.class);

    private static final String ENTITY_NAME = "enunciado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnunciadoRepository enunciadoRepository;

    public EnunciadoResource(EnunciadoRepository enunciadoRepository) {
        this.enunciadoRepository = enunciadoRepository;
    }

    /**
     * {@code POST  /enunciados} : Create a new enunciado.
     *
     * @param enunciado the enunciado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enunciado, or with status {@code 400 (Bad Request)} if the enunciado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enunciados")
    public ResponseEntity<Enunciado> createEnunciado(@RequestBody Enunciado enunciado) throws URISyntaxException {
        log.debug("REST request to save Enunciado : {}", enunciado);
        if (enunciado.getId() != null) {
            throw new BadRequestAlertException("A new enunciado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Enunciado result = enunciadoRepository.save(enunciado);
        return ResponseEntity.created(new URI("/api/enunciados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enunciados} : Updates an existing enunciado.
     *
     * @param enunciado the enunciado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enunciado,
     * or with status {@code 400 (Bad Request)} if the enunciado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enunciado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enunciados")
    public ResponseEntity<Enunciado> updateEnunciado(@RequestBody Enunciado enunciado) throws URISyntaxException {
        log.debug("REST request to update Enunciado : {}", enunciado);
        if (enunciado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Enunciado result = enunciadoRepository.save(enunciado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enunciado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enunciados} : get all the enunciados.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enunciados in body.
     */
    @GetMapping("/enunciados")
    public List<Enunciado> getAllEnunciados() {
        log.debug("REST request to get all Enunciados");
        return enunciadoRepository.findAll();
    }

    /**
     * {@code GET  /enunciados/:id} : get the "id" enunciado.
     *
     * @param id the id of the enunciado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enunciado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enunciados/{id}")
    public ResponseEntity<Enunciado> getEnunciado(@PathVariable Long id) {
        log.debug("REST request to get Enunciado : {}", id);
        Optional<Enunciado> enunciado = enunciadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(enunciado);
    }

    /**
     * {@code DELETE  /enunciados/:id} : delete the "id" enunciado.
     *
     * @param id the id of the enunciado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enunciados/{id}")
    public ResponseEntity<Void> deleteEnunciado(@PathVariable Long id) {
        log.debug("REST request to delete Enunciado : {}", id);
        enunciadoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
