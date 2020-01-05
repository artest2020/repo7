package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.Edital;
import br.repo7.repository.EditalRepository;
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
import java.util.List;

import static br.repo7.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EditalResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class EditalResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private EditalRepository editalRepository;

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

    private MockMvc restEditalMockMvc;

    private Edital edital;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EditalResource editalResource = new EditalResource(editalRepository);
        this.restEditalMockMvc = MockMvcBuilders.standaloneSetup(editalResource)
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
    public static Edital createEntity(EntityManager em) {
        Edital edital = new Edital()
            .descricao(DEFAULT_DESCRICAO);
        return edital;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Edital createUpdatedEntity(EntityManager em) {
        Edital edital = new Edital()
            .descricao(UPDATED_DESCRICAO);
        return edital;
    }

    @BeforeEach
    public void initTest() {
        edital = createEntity(em);
    }

    @Test
    @Transactional
    public void createEdital() throws Exception {
        int databaseSizeBeforeCreate = editalRepository.findAll().size();

        // Create the Edital
        restEditalMockMvc.perform(post("/api/editals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edital)))
            .andExpect(status().isCreated());

        // Validate the Edital in the database
        List<Edital> editalList = editalRepository.findAll();
        assertThat(editalList).hasSize(databaseSizeBeforeCreate + 1);
        Edital testEdital = editalList.get(editalList.size() - 1);
        assertThat(testEdital.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createEditalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = editalRepository.findAll().size();

        // Create the Edital with an existing ID
        edital.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEditalMockMvc.perform(post("/api/editals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edital)))
            .andExpect(status().isBadRequest());

        // Validate the Edital in the database
        List<Edital> editalList = editalRepository.findAll();
        assertThat(editalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEditals() throws Exception {
        // Initialize the database
        editalRepository.saveAndFlush(edital);

        // Get all the editalList
        restEditalMockMvc.perform(get("/api/editals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(edital.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getEdital() throws Exception {
        // Initialize the database
        editalRepository.saveAndFlush(edital);

        // Get the edital
        restEditalMockMvc.perform(get("/api/editals/{id}", edital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(edital.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingEdital() throws Exception {
        // Get the edital
        restEditalMockMvc.perform(get("/api/editals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEdital() throws Exception {
        // Initialize the database
        editalRepository.saveAndFlush(edital);

        int databaseSizeBeforeUpdate = editalRepository.findAll().size();

        // Update the edital
        Edital updatedEdital = editalRepository.findById(edital.getId()).get();
        // Disconnect from session so that the updates on updatedEdital are not directly saved in db
        em.detach(updatedEdital);
        updatedEdital
            .descricao(UPDATED_DESCRICAO);

        restEditalMockMvc.perform(put("/api/editals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEdital)))
            .andExpect(status().isOk());

        // Validate the Edital in the database
        List<Edital> editalList = editalRepository.findAll();
        assertThat(editalList).hasSize(databaseSizeBeforeUpdate);
        Edital testEdital = editalList.get(editalList.size() - 1);
        assertThat(testEdital.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingEdital() throws Exception {
        int databaseSizeBeforeUpdate = editalRepository.findAll().size();

        // Create the Edital

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEditalMockMvc.perform(put("/api/editals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(edital)))
            .andExpect(status().isBadRequest());

        // Validate the Edital in the database
        List<Edital> editalList = editalRepository.findAll();
        assertThat(editalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEdital() throws Exception {
        // Initialize the database
        editalRepository.saveAndFlush(edital);

        int databaseSizeBeforeDelete = editalRepository.findAll().size();

        // Delete the edital
        restEditalMockMvc.perform(delete("/api/editals/{id}", edital.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Edital> editalList = editalRepository.findAll();
        assertThat(editalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
