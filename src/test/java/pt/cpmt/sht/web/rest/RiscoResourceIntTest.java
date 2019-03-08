package pt.cpmt.sht.web.rest;

import pt.cpmt.sht.CpmtshtApp;

import pt.cpmt.sht.domain.Risco;
import pt.cpmt.sht.repository.RiscoRepository;
import pt.cpmt.sht.service.RiscoService;
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
 * Test class for the RiscoResource REST controller.
 *
 * @see RiscoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmtshtApp.class)
public class RiscoResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RiscoRepository riscoRepository;

    @Autowired
    private RiscoService riscoService;

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

    private MockMvc restRiscoMockMvc;

    private Risco risco;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RiscoResource riscoResource = new RiscoResource(riscoService);
        this.restRiscoMockMvc = MockMvcBuilders.standaloneSetup(riscoResource)
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
    public static Risco createEntity(EntityManager em) {
        Risco risco = new Risco()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return risco;
    }

    @Before
    public void initTest() {
        risco = createEntity(em);
    }

    @Test
    @Transactional
    public void createRisco() throws Exception {
        int databaseSizeBeforeCreate = riscoRepository.findAll().size();

        // Create the Risco
        restRiscoMockMvc.perform(post("/api/riscos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risco)))
            .andExpect(status().isCreated());

        // Validate the Risco in the database
        List<Risco> riscoList = riscoRepository.findAll();
        assertThat(riscoList).hasSize(databaseSizeBeforeCreate + 1);
        Risco testRisco = riscoList.get(riscoList.size() - 1);
        assertThat(testRisco.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRisco.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRiscoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = riscoRepository.findAll().size();

        // Create the Risco with an existing ID
        risco.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiscoMockMvc.perform(post("/api/riscos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risco)))
            .andExpect(status().isBadRequest());

        // Validate the Risco in the database
        List<Risco> riscoList = riscoRepository.findAll();
        assertThat(riscoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRiscos() throws Exception {
        // Initialize the database
        riscoRepository.saveAndFlush(risco);

        // Get all the riscoList
        restRiscoMockMvc.perform(get("/api/riscos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(risco.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRisco() throws Exception {
        // Initialize the database
        riscoRepository.saveAndFlush(risco);

        // Get the risco
        restRiscoMockMvc.perform(get("/api/riscos/{id}", risco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(risco.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRisco() throws Exception {
        // Get the risco
        restRiscoMockMvc.perform(get("/api/riscos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRisco() throws Exception {
        // Initialize the database
        riscoService.save(risco);

        int databaseSizeBeforeUpdate = riscoRepository.findAll().size();

        // Update the risco
        Risco updatedRisco = riscoRepository.findById(risco.getId()).get();
        // Disconnect from session so that the updates on updatedRisco are not directly saved in db
        em.detach(updatedRisco);
        updatedRisco
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);

        restRiscoMockMvc.perform(put("/api/riscos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRisco)))
            .andExpect(status().isOk());

        // Validate the Risco in the database
        List<Risco> riscoList = riscoRepository.findAll();
        assertThat(riscoList).hasSize(databaseSizeBeforeUpdate);
        Risco testRisco = riscoList.get(riscoList.size() - 1);
        assertThat(testRisco.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRisco.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRisco() throws Exception {
        int databaseSizeBeforeUpdate = riscoRepository.findAll().size();

        // Create the Risco

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiscoMockMvc.perform(put("/api/riscos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(risco)))
            .andExpect(status().isBadRequest());

        // Validate the Risco in the database
        List<Risco> riscoList = riscoRepository.findAll();
        assertThat(riscoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRisco() throws Exception {
        // Initialize the database
        riscoService.save(risco);

        int databaseSizeBeforeDelete = riscoRepository.findAll().size();

        // Delete the risco
        restRiscoMockMvc.perform(delete("/api/riscos/{id}", risco.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Risco> riscoList = riscoRepository.findAll();
        assertThat(riscoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Risco.class);
        Risco risco1 = new Risco();
        risco1.setId(1L);
        Risco risco2 = new Risco();
        risco2.setId(risco1.getId());
        assertThat(risco1).isEqualTo(risco2);
        risco2.setId(2L);
        assertThat(risco1).isNotEqualTo(risco2);
        risco1.setId(null);
        assertThat(risco1).isNotEqualTo(risco2);
    }
}
