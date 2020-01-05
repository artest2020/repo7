package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.Pergunta;
import br.repo7.repository.PerguntaRepository;
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
 * Integration tests for the {@link PerguntaResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class PerguntaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private PerguntaRepository perguntaRepository;

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

    private MockMvc restPerguntaMockMvc;

    private Pergunta pergunta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerguntaResource perguntaResource = new PerguntaResource(perguntaRepository);
        this.restPerguntaMockMvc = MockMvcBuilders.standaloneSetup(perguntaResource)
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
    public static Pergunta createEntity(EntityManager em) {
        Pergunta pergunta = new Pergunta()
            .descricao(DEFAULT_DESCRICAO);
        return pergunta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pergunta createUpdatedEntity(EntityManager em) {
        Pergunta pergunta = new Pergunta()
            .descricao(UPDATED_DESCRICAO);
        return pergunta;
    }

    @BeforeEach
    public void initTest() {
        pergunta = createEntity(em);
    }

    @Test
    @Transactional
    public void createPergunta() throws Exception {
        int databaseSizeBeforeCreate = perguntaRepository.findAll().size();

        // Create the Pergunta
        restPerguntaMockMvc.perform(post("/api/perguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pergunta)))
            .andExpect(status().isCreated());

        // Validate the Pergunta in the database
        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeCreate + 1);
        Pergunta testPergunta = perguntaList.get(perguntaList.size() - 1);
        assertThat(testPergunta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createPerguntaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perguntaRepository.findAll().size();

        // Create the Pergunta with an existing ID
        pergunta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerguntaMockMvc.perform(post("/api/perguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pergunta)))
            .andExpect(status().isBadRequest());

        // Validate the Pergunta in the database
        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPerguntas() throws Exception {
        // Initialize the database
        perguntaRepository.saveAndFlush(pergunta);

        // Get all the perguntaList
        restPerguntaMockMvc.perform(get("/api/perguntas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pergunta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getPergunta() throws Exception {
        // Initialize the database
        perguntaRepository.saveAndFlush(pergunta);

        // Get the pergunta
        restPerguntaMockMvc.perform(get("/api/perguntas/{id}", pergunta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pergunta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingPergunta() throws Exception {
        // Get the pergunta
        restPerguntaMockMvc.perform(get("/api/perguntas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePergunta() throws Exception {
        // Initialize the database
        perguntaRepository.saveAndFlush(pergunta);

        int databaseSizeBeforeUpdate = perguntaRepository.findAll().size();

        // Update the pergunta
        Pergunta updatedPergunta = perguntaRepository.findById(pergunta.getId()).get();
        // Disconnect from session so that the updates on updatedPergunta are not directly saved in db
        em.detach(updatedPergunta);
        updatedPergunta
            .descricao(UPDATED_DESCRICAO);

        restPerguntaMockMvc.perform(put("/api/perguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPergunta)))
            .andExpect(status().isOk());

        // Validate the Pergunta in the database
        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeUpdate);
        Pergunta testPergunta = perguntaList.get(perguntaList.size() - 1);
        assertThat(testPergunta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingPergunta() throws Exception {
        int databaseSizeBeforeUpdate = perguntaRepository.findAll().size();

        // Create the Pergunta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerguntaMockMvc.perform(put("/api/perguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pergunta)))
            .andExpect(status().isBadRequest());

        // Validate the Pergunta in the database
        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePergunta() throws Exception {
        // Initialize the database
        perguntaRepository.saveAndFlush(pergunta);

        int databaseSizeBeforeDelete = perguntaRepository.findAll().size();

        // Delete the pergunta
        restPerguntaMockMvc.perform(delete("/api/perguntas/{id}", pergunta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pergunta> perguntaList = perguntaRepository.findAll();
        assertThat(perguntaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
