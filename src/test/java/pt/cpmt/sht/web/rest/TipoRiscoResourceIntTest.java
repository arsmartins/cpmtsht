package pt.cpmt.sht.web.rest;

import pt.cpmt.sht.CpmtshtApp;

import pt.cpmt.sht.domain.TipoRisco;
import pt.cpmt.sht.repository.TipoRiscoRepository;
import pt.cpmt.sht.service.TipoRiscoService;
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
 * Test class for the TipoRiscoResource REST controller.
 *
 * @see TipoRiscoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmtshtApp.class)
public class TipoRiscoResourceIntTest {

    private static final String DEFAULT_DESIGNACAO = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNACAO = "BBBBBBBBBB";

    @Autowired
    private TipoRiscoRepository tipoRiscoRepository;

    @Autowired
    private TipoRiscoService tipoRiscoService;

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

    private MockMvc restTipoRiscoMockMvc;

    private TipoRisco tipoRisco;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoRiscoResource tipoRiscoResource = new TipoRiscoResource(tipoRiscoService);
        this.restTipoRiscoMockMvc = MockMvcBuilders.standaloneSetup(tipoRiscoResource)
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
    public static TipoRisco createEntity(EntityManager em) {
        TipoRisco tipoRisco = new TipoRisco()
            .designacao(DEFAULT_DESIGNACAO);
        return tipoRisco;
    }

    @Before
    public void initTest() {
        tipoRisco = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoRisco() throws Exception {
        int databaseSizeBeforeCreate = tipoRiscoRepository.findAll().size();

        // Create the TipoRisco
        restTipoRiscoMockMvc.perform(post("/api/tipo-riscos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRisco)))
            .andExpect(status().isCreated());

        // Validate the TipoRisco in the database
        List<TipoRisco> tipoRiscoList = tipoRiscoRepository.findAll();
        assertThat(tipoRiscoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRisco testTipoRisco = tipoRiscoList.get(tipoRiscoList.size() - 1);
        assertThat(testTipoRisco.getDesignacao()).isEqualTo(DEFAULT_DESIGNACAO);
    }

    @Test
    @Transactional
    public void createTipoRiscoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoRiscoRepository.findAll().size();

        // Create the TipoRisco with an existing ID
        tipoRisco.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRiscoMockMvc.perform(post("/api/tipo-riscos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRisco)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRisco in the database
        List<TipoRisco> tipoRiscoList = tipoRiscoRepository.findAll();
        assertThat(tipoRiscoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTipoRiscos() throws Exception {
        // Initialize the database
        tipoRiscoRepository.saveAndFlush(tipoRisco);

        // Get all the tipoRiscoList
        restTipoRiscoMockMvc.perform(get("/api/tipo-riscos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRisco.getId().intValue())))
            .andExpect(jsonPath("$.[*].designacao").value(hasItem(DEFAULT_DESIGNACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoRisco() throws Exception {
        // Initialize the database
        tipoRiscoRepository.saveAndFlush(tipoRisco);

        // Get the tipoRisco
        restTipoRiscoMockMvc.perform(get("/api/tipo-riscos/{id}", tipoRisco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRisco.getId().intValue()))
            .andExpect(jsonPath("$.designacao").value(DEFAULT_DESIGNACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoRisco() throws Exception {
        // Get the tipoRisco
        restTipoRiscoMockMvc.perform(get("/api/tipo-riscos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoRisco() throws Exception {
        // Initialize the database
        tipoRiscoService.save(tipoRisco);

        int databaseSizeBeforeUpdate = tipoRiscoRepository.findAll().size();

        // Update the tipoRisco
        TipoRisco updatedTipoRisco = tipoRiscoRepository.findById(tipoRisco.getId()).get();
        // Disconnect from session so that the updates on updatedTipoRisco are not directly saved in db
        em.detach(updatedTipoRisco);
        updatedTipoRisco
            .designacao(UPDATED_DESIGNACAO);

        restTipoRiscoMockMvc.perform(put("/api/tipo-riscos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoRisco)))
            .andExpect(status().isOk());

        // Validate the TipoRisco in the database
        List<TipoRisco> tipoRiscoList = tipoRiscoRepository.findAll();
        assertThat(tipoRiscoList).hasSize(databaseSizeBeforeUpdate);
        TipoRisco testTipoRisco = tipoRiscoList.get(tipoRiscoList.size() - 1);
        assertThat(testTipoRisco.getDesignacao()).isEqualTo(UPDATED_DESIGNACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoRisco() throws Exception {
        int databaseSizeBeforeUpdate = tipoRiscoRepository.findAll().size();

        // Create the TipoRisco

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoRiscoMockMvc.perform(put("/api/tipo-riscos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRisco)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRisco in the database
        List<TipoRisco> tipoRiscoList = tipoRiscoRepository.findAll();
        assertThat(tipoRiscoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoRisco() throws Exception {
        // Initialize the database
        tipoRiscoService.save(tipoRisco);

        int databaseSizeBeforeDelete = tipoRiscoRepository.findAll().size();

        // Delete the tipoRisco
        restTipoRiscoMockMvc.perform(delete("/api/tipo-riscos/{id}", tipoRisco.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoRisco> tipoRiscoList = tipoRiscoRepository.findAll();
        assertThat(tipoRiscoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRisco.class);
        TipoRisco tipoRisco1 = new TipoRisco();
        tipoRisco1.setId(1L);
        TipoRisco tipoRisco2 = new TipoRisco();
        tipoRisco2.setId(tipoRisco1.getId());
        assertThat(tipoRisco1).isEqualTo(tipoRisco2);
        tipoRisco2.setId(2L);
        assertThat(tipoRisco1).isNotEqualTo(tipoRisco2);
        tipoRisco1.setId(null);
        assertThat(tipoRisco1).isNotEqualTo(tipoRisco2);
    }
}
