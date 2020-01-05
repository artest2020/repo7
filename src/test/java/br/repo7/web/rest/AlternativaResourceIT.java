package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.Alternativa;
import br.repo7.domain.Frase;
import br.repo7.repository.AlternativaRepository;
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
 * Integration tests for the {@link AlternativaResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class AlternativaResourceIT {

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    @Autowired
    private AlternativaRepository alternativaRepository;

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

    private MockMvc restAlternativaMockMvc;

    private Alternativa alternativa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlternativaResource alternativaResource = new AlternativaResource(alternativaRepository);
        this.restAlternativaMockMvc = MockMvcBuilders.standaloneSetup(alternativaResource)
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
    public static Alternativa createEntity(EntityManager em) {
        Alternativa alternativa = new Alternativa()
            .ordem(DEFAULT_ORDEM);
        // Add required entity
        Frase frase;
        if (TestUtil.findAll(em, Frase.class).isEmpty()) {
            frase = FraseResourceIT.createEntity(em);
            em.persist(frase);
            em.flush();
        } else {
            frase = TestUtil.findAll(em, Frase.class).get(0);
        }
        alternativa.getListaFrases().add(frase);
        return alternativa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alternativa createUpdatedEntity(EntityManager em) {
        Alternativa alternativa = new Alternativa()
            .ordem(UPDATED_ORDEM);
        // Add required entity
        Frase frase;
        if (TestUtil.findAll(em, Frase.class).isEmpty()) {
            frase = FraseResourceIT.createUpdatedEntity(em);
            em.persist(frase);
            em.flush();
        } else {
            frase = TestUtil.findAll(em, Frase.class).get(0);
        }
        alternativa.getListaFrases().add(frase);
        return alternativa;
    }

    @BeforeEach
    public void initTest() {
        alternativa = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlternativa() throws Exception {
        int databaseSizeBeforeCreate = alternativaRepository.findAll().size();

        // Create the Alternativa
        restAlternativaMockMvc.perform(post("/api/alternativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alternativa)))
            .andExpect(status().isCreated());

        // Validate the Alternativa in the database
        List<Alternativa> alternativaList = alternativaRepository.findAll();
        assertThat(alternativaList).hasSize(databaseSizeBeforeCreate + 1);
        Alternativa testAlternativa = alternativaList.get(alternativaList.size() - 1);
        assertThat(testAlternativa.getOrdem()).isEqualTo(DEFAULT_ORDEM);
    }

    @Test
    @Transactional
    public void createAlternativaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alternativaRepository.findAll().size();

        // Create the Alternativa with an existing ID
        alternativa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlternativaMockMvc.perform(post("/api/alternativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alternativa)))
            .andExpect(status().isBadRequest());

        // Validate the Alternativa in the database
        List<Alternativa> alternativaList = alternativaRepository.findAll();
        assertThat(alternativaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlternativas() throws Exception {
        // Initialize the database
        alternativaRepository.saveAndFlush(alternativa);

        // Get all the alternativaList
        restAlternativaMockMvc.perform(get("/api/alternativas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alternativa.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)));
    }
    
    @Test
    @Transactional
    public void getAlternativa() throws Exception {
        // Initialize the database
        alternativaRepository.saveAndFlush(alternativa);

        // Get the alternativa
        restAlternativaMockMvc.perform(get("/api/alternativas/{id}", alternativa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alternativa.getId().intValue()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM));
    }

    @Test
    @Transactional
    public void getNonExistingAlternativa() throws Exception {
        // Get the alternativa
        restAlternativaMockMvc.perform(get("/api/alternativas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlternativa() throws Exception {
        // Initialize the database
        alternativaRepository.saveAndFlush(alternativa);

        int databaseSizeBeforeUpdate = alternativaRepository.findAll().size();

        // Update the alternativa
        Alternativa updatedAlternativa = alternativaRepository.findById(alternativa.getId()).get();
        // Disconnect from session so that the updates on updatedAlternativa are not directly saved in db
        em.detach(updatedAlternativa);
        updatedAlternativa
            .ordem(UPDATED_ORDEM);

        restAlternativaMockMvc.perform(put("/api/alternativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlternativa)))
            .andExpect(status().isOk());

        // Validate the Alternativa in the database
        List<Alternativa> alternativaList = alternativaRepository.findAll();
        assertThat(alternativaList).hasSize(databaseSizeBeforeUpdate);
        Alternativa testAlternativa = alternativaList.get(alternativaList.size() - 1);
        assertThat(testAlternativa.getOrdem()).isEqualTo(UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void updateNonExistingAlternativa() throws Exception {
        int databaseSizeBeforeUpdate = alternativaRepository.findAll().size();

        // Create the Alternativa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlternativaMockMvc.perform(put("/api/alternativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alternativa)))
            .andExpect(status().isBadRequest());

        // Validate the Alternativa in the database
        List<Alternativa> alternativaList = alternativaRepository.findAll();
        assertThat(alternativaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlternativa() throws Exception {
        // Initialize the database
        alternativaRepository.saveAndFlush(alternativa);

        int databaseSizeBeforeDelete = alternativaRepository.findAll().size();

        // Delete the alternativa
        restAlternativaMockMvc.perform(delete("/api/alternativas/{id}", alternativa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alternativa> alternativaList = alternativaRepository.findAll();
        assertThat(alternativaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
