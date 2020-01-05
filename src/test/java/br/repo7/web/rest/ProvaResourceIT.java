package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.Prova;
import br.repo7.domain.Instituicao;
import br.repo7.domain.Edital;
import br.repo7.repository.ProvaRepository;
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
 * Integration tests for the {@link ProvaResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class ProvaResourceIT {

    private static final Long DEFAULT_ANO = 1L;
    private static final Long UPDATED_ANO = 2L;

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_HORA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProvaRepository provaRepository;

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

    private MockMvc restProvaMockMvc;

    private Prova prova;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProvaResource provaResource = new ProvaResource(provaRepository);
        this.restProvaMockMvc = MockMvcBuilders.standaloneSetup(provaResource)
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
    public static Prova createEntity(EntityManager em) {
        Prova prova = new Prova()
            .ano(DEFAULT_ANO)
            .cidade(DEFAULT_CIDADE)
            .dataHoraInicio(DEFAULT_DATA_HORA_INICIO)
            .dataHoraFim(DEFAULT_DATA_HORA_FIM);
        // Add required entity
        Instituicao instituicao;
        if (TestUtil.findAll(em, Instituicao.class).isEmpty()) {
            instituicao = InstituicaoResourceIT.createEntity(em);
            em.persist(instituicao);
            em.flush();
        } else {
            instituicao = TestUtil.findAll(em, Instituicao.class).get(0);
        }
        prova.setInstituicao(instituicao);
        // Add required entity
        Edital edital;
        if (TestUtil.findAll(em, Edital.class).isEmpty()) {
            edital = EditalResourceIT.createEntity(em);
            em.persist(edital);
            em.flush();
        } else {
            edital = TestUtil.findAll(em, Edital.class).get(0);
        }
        prova.setEdital(edital);
        return prova;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prova createUpdatedEntity(EntityManager em) {
        Prova prova = new Prova()
            .ano(UPDATED_ANO)
            .cidade(UPDATED_CIDADE)
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM);
        // Add required entity
        Instituicao instituicao;
        if (TestUtil.findAll(em, Instituicao.class).isEmpty()) {
            instituicao = InstituicaoResourceIT.createUpdatedEntity(em);
            em.persist(instituicao);
            em.flush();
        } else {
            instituicao = TestUtil.findAll(em, Instituicao.class).get(0);
        }
        prova.setInstituicao(instituicao);
        // Add required entity
        Edital edital;
        if (TestUtil.findAll(em, Edital.class).isEmpty()) {
            edital = EditalResourceIT.createUpdatedEntity(em);
            em.persist(edital);
            em.flush();
        } else {
            edital = TestUtil.findAll(em, Edital.class).get(0);
        }
        prova.setEdital(edital);
        return prova;
    }

    @BeforeEach
    public void initTest() {
        prova = createEntity(em);
    }

    @Test
    @Transactional
    public void createProva() throws Exception {
        int databaseSizeBeforeCreate = provaRepository.findAll().size();

        // Create the Prova
        restProvaMockMvc.perform(post("/api/prova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova)))
            .andExpect(status().isCreated());

        // Validate the Prova in the database
        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeCreate + 1);
        Prova testProva = provaList.get(provaList.size() - 1);
        assertThat(testProva.getAno()).isEqualTo(DEFAULT_ANO);
        assertThat(testProva.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testProva.getDataHoraInicio()).isEqualTo(DEFAULT_DATA_HORA_INICIO);
        assertThat(testProva.getDataHoraFim()).isEqualTo(DEFAULT_DATA_HORA_FIM);
    }

    @Test
    @Transactional
    public void createProvaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = provaRepository.findAll().size();

        // Create the Prova with an existing ID
        prova.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvaMockMvc.perform(post("/api/prova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova)))
            .andExpect(status().isBadRequest());

        // Validate the Prova in the database
        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = provaRepository.findAll().size();
        // set the field null
        prova.setAno(null);

        // Create the Prova, which fails.

        restProvaMockMvc.perform(post("/api/prova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova)))
            .andExpect(status().isBadRequest());

        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProva() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList
        restProvaMockMvc.perform(get("/api/prova?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prova.getId().intValue())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO.intValue())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].dataHoraInicio").value(hasItem(DEFAULT_DATA_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataHoraFim").value(hasItem(DEFAULT_DATA_HORA_FIM.toString())));
    }
    
    @Test
    @Transactional
    public void getProva() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get the prova
        restProvaMockMvc.perform(get("/api/prova/{id}", prova.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prova.getId().intValue()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO.intValue()))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.dataHoraInicio").value(DEFAULT_DATA_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.dataHoraFim").value(DEFAULT_DATA_HORA_FIM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProva() throws Exception {
        // Get the prova
        restProvaMockMvc.perform(get("/api/prova/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProva() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        int databaseSizeBeforeUpdate = provaRepository.findAll().size();

        // Update the prova
        Prova updatedProva = provaRepository.findById(prova.getId()).get();
        // Disconnect from session so that the updates on updatedProva are not directly saved in db
        em.detach(updatedProva);
        updatedProva
            .ano(UPDATED_ANO)
            .cidade(UPDATED_CIDADE)
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM);

        restProvaMockMvc.perform(put("/api/prova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProva)))
            .andExpect(status().isOk());

        // Validate the Prova in the database
        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeUpdate);
        Prova testProva = provaList.get(provaList.size() - 1);
        assertThat(testProva.getAno()).isEqualTo(UPDATED_ANO);
        assertThat(testProva.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testProva.getDataHoraInicio()).isEqualTo(UPDATED_DATA_HORA_INICIO);
        assertThat(testProva.getDataHoraFim()).isEqualTo(UPDATED_DATA_HORA_FIM);
    }

    @Test
    @Transactional
    public void updateNonExistingProva() throws Exception {
        int databaseSizeBeforeUpdate = provaRepository.findAll().size();

        // Create the Prova

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvaMockMvc.perform(put("/api/prova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prova)))
            .andExpect(status().isBadRequest());

        // Validate the Prova in the database
        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProva() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        int databaseSizeBeforeDelete = provaRepository.findAll().size();

        // Delete the prova
        restProvaMockMvc.perform(delete("/api/prova/{id}", prova.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
