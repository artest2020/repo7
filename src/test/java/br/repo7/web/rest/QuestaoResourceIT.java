package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.Questao;
import br.repo7.domain.Enunciado;
import br.repo7.domain.Pergunta;
import br.repo7.domain.Alternativa;
import br.repo7.repository.QuestaoRepository;
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
 * Integration tests for the {@link QuestaoResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class QuestaoResourceIT {

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    @Autowired
    private QuestaoRepository questaoRepository;

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

    private MockMvc restQuestaoMockMvc;

    private Questao questao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestaoResource questaoResource = new QuestaoResource(questaoRepository);
        this.restQuestaoMockMvc = MockMvcBuilders.standaloneSetup(questaoResource)
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
    public static Questao createEntity(EntityManager em) {
        Questao questao = new Questao()
            .ordem(DEFAULT_ORDEM);
        // Add required entity
        Enunciado enunciado;
        if (TestUtil.findAll(em, Enunciado.class).isEmpty()) {
            enunciado = EnunciadoResourceIT.createEntity(em);
            em.persist(enunciado);
            em.flush();
        } else {
            enunciado = TestUtil.findAll(em, Enunciado.class).get(0);
        }
        questao.setEnunciado(enunciado);
        // Add required entity
        Pergunta pergunta;
        if (TestUtil.findAll(em, Pergunta.class).isEmpty()) {
            pergunta = PerguntaResourceIT.createEntity(em);
            em.persist(pergunta);
            em.flush();
        } else {
            pergunta = TestUtil.findAll(em, Pergunta.class).get(0);
        }
        questao.setPergunta(pergunta);
        // Add required entity
        Alternativa alternativa;
        if (TestUtil.findAll(em, Alternativa.class).isEmpty()) {
            alternativa = AlternativaResourceIT.createEntity(em);
            em.persist(alternativa);
            em.flush();
        } else {
            alternativa = TestUtil.findAll(em, Alternativa.class).get(0);
        }
        questao.getListaAlternativas().add(alternativa);
        return questao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questao createUpdatedEntity(EntityManager em) {
        Questao questao = new Questao()
            .ordem(UPDATED_ORDEM);
        // Add required entity
        Enunciado enunciado;
        if (TestUtil.findAll(em, Enunciado.class).isEmpty()) {
            enunciado = EnunciadoResourceIT.createUpdatedEntity(em);
            em.persist(enunciado);
            em.flush();
        } else {
            enunciado = TestUtil.findAll(em, Enunciado.class).get(0);
        }
        questao.setEnunciado(enunciado);
        // Add required entity
        Pergunta pergunta;
        if (TestUtil.findAll(em, Pergunta.class).isEmpty()) {
            pergunta = PerguntaResourceIT.createUpdatedEntity(em);
            em.persist(pergunta);
            em.flush();
        } else {
            pergunta = TestUtil.findAll(em, Pergunta.class).get(0);
        }
        questao.setPergunta(pergunta);
        // Add required entity
        Alternativa alternativa;
        if (TestUtil.findAll(em, Alternativa.class).isEmpty()) {
            alternativa = AlternativaResourceIT.createUpdatedEntity(em);
            em.persist(alternativa);
            em.flush();
        } else {
            alternativa = TestUtil.findAll(em, Alternativa.class).get(0);
        }
        questao.getListaAlternativas().add(alternativa);
        return questao;
    }

    @BeforeEach
    public void initTest() {
        questao = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestao() throws Exception {
        int databaseSizeBeforeCreate = questaoRepository.findAll().size();

        // Create the Questao
        restQuestaoMockMvc.perform(post("/api/questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questao)))
            .andExpect(status().isCreated());

        // Validate the Questao in the database
        List<Questao> questaoList = questaoRepository.findAll();
        assertThat(questaoList).hasSize(databaseSizeBeforeCreate + 1);
        Questao testQuestao = questaoList.get(questaoList.size() - 1);
        assertThat(testQuestao.getOrdem()).isEqualTo(DEFAULT_ORDEM);
    }

    @Test
    @Transactional
    public void createQuestaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questaoRepository.findAll().size();

        // Create the Questao with an existing ID
        questao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestaoMockMvc.perform(post("/api/questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questao)))
            .andExpect(status().isBadRequest());

        // Validate the Questao in the database
        List<Questao> questaoList = questaoRepository.findAll();
        assertThat(questaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuestaos() throws Exception {
        // Initialize the database
        questaoRepository.saveAndFlush(questao);

        // Get all the questaoList
        restQuestaoMockMvc.perform(get("/api/questaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questao.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)));
    }
    
    @Test
    @Transactional
    public void getQuestao() throws Exception {
        // Initialize the database
        questaoRepository.saveAndFlush(questao);

        // Get the questao
        restQuestaoMockMvc.perform(get("/api/questaos/{id}", questao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questao.getId().intValue()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM));
    }

    @Test
    @Transactional
    public void getNonExistingQuestao() throws Exception {
        // Get the questao
        restQuestaoMockMvc.perform(get("/api/questaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestao() throws Exception {
        // Initialize the database
        questaoRepository.saveAndFlush(questao);

        int databaseSizeBeforeUpdate = questaoRepository.findAll().size();

        // Update the questao
        Questao updatedQuestao = questaoRepository.findById(questao.getId()).get();
        // Disconnect from session so that the updates on updatedQuestao are not directly saved in db
        em.detach(updatedQuestao);
        updatedQuestao
            .ordem(UPDATED_ORDEM);

        restQuestaoMockMvc.perform(put("/api/questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestao)))
            .andExpect(status().isOk());

        // Validate the Questao in the database
        List<Questao> questaoList = questaoRepository.findAll();
        assertThat(questaoList).hasSize(databaseSizeBeforeUpdate);
        Questao testQuestao = questaoList.get(questaoList.size() - 1);
        assertThat(testQuestao.getOrdem()).isEqualTo(UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestao() throws Exception {
        int databaseSizeBeforeUpdate = questaoRepository.findAll().size();

        // Create the Questao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestaoMockMvc.perform(put("/api/questaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questao)))
            .andExpect(status().isBadRequest());

        // Validate the Questao in the database
        List<Questao> questaoList = questaoRepository.findAll();
        assertThat(questaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestao() throws Exception {
        // Initialize the database
        questaoRepository.saveAndFlush(questao);

        int databaseSizeBeforeDelete = questaoRepository.findAll().size();

        // Delete the questao
        restQuestaoMockMvc.perform(delete("/api/questaos/{id}", questao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Questao> questaoList = questaoRepository.findAll();
        assertThat(questaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
