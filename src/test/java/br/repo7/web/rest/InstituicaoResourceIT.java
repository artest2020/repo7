package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.Instituicao;
import br.repo7.repository.InstituicaoRepository;
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
 * Integration tests for the {@link InstituicaoResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class InstituicaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    @Autowired
    private InstituicaoRepository instituicaoRepository;

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

    private MockMvc restInstituicaoMockMvc;

    private Instituicao instituicao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstituicaoResource instituicaoResource = new InstituicaoResource(instituicaoRepository);
        this.restInstituicaoMockMvc = MockMvcBuilders.standaloneSetup(instituicaoResource)
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
    public static Instituicao createEntity(EntityManager em) {
        Instituicao instituicao = new Instituicao()
            .nome(DEFAULT_NOME)
            .sigla(DEFAULT_SIGLA);
        return instituicao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instituicao createUpdatedEntity(EntityManager em) {
        Instituicao instituicao = new Instituicao()
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA);
        return instituicao;
    }

    @BeforeEach
    public void initTest() {
        instituicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstituicao() throws Exception {
        int databaseSizeBeforeCreate = instituicaoRepository.findAll().size();

        // Create the Instituicao
        restInstituicaoMockMvc.perform(post("/api/instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituicao)))
            .andExpect(status().isCreated());

        // Validate the Instituicao in the database
        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeCreate + 1);
        Instituicao testInstituicao = instituicaoList.get(instituicaoList.size() - 1);
        assertThat(testInstituicao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testInstituicao.getSigla()).isEqualTo(DEFAULT_SIGLA);
    }

    @Test
    @Transactional
    public void createInstituicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instituicaoRepository.findAll().size();

        // Create the Instituicao with an existing ID
        instituicao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstituicaoMockMvc.perform(post("/api/instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituicao)))
            .andExpect(status().isBadRequest());

        // Validate the Instituicao in the database
        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituicaoRepository.findAll().size();
        // set the field null
        instituicao.setNome(null);

        // Create the Instituicao, which fails.

        restInstituicaoMockMvc.perform(post("/api/instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituicao)))
            .andExpect(status().isBadRequest());

        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstituicaos() throws Exception {
        // Initialize the database
        instituicaoRepository.saveAndFlush(instituicao);

        // Get all the instituicaoList
        restInstituicaoMockMvc.perform(get("/api/instituicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instituicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }
    
    @Test
    @Transactional
    public void getInstituicao() throws Exception {
        // Initialize the database
        instituicaoRepository.saveAndFlush(instituicao);

        // Get the instituicao
        restInstituicaoMockMvc.perform(get("/api/instituicaos/{id}", instituicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(instituicao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }

    @Test
    @Transactional
    public void getNonExistingInstituicao() throws Exception {
        // Get the instituicao
        restInstituicaoMockMvc.perform(get("/api/instituicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstituicao() throws Exception {
        // Initialize the database
        instituicaoRepository.saveAndFlush(instituicao);

        int databaseSizeBeforeUpdate = instituicaoRepository.findAll().size();

        // Update the instituicao
        Instituicao updatedInstituicao = instituicaoRepository.findById(instituicao.getId()).get();
        // Disconnect from session so that the updates on updatedInstituicao are not directly saved in db
        em.detach(updatedInstituicao);
        updatedInstituicao
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA);

        restInstituicaoMockMvc.perform(put("/api/instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstituicao)))
            .andExpect(status().isOk());

        // Validate the Instituicao in the database
        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeUpdate);
        Instituicao testInstituicao = instituicaoList.get(instituicaoList.size() - 1);
        assertThat(testInstituicao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testInstituicao.getSigla()).isEqualTo(UPDATED_SIGLA);
    }

    @Test
    @Transactional
    public void updateNonExistingInstituicao() throws Exception {
        int databaseSizeBeforeUpdate = instituicaoRepository.findAll().size();

        // Create the Instituicao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstituicaoMockMvc.perform(put("/api/instituicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instituicao)))
            .andExpect(status().isBadRequest());

        // Validate the Instituicao in the database
        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstituicao() throws Exception {
        // Initialize the database
        instituicaoRepository.saveAndFlush(instituicao);

        int databaseSizeBeforeDelete = instituicaoRepository.findAll().size();

        // Delete the instituicao
        restInstituicaoMockMvc.perform(delete("/api/instituicaos/{id}", instituicao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Instituicao> instituicaoList = instituicaoRepository.findAll();
        assertThat(instituicaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
