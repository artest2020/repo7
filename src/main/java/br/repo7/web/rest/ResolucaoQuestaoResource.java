package br.repo7.web.rest;

import br.repo7.domain.ResolucaoQuestao;
import br.repo7.repository.ResolucaoQuestaoRepository;
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
 * REST controller for managing {@link br.repo7.domain.ResolucaoQuestao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResolucaoQuestaoResource {

    private final Logger log = LoggerFactory.getLogger(ResolucaoQuestaoResource.class);

    private static final String ENTITY_NAME = "resolucaoQuestao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResolucaoQuestaoRepository resolucaoQuestaoRepository;

    public ResolucaoQuestaoResource(ResolucaoQuestaoRepository resolucaoQuestaoRepository) {
        this.resolucaoQuestaoRepository = resolucaoQuestaoRepository;
    }

    /**
     * {@code POST  /resolucao-questaos} : Create a new resolucaoQuestao.
     *
     * @param resolucaoQuestao the resolucaoQuestao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resolucaoQuestao, or with status {@code 400 (Bad Request)} if the resolucaoQuestao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resolucao-questaos")
    public ResponseEntity<ResolucaoQuestao> createResolucaoQuestao(@RequestBody ResolucaoQuestao resolucaoQuestao) throws URISyntaxException {
        log.debug("REST request to save ResolucaoQuestao : {}", resolucaoQuestao);
        if (resolucaoQuestao.getId() != null) {
            throw new BadRequestAlertException("A new resolucaoQuestao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResolucaoQuestao result = resolucaoQuestaoRepository.save(resolucaoQuestao);
        return ResponseEntity.created(new URI("/api/resolucao-questaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resolucao-questaos} : Updates an existing resolucaoQuestao.
     *
     * @param resolucaoQuestao the resolucaoQuestao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resolucaoQuestao,
     * or with status {@code 400 (Bad Request)} if the resolucaoQuestao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resolucaoQuestao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resolucao-questaos")
    public ResponseEntity<ResolucaoQuestao> updateResolucaoQuestao(@RequestBody ResolucaoQuestao resolucaoQuestao) throws URISyntaxException {
        log.debug("REST request to update ResolucaoQuestao : {}", resolucaoQuestao);
        if (resolucaoQuestao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResolucaoQuestao result = resolucaoQuestaoRepository.save(resolucaoQuestao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resolucaoQuestao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resolucao-questaos} : get all the resolucaoQuestaos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resolucaoQuestaos in body.
     */
    @GetMapping("/resolucao-questaos")
    public List<ResolucaoQuestao> getAllResolucaoQuestaos() {
        log.debug("REST request to get all ResolucaoQuestaos");
        return resolucaoQuestaoRepository.findAll();
    }

    /**
     * {@code GET  /resolucao-questaos/:id} : get the "id" resolucaoQuestao.
     *
     * @param id the id of the resolucaoQuestao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resolucaoQuestao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resolucao-questaos/{id}")
    public ResponseEntity<ResolucaoQuestao> getResolucaoQuestao(@PathVariable Long id) {
        log.debug("REST request to get ResolucaoQuestao : {}", id);
        Optional<ResolucaoQuestao> resolucaoQuestao = resolucaoQuestaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resolucaoQuestao);
    }

    /**
     * {@code DELETE  /resolucao-questaos/:id} : delete the "id" resolucaoQuestao.
     *
     * @param id the id of the resolucaoQuestao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resolucao-questaos/{id}")
    public ResponseEntity<Void> deleteResolucaoQuestao(@PathVariable Long id) {
        log.debug("REST request to delete ResolucaoQuestao : {}", id);
        resolucaoQuestaoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
