package pt.cpmt.sht.web.rest;

import pt.cpmt.sht.CpmtshtApp;

import pt.cpmt.sht.domain.Estabelecimento;
import pt.cpmt.sht.repository.EstabelecimentoRepository;
import pt.cpmt.sht.service.EstabelecimentoService;
import pt.cpmt.sht.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static pt.cpmt.sht.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EstabelecimentoResource REST controller.
 *
 * @see EstabelecimentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmtshtApp.class)
public class EstabelecimentoResourceIntTest {

    private static final String DEFAULT_DESIGNACAO = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNACAO = "BBBBBBBBBB";

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

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

    private MockMvc restEstabelecimentoMockMvc;

    private Estabelecimento estabelecimento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstabelecimentoResource estabelecimentoResource = new EstabelecimentoResource(estabelecimentoService);
        this.restEstabelecimentoMockMvc = MockMvcBuilders.standaloneSetup(estabelecimentoResource)
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
    public static Estabelecimento createEntity(EntityManager em) {
        Estabelecimento estabelecimento = new Estabelecimento()
            .designacao(DEFAULT_DESIGNACAO);
        return estabelecimento;
    }

    @Before
    public void initTest() {
        estabelecimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstabelecimento() throws Exception {
        int databaseSizeBeforeCreate = estabelecimentoRepository.findAll().size();

        // Create the Estabelecimento
        restEstabelecimentoMockMvc.perform(post("/api/estabelecimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estabelecimento)))
            .andExpect(status().isCreated());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Estabelecimento testEstabelecimento = estabelecimentoList.get(estabelecimentoList.size() - 1);
        assertThat(testEstabelecimento.getDesignacao()).isEqualTo(DEFAULT_DESIGNACAO);
    }

    @Test
    @Transactional
    public void createEstabelecimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estabelecimentoRepository.findAll().size();

        // Create the Estabelecimento with an existing ID
        estabelecimento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstabelecimentoMockMvc.perform(post("/api/estabelecimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estabelecimento)))
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDesignacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estabelecimentoRepository.findAll().size();
        // set the field null
        estabelecimento.setDesignacao(null);

        // Create the Estabelecimento, which fails.

        restEstabelecimentoMockMvc.perform(post("/api/estabelecimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estabelecimento)))
            .andExpect(status().isBadRequest());

        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstabelecimentos() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList
        restEstabelecimentoMockMvc.perform(get("/api/estabelecimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estabelecimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].designacao").value(hasItem(DEFAULT_DESIGNACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getEstabelecimento() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get the estabelecimento
        restEstabelecimentoMockMvc.perform(get("/api/estabelecimentos/{id}", estabelecimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estabelecimento.getId().intValue()))
            .andExpect(jsonPath("$.designacao").value(DEFAULT_DESIGNACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstabelecimento() throws Exception {
        // Get the estabelecimento
        restEstabelecimentoMockMvc.perform(get("/api/estabelecimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstabelecimento() throws Exception {
        // Initialize the database
        estabelecimentoService.save(estabelecimento);

        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();

        // Update the estabelecimento
        Estabelecimento updatedEstabelecimento = estabelecimentoRepository.findById(estabelecimento.getId()).get();
        // Disconnect from session so that the updates on updatedEstabelecimento are not directly saved in db
        em.detach(updatedEstabelecimento);
        updatedEstabelecimento
            .designacao(UPDATED_DESIGNACAO);

        restEstabelecimentoMockMvc.perform(put("/api/estabelecimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstabelecimento)))
            .andExpect(status().isOk());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
        Estabelecimento testEstabelecimento = estabelecimentoList.get(estabelecimentoList.size() - 1);
        assertThat(testEstabelecimento.getDesignacao()).isEqualTo(UPDATED_DESIGNACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();

        // Create the Estabelecimento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc.perform(put("/api/estabelecimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estabelecimento)))
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstabelecimento() throws Exception {
        // Initialize the database
        estabelecimentoService.save(estabelecimento);

        int databaseSizeBeforeDelete = estabelecimentoRepository.findAll().size();

        // Delete the estabelecimento
        restEstabelecimentoMockMvc.perform(delete("/api/estabelecimentos/{id}", estabelecimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estabelecimento.class);
        Estabelecimento estabelecimento1 = new Estabelecimento();
        estabelecimento1.setId(1L);
        Estabelecimento estabelecimento2 = new Estabelecimento();
        estabelecimento2.setId(estabelecimento1.getId());
        assertThat(estabelecimento1).isEqualTo(estabelecimento2);
        estabelecimento2.setId(2L);
        assertThat(estabelecimento1).isNotEqualTo(estabelecimento2);
        estabelecimento1.setId(null);
        assertThat(estabelecimento1).isNotEqualTo(estabelecimento2);
    }
}
