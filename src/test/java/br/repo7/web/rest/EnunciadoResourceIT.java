package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.Enunciado;
import br.repo7.repository.EnunciadoRepository;
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
 * Integration tests for the {@link EnunciadoResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class EnunciadoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private EnunciadoRepository enunciadoRepository;

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

    private MockMvc restEnunciadoMockMvc;

    private Enunciado enunciado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnunciadoResource enunciadoResource = new EnunciadoResource(enunciadoRepository);
        this.restEnunciadoMockMvc = MockMvcBuilders.standaloneSetup(enunciadoResource)
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
    public static Enunciado createEntity(EntityManager em) {
        Enunciado enunciado = new Enunciado()
            .descricao(DEFAULT_DESCRICAO);
        return enunciado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enunciado createUpdatedEntity(EntityManager em) {
        Enunciado enunciado = new Enunciado()
            .descricao(UPDATED_DESCRICAO);
        return enunciado;
    }

    @BeforeEach
    public void initTest() {
        enunciado = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnunciado() throws Exception {
        int databaseSizeBeforeCreate = enunciadoRepository.findAll().size();

        // Create the Enunciado
        restEnunciadoMockMvc.perform(post("/api/enunciados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enunciado)))
            .andExpect(status().isCreated());

        // Validate the Enunciado in the database
        List<Enunciado> enunciadoList = enunciadoRepository.findAll();
        assertThat(enunciadoList).hasSize(databaseSizeBeforeCreate + 1);
        Enunciado testEnunciado = enunciadoList.get(enunciadoList.size() - 1);
        assertThat(testEnunciado.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createEnunciadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enunciadoRepository.findAll().size();

        // Create the Enunciado with an existing ID
        enunciado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnunciadoMockMvc.perform(post("/api/enunciados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enunciado)))
            .andExpect(status().isBadRequest());

        // Validate the Enunciado in the database
        List<Enunciado> enunciadoList = enunciadoRepository.findAll();
        assertThat(enunciadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEnunciados() throws Exception {
        // Initialize the database
        enunciadoRepository.saveAndFlush(enunciado);

        // Get all the enunciadoList
        restEnunciadoMockMvc.perform(get("/api/enunciados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enunciado.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getEnunciado() throws Exception {
        // Initialize the database
        enunciadoRepository.saveAndFlush(enunciado);

        // Get the enunciado
        restEnunciadoMockMvc.perform(get("/api/enunciados/{id}", enunciado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enunciado.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingEnunciado() throws Exception {
        // Get the enunciado
        restEnunciadoMockMvc.perform(get("/api/enunciados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnunciado() throws Exception {
        // Initialize the database
        enunciadoRepository.saveAndFlush(enunciado);

        int databaseSizeBeforeUpdate = enunciadoRepository.findAll().size();

        // Update the enunciado
        Enunciado updatedEnunciado = enunciadoRepository.findById(enunciado.getId()).get();
        // Disconnect from session so that the updates on updatedEnunciado are not directly saved in db
        em.detach(updatedEnunciado);
        updatedEnunciado
            .descricao(UPDATED_DESCRICAO);

        restEnunciadoMockMvc.perform(put("/api/enunciados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnunciado)))
            .andExpect(status().isOk());

        // Validate the Enunciado in the database
        List<Enunciado> enunciadoList = enunciadoRepository.findAll();
        assertThat(enunciadoList).hasSize(databaseSizeBeforeUpdate);
        Enunciado testEnunciado = enunciadoList.get(enunciadoList.size() - 1);
        assertThat(testEnunciado.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingEnunciado() throws Exception {
        int databaseSizeBeforeUpdate = enunciadoRepository.findAll().size();

        // Create the Enunciado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnunciadoMockMvc.perform(put("/api/enunciados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enunciado)))
            .andExpect(status().isBadRequest());

        // Validate the Enunciado in the database
        List<Enunciado> enunciadoList = enunciadoRepository.findAll();
        assertThat(enunciadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnunciado() throws Exception {
        // Initialize the database
        enunciadoRepository.saveAndFlush(enunciado);

        int databaseSizeBeforeDelete = enunciadoRepository.findAll().size();

        // Delete the enunciado
        restEnunciadoMockMvc.perform(delete("/api/enunciados/{id}", enunciado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enunciado> enunciadoList = enunciadoRepository.findAll();
        assertThat(enunciadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
