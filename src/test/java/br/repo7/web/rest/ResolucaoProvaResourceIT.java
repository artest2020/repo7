package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.ResolucaoProva;
import br.repo7.repository.ResolucaoProvaRepository;
import br.repo7.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static br.repo7.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ResolucaoProvaResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class ResolucaoProvaResourceIT {

    private static final Instant DEFAULT_DATA_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_HORA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResolucaoProvaRepository resolucaoProvaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restResolucaoProvaMockMvc;

    private ResolucaoProva resolucaoProva;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResolucaoProvaResource resolucaoProvaResource = new ResolucaoProvaResource(resolucaoProvaRepository);
        this.restResolucaoProvaMockMvc = MockMvcBuilders.standaloneSetup(resolucaoProvaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResolucaoProva createEntity(EntityManager em) {
        ResolucaoProva resolucaoProva = new ResolucaoProva()
            .dataHoraInicio(DEFAULT_DATA_HORA_INICIO)
            .dataHoraFim(DEFAULT_DATA_HORA_FIM);
        return resolucaoProva;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResolucaoProva createUpdatedEntity(EntityManager em) {
        ResolucaoProva resolucaoProva = new ResolucaoProva()
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM);
        return resolucaoProva;
    }

    @BeforeEach
    public void initTest() {
        resolucaoProva = createEntity(em);
    }

    @Test
    @Transactional
    public void createResolucaoProva() throws Exception {
        int databaseSizeBeforeCreate = resolucaoProvaRepository.findAll().size();

        // Create the ResolucaoProva
        restResolucaoProvaMockMvc.perform(post("/api/resolucao-prova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolucaoProva)))
            .andExpect(status().isCreated());

        // Validate the ResolucaoProva in the database
        List<ResolucaoProva> resolucaoProvaList = resolucaoProvaRepository.findAll();
        assertThat(resolucaoProvaList).hasSize(databaseSizeBeforeCreate + 1);
        ResolucaoProva testResolucaoProva = resolucaoProvaList.get(resolucaoProvaList.size() - 1);
        assertThat(testResolucaoProva.getDataHoraInicio()).isEqualTo(DEFAULT_DATA_HORA_INICIO);
        assertThat(testResolucaoProva.getDataHoraFim()).isEqualTo(DEFAULT_DATA_HORA_FIM);
    }

    @Test
    @Transactional
    public void createResolucaoProvaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resolucaoProvaRepository.findAll().size();

        // Create the ResolucaoProva with an existing ID
        resolucaoProva.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResolucaoProvaMockMvc.perform(post("/api/resolucao-prova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolucaoProva)))
            .andExpect(status().isBadRequest());

        // Validate the ResolucaoProva in the database
        List<ResolucaoProva> resolucaoProvaList = resolucaoProvaRepository.findAll();
        assertThat(resolucaoProvaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllResolucaoProva() throws Exception {
        // Initialize the database
        resolucaoProvaRepository.saveAndFlush(resolucaoProva);

        // Get all the resolucaoProvaList
        restResolucaoProvaMockMvc.perform(get("/api/resolucao-prova?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resolucaoProva.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHoraInicio").value(hasItem(DEFAULT_DATA_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataHoraFim").value(hasItem(DEFAULT_DATA_HORA_FIM.toString())));
    }
    
    @Test
    @Transactional
    public void getResolucaoProva() throws Exception {
        // Initialize the database
        resolucaoProvaRepository.saveAndFlush(resolucaoProva);

        // Get the resolucaoProva
        restResolucaoProvaMockMvc.perform(get("/api/resolucao-prova/{id}", resolucaoProva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resolucaoProva.getId().intValue()))
            .andExpect(jsonPath("$.dataHoraInicio").value(DEFAULT_DATA_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.dataHoraFim").value(DEFAULT_DATA_HORA_FIM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResolucaoProva() throws Exception {
        // Get the resolucaoProva
        restResolucaoProvaMockMvc.perform(get("/api/resolucao-prova/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResolucaoProva() throws Exception {
        // Initialize the database
        resolucaoProvaRepository.saveAndFlush(resolucaoProva);

        int databaseSizeBeforeUpdate = resolucaoProvaRepository.findAll().size();

        // Update the resolucaoProva
        ResolucaoProva updatedResolucaoProva = resolucaoProvaRepository.findById(resolucaoProva.getId()).get();
        // Disconnect from session so that the updates on updatedResolucaoProva are not directly saved in db
        em.detach(updatedResolucaoProva);
        updatedResolucaoProva
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM);

        restResolucaoProvaMockMvc.perform(put("/api/resolucao-prova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResolucaoProva)))
            .andExpect(status().isOk());

        // Validate the ResolucaoProva in the database
        List<ResolucaoProva> resolucaoProvaList = resolucaoProvaRepository.findAll();
        assertThat(resolucaoProvaList).hasSize(databaseSizeBeforeUpdate);
        ResolucaoProva testResolucaoProva = resolucaoProvaList.get(resolucaoProvaList.size() - 1);
        assertThat(testResolucaoProva.getDataHoraInicio()).isEqualTo(UPDATED_DATA_HORA_INICIO);
        assertThat(testResolucaoProva.getDataHoraFim()).isEqualTo(UPDATED_DATA_HORA_FIM);
    }

    @Test
    @Transactional
    public void updateNonExistingResolucaoProva() throws Exception {
        int databaseSizeBeforeUpdate = resolucaoProvaRepository.findAll().size();

        // Create the ResolucaoProva

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResolucaoProvaMockMvc.perform(put("/api/resolucao-prova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolucaoProva)))
            .andExpect(status().isBadRequest());

        // Validate the ResolucaoProva in the database
        List<ResolucaoProva> resolucaoProvaList = resolucaoProvaRepository.findAll();
        assertThat(resolucaoProvaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResolucaoProva() throws Exception {
        // Initialize the database
        resolucaoProvaRepository.saveAndFlush(resolucaoProva);

        int databaseSizeBeforeDelete = resolucaoProvaRepository.findAll().size();

        // Delete the resolucaoProva
        restResolucaoProvaMockMvc.perform(delete("/api/resolucao-prova/{id}", resolucaoProva.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResolucaoProva> resolucaoProvaList = resolucaoProvaRepository.findAll();
        assertThat(resolucaoProvaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
