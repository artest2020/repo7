package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.Frase;
import br.repo7.repository.FraseRepository;
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

import br.repo7.domain.enumeration.TipoVF;
/**
 * Integration tests for the {@link FraseResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class FraseResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final TipoVF DEFAULT_VERDADEIRA = TipoVF.V;
    private static final TipoVF UPDATED_VERDADEIRA = TipoVF.F;

    @Autowired
    private FraseRepository fraseRepository;

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

    private MockMvc restFraseMockMvc;

    private Frase frase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FraseResource fraseResource = new FraseResource(fraseRepository);
        this.restFraseMockMvc = MockMvcBuilders.standaloneSetup(fraseResource)
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
    public static Frase createEntity(EntityManager em) {
        Frase frase = new Frase()
            .descricao(DEFAULT_DESCRICAO)
            .verdadeira(DEFAULT_VERDADEIRA);
        return frase;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frase createUpdatedEntity(EntityManager em) {
        Frase frase = new Frase()
            .descricao(UPDATED_DESCRICAO)
            .verdadeira(UPDATED_VERDADEIRA);
        return frase;
    }

    @BeforeEach
    public void initTest() {
        frase = createEntity(em);
    }

    @Test
    @Transactional
    public void createFrase() throws Exception {
        int databaseSizeBeforeCreate = fraseRepository.findAll().size();

        // Create the Frase
        restFraseMockMvc.perform(post("/api/frases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(frase)))
            .andExpect(status().isCreated());

        // Validate the Frase in the database
        List<Frase> fraseList = fraseRepository.findAll();
        assertThat(fraseList).hasSize(databaseSizeBeforeCreate + 1);
        Frase testFrase = fraseList.get(fraseList.size() - 1);
        assertThat(testFrase.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testFrase.getVerdadeira()).isEqualTo(DEFAULT_VERDADEIRA);
    }

    @Test
    @Transactional
    public void createFraseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fraseRepository.findAll().size();

        // Create the Frase with an existing ID
        frase.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraseMockMvc.perform(post("/api/frases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(frase)))
            .andExpect(status().isBadRequest());

        // Validate the Frase in the database
        List<Frase> fraseList = fraseRepository.findAll();
        assertThat(fraseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFrases() throws Exception {
        // Initialize the database
        fraseRepository.saveAndFlush(frase);

        // Get all the fraseList
        restFraseMockMvc.perform(get("/api/frases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frase.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].verdadeira").value(hasItem(DEFAULT_VERDADEIRA.toString())));
    }
    
    @Test
    @Transactional
    public void getFrase() throws Exception {
        // Initialize the database
        fraseRepository.saveAndFlush(frase);

        // Get the frase
        restFraseMockMvc.perform(get("/api/frases/{id}", frase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(frase.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.verdadeira").value(DEFAULT_VERDADEIRA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFrase() throws Exception {
        // Get the frase
        restFraseMockMvc.perform(get("/api/frases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFrase() throws Exception {
        // Initialize the database
        fraseRepository.saveAndFlush(frase);

        int databaseSizeBeforeUpdate = fraseRepository.findAll().size();

        // Update the frase
        Frase updatedFrase = fraseRepository.findById(frase.getId()).get();
        // Disconnect from session so that the updates on updatedFrase are not directly saved in db
        em.detach(updatedFrase);
        updatedFrase
            .descricao(UPDATED_DESCRICAO)
            .verdadeira(UPDATED_VERDADEIRA);

        restFraseMockMvc.perform(put("/api/frases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFrase)))
            .andExpect(status().isOk());

        // Validate the Frase in the database
        List<Frase> fraseList = fraseRepository.findAll();
        assertThat(fraseList).hasSize(databaseSizeBeforeUpdate);
        Frase testFrase = fraseList.get(fraseList.size() - 1);
        assertThat(testFrase.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testFrase.getVerdadeira()).isEqualTo(UPDATED_VERDADEIRA);
    }

    @Test
    @Transactional
    public void updateNonExistingFrase() throws Exception {
        int databaseSizeBeforeUpdate = fraseRepository.findAll().size();

        // Create the Frase

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraseMockMvc.perform(put("/api/frases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(frase)))
            .andExpect(status().isBadRequest());

        // Validate the Frase in the database
        List<Frase> fraseList = fraseRepository.findAll();
        assertThat(fraseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFrase() throws Exception {
        // Initialize the database
        fraseRepository.saveAndFlush(frase);

        int databaseSizeBeforeDelete = fraseRepository.findAll().size();

        // Delete the frase
        restFraseMockMvc.perform(delete("/api/frases/{id}", frase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Frase> fraseList = fraseRepository.findAll();
        assertThat(fraseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
