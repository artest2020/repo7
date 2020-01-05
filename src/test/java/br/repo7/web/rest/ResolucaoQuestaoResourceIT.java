package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.ResolucaoQuestao;
import br.repo7.repository.ResolucaoQuestaoRepository;
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
 * Integration tests for the {@link ResolucaoQuestaoResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class ResolucaoQuestaoResourceIT {

    private static final Instant DEFAULT_DATA_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_HORA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResolucaoQuestaoRepository resolucaoQuestaoRepository;

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

    private MockMvc restResolucaoQuestaoMockMvc;

    private ResolucaoQuestao resolucaoQuestao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResolucaoQuestaoResource resolucaoQuestaoResource = new ResolucaoQuestaoResource(resolucaoQuestaoRepository);
        this.restResolucaoQuestaoMockMvc = MockMvcBuilders.standaloneSetup(resolucaoQuestaoResource)
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
    public static ResolucaoQuestao createEntity(EntityManager em) {
        ResolucaoQuestao resolucaoQuestao = new ResolucaoQuestao()
            .dataHoraInicio(DEFAULT_DATA_HORA_INICIO)
            .dataHoraFim(DEFAULT_DATA_HORA_FIM);
        return resolucaoQuestao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResolucaoQuestao createUpdatedEntity(EntityManager em) {
        ResolucaoQuestao resolucaoQuestao = new ResolucaoQuestao()
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM);
        return resolucaoQuestao;
    }

    @BeforeEach
    public void initTest() {
        resolucaoQuestao = createEntity(em);
    }

    @Test
    @Transactional
    public void createResolucaoQuestao() throws Exception {
        int databaseSizeBeforeCreate = resolucaoQuestaoRepository.findAll().size();

        // Create the ResolucaoQuestao
        restResolucaoQuestaoMockMvc.perform(post("/api/resolucao-questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolucaoQuestao)))
            .andExpect(status().isCreated());

        // Validate the ResolucaoQuestao in the database
        List<ResolucaoQuestao> resolucaoQuestaoList = resolucaoQuestaoRepository.findAll();
        assertThat(resolucaoQuestaoList).hasSize(databaseSizeBeforeCreate + 1);
        ResolucaoQuestao testResolucaoQuestao = resolucaoQuestaoList.get(resolucaoQuestaoList.size() - 1);
        assertThat(testResolucaoQuestao.getDataHoraInicio()).isEqualTo(DEFAULT_DATA_HORA_INICIO);
        assertThat(testResolucaoQuestao.getDataHoraFim()).isEqualTo(DEFAULT_DATA_HORA_FIM);
    }

    @Test
    @Transactional
    public void createResolucaoQuestaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resolucaoQuestaoRepository.findAll().size();

        // Create the ResolucaoQuestao with an existing ID
        resolucaoQuestao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResolucaoQuestaoMockMvc.perform(post("/api/resolucao-questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolucaoQuestao)))
            .andExpect(status().isBadRequest());

        // Validate the ResolucaoQuestao in the database
        List<ResolucaoQuestao> resolucaoQuestaoList = resolucaoQuestaoRepository.findAll();
        assertThat(resolucaoQuestaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllResolucaoQuestaos() throws Exception {
        // Initialize the database
        resolucaoQuestaoRepository.saveAndFlush(resolucaoQuestao);

        // Get all the resolucaoQuestaoList
        restResolucaoQuestaoMockMvc.perform(get("/api/resolucao-questaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resolucaoQuestao.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHoraInicio").value(hasItem(DEFAULT_DATA_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataHoraFim").value(hasItem(DEFAULT_DATA_HORA_FIM.toString())));
    }
    
    @Test
    @Transactional
    public void getResolucaoQuestao() throws Exception {
        // Initialize the database
        resolucaoQuestaoRepository.saveAndFlush(resolucaoQuestao);

        // Get the resolucaoQuestao
        restResolucaoQuestaoMockMvc.perform(get("/api/resolucao-questaos/{id}", resolucaoQuestao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resolucaoQuestao.getId().intValue()))
            .andExpect(jsonPath("$.dataHoraInicio").value(DEFAULT_DATA_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.dataHoraFim").value(DEFAULT_DATA_HORA_FIM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResolucaoQuestao() throws Exception {
        // Get the resolucaoQuestao
        restResolucaoQuestaoMockMvc.perform(get("/api/resolucao-questaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResolucaoQuestao() throws Exception {
        // Initialize the database
        resolucaoQuestaoRepository.saveAndFlush(resolucaoQuestao);

        int databaseSizeBeforeUpdate = resolucaoQuestaoRepository.findAll().size();

        // Update the resolucaoQuestao
        ResolucaoQuestao updatedResolucaoQuestao = resolucaoQuestaoRepository.findById(resolucaoQuestao.getId()).get();
        // Disconnect from session so that the updates on updatedResolucaoQuestao are not directly saved in db
        em.detach(updatedResolucaoQuestao);
        updatedResolucaoQuestao
            .dataHoraInicio(UPDATED_DATA_HORA_INICIO)
            .dataHoraFim(UPDATED_DATA_HORA_FIM);

        restResolucaoQuestaoMockMvc.perform(put("/api/resolucao-questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResolucaoQuestao)))
            .andExpect(status().isOk());

        // Validate the ResolucaoQuestao in the database
        List<ResolucaoQuestao> resolucaoQuestaoList = resolucaoQuestaoRepository.findAll();
        assertThat(resolucaoQuestaoList).hasSize(databaseSizeBeforeUpdate);
        ResolucaoQuestao testResolucaoQuestao = resolucaoQuestaoList.get(resolucaoQuestaoList.size() - 1);
        assertThat(testResolucaoQuestao.getDataHoraInicio()).isEqualTo(UPDATED_DATA_HORA_INICIO);
        assertThat(testResolucaoQuestao.getDataHoraFim()).isEqualTo(UPDATED_DATA_HORA_FIM);
    }

    @Test
    @Transactional
    public void updateNonExistingResolucaoQuestao() throws Exception {
        int databaseSizeBeforeUpdate = resolucaoQuestaoRepository.findAll().size();

        // Create the ResolucaoQuestao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResolucaoQuestaoMockMvc.perform(put("/api/resolucao-questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolucaoQuestao)))
            .andExpect(status().isBadRequest());

        // Validate the ResolucaoQuestao in the database
        List<ResolucaoQuestao> resolucaoQuestaoList = resolucaoQuestaoRepository.findAll();
        assertThat(resolucaoQuestaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResolucaoQuestao() throws Exception {
        // Initialize the database
        resolucaoQuestaoRepository.saveAndFlush(resolucaoQuestao);

        int databaseSizeBeforeDelete = resolucaoQuestaoRepository.findAll().size();

        // Delete the resolucaoQuestao
        restResolucaoQuestaoMockMvc.perform(delete("/api/resolucao-questaos/{id}", resolucaoQuestao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResolucaoQuestao> resolucaoQuestaoList = resolucaoQuestaoRepository.findAll();
        assertThat(resolucaoQuestaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
