package br.repo7.web.rest;

import br.repo7.Repo7App;
import br.repo7.domain.Resposta;
import br.repo7.repository.RespostaRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static br.repo7.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.repo7.domain.enumeration.TipoResposta;
import br.repo7.domain.enumeration.TipoVF;
/**
 * Integration tests for the {@link RespostaResource} REST controller.
 */
@SpringBootTest(classes = Repo7App.class)
public class RespostaResourceIT {

    private static final TipoResposta DEFAULT_TIPO = TipoResposta.V_F;
    private static final TipoResposta UPDATED_TIPO = TipoResposta.TEXTO;

    private static final TipoVF DEFAULT_VALOR_VF = TipoVF.V;
    private static final TipoVF UPDATED_VALOR_VF = TipoVF.F;

    private static final String DEFAULT_VALOR_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_TEXTO = "BBBBBBBBBB";

    private static final Long DEFAULT_VALOR_N = 1L;
    private static final Long UPDATED_VALOR_N = 2L;

    private static final Long DEFAULT_VALOR_Z = 1L;
    private static final Long UPDATED_VALOR_Z = 2L;

    private static final BigDecimal DEFAULT_VALOR_Q = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_Q = new BigDecimal(2);

    @Autowired
    private RespostaRepository respostaRepository;

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

    private MockMvc restRespostaMockMvc;

    private Resposta resposta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RespostaResource respostaResource = new RespostaResource(respostaRepository);
        this.restRespostaMockMvc = MockMvcBuilders.standaloneSetup(respostaResource)
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
    public static Resposta createEntity(EntityManager em) {
        Resposta resposta = new Resposta()
            .tipo(DEFAULT_TIPO)
            .valorVF(DEFAULT_VALOR_VF)
            .valorTexto(DEFAULT_VALOR_TEXTO)
            .valorN(DEFAULT_VALOR_N)
            .valorZ(DEFAULT_VALOR_Z)
            .valorQ(DEFAULT_VALOR_Q);
        return resposta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resposta createUpdatedEntity(EntityManager em) {
        Resposta resposta = new Resposta()
            .tipo(UPDATED_TIPO)
            .valorVF(UPDATED_VALOR_VF)
            .valorTexto(UPDATED_VALOR_TEXTO)
            .valorN(UPDATED_VALOR_N)
            .valorZ(UPDATED_VALOR_Z)
            .valorQ(UPDATED_VALOR_Q);
        return resposta;
    }

    @BeforeEach
    public void initTest() {
        resposta = createEntity(em);
    }

    @Test
    @Transactional
    public void createResposta() throws Exception {
        int databaseSizeBeforeCreate = respostaRepository.findAll().size();

        // Create the Resposta
        restRespostaMockMvc.perform(post("/api/respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resposta)))
            .andExpect(status().isCreated());

        // Validate the Resposta in the database
        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeCreate + 1);
        Resposta testResposta = respostaList.get(respostaList.size() - 1);
        assertThat(testResposta.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testResposta.getValorVF()).isEqualTo(DEFAULT_VALOR_VF);
        assertThat(testResposta.getValorTexto()).isEqualTo(DEFAULT_VALOR_TEXTO);
        assertThat(testResposta.getValorN()).isEqualTo(DEFAULT_VALOR_N);
        assertThat(testResposta.getValorZ()).isEqualTo(DEFAULT_VALOR_Z);
        assertThat(testResposta.getValorQ()).isEqualTo(DEFAULT_VALOR_Q);
    }

    @Test
    @Transactional
    public void createRespostaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = respostaRepository.findAll().size();

        // Create the Resposta with an existing ID
        resposta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRespostaMockMvc.perform(post("/api/respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resposta)))
            .andExpect(status().isBadRequest());

        // Validate the Resposta in the database
        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = respostaRepository.findAll().size();
        // set the field null
        resposta.setTipo(null);

        // Create the Resposta, which fails.

        restRespostaMockMvc.perform(post("/api/respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resposta)))
            .andExpect(status().isBadRequest());

        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRespostas() throws Exception {
        // Initialize the database
        respostaRepository.saveAndFlush(resposta);

        // Get all the respostaList
        restRespostaMockMvc.perform(get("/api/respostas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resposta.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].valorVF").value(hasItem(DEFAULT_VALOR_VF.toString())))
            .andExpect(jsonPath("$.[*].valorTexto").value(hasItem(DEFAULT_VALOR_TEXTO)))
            .andExpect(jsonPath("$.[*].valorN").value(hasItem(DEFAULT_VALOR_N.intValue())))
            .andExpect(jsonPath("$.[*].valorZ").value(hasItem(DEFAULT_VALOR_Z.intValue())))
            .andExpect(jsonPath("$.[*].valorQ").value(hasItem(DEFAULT_VALOR_Q.intValue())));
    }
    
    @Test
    @Transactional
    public void getResposta() throws Exception {
        // Initialize the database
        respostaRepository.saveAndFlush(resposta);

        // Get the resposta
        restRespostaMockMvc.perform(get("/api/respostas/{id}", resposta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resposta.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.valorVF").value(DEFAULT_VALOR_VF.toString()))
            .andExpect(jsonPath("$.valorTexto").value(DEFAULT_VALOR_TEXTO))
            .andExpect(jsonPath("$.valorN").value(DEFAULT_VALOR_N.intValue()))
            .andExpect(jsonPath("$.valorZ").value(DEFAULT_VALOR_Z.intValue()))
            .andExpect(jsonPath("$.valorQ").value(DEFAULT_VALOR_Q.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingResposta() throws Exception {
        // Get the resposta
        restRespostaMockMvc.perform(get("/api/respostas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResposta() throws Exception {
        // Initialize the database
        respostaRepository.saveAndFlush(resposta);

        int databaseSizeBeforeUpdate = respostaRepository.findAll().size();

        // Update the resposta
        Resposta updatedResposta = respostaRepository.findById(resposta.getId()).get();
        // Disconnect from session so that the updates on updatedResposta are not directly saved in db
        em.detach(updatedResposta);
        updatedResposta
            .tipo(UPDATED_TIPO)
            .valorVF(UPDATED_VALOR_VF)
            .valorTexto(UPDATED_VALOR_TEXTO)
            .valorN(UPDATED_VALOR_N)
            .valorZ(UPDATED_VALOR_Z)
            .valorQ(UPDATED_VALOR_Q);

        restRespostaMockMvc.perform(put("/api/respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResposta)))
            .andExpect(status().isOk());

        // Validate the Resposta in the database
        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeUpdate);
        Resposta testResposta = respostaList.get(respostaList.size() - 1);
        assertThat(testResposta.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testResposta.getValorVF()).isEqualTo(UPDATED_VALOR_VF);
        assertThat(testResposta.getValorTexto()).isEqualTo(UPDATED_VALOR_TEXTO);
        assertThat(testResposta.getValorN()).isEqualTo(UPDATED_VALOR_N);
        assertThat(testResposta.getValorZ()).isEqualTo(UPDATED_VALOR_Z);
        assertThat(testResposta.getValorQ()).isEqualTo(UPDATED_VALOR_Q);
    }

    @Test
    @Transactional
    public void updateNonExistingResposta() throws Exception {
        int databaseSizeBeforeUpdate = respostaRepository.findAll().size();

        // Create the Resposta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRespostaMockMvc.perform(put("/api/respostas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resposta)))
            .andExpect(status().isBadRequest());

        // Validate the Resposta in the database
        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResposta() throws Exception {
        // Initialize the database
        respostaRepository.saveAndFlush(resposta);

        int databaseSizeBeforeDelete = respostaRepository.findAll().size();

        // Delete the resposta
        restRespostaMockMvc.perform(delete("/api/respostas/{id}", resposta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resposta> respostaList = respostaRepository.findAll();
        assertThat(respostaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
