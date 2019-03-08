package pt.cpmt.sht.web.rest;

import pt.cpmt.sht.CpmtshtApp;

import pt.cpmt.sht.domain.Localizacao;
import pt.cpmt.sht.repository.LocalizacaoRepository;
import pt.cpmt.sht.service.LocalizacaoService;
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
 * Test class for the LocalizacaoResource REST controller.
 *
 * @see LocalizacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmtshtApp.class)
public class LocalizacaoResourceIntTest {

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Autowired
    private LocalizacaoService localizacaoService;

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

    private MockMvc restLocalizacaoMockMvc;

    private Localizacao localizacao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalizacaoResource localizacaoResource = new LocalizacaoResource(localizacaoService);
        this.restLocalizacaoMockMvc = MockMvcBuilders.standaloneSetup(localizacaoResource)
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
    public static Localizacao createEntity(EntityManager em) {
        Localizacao localizacao = new Localizacao()
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .district(DEFAULT_DISTRICT)
            .country(DEFAULT_COUNTRY);
        return localizacao;
    }

    @Before
    public void initTest() {
        localizacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalizacao() throws Exception {
        int databaseSizeBeforeCreate = localizacaoRepository.findAll().size();

        // Create the Localizacao
        restLocalizacaoMockMvc.perform(post("/api/localizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacao)))
            .andExpect(status().isCreated());

        // Validate the Localizacao in the database
        List<Localizacao> localizacaoList = localizacaoRepository.findAll();
        assertThat(localizacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Localizacao testLocalizacao = localizacaoList.get(localizacaoList.size() - 1);
        assertThat(testLocalizacao.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testLocalizacao.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testLocalizacao.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testLocalizacao.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testLocalizacao.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createLocalizacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localizacaoRepository.findAll().size();

        // Create the Localizacao with an existing ID
        localizacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalizacaoMockMvc.perform(post("/api/localizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacao)))
            .andExpect(status().isBadRequest());

        // Validate the Localizacao in the database
        List<Localizacao> localizacaoList = localizacaoRepository.findAll();
        assertThat(localizacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocalizacaos() throws Exception {
        // Initialize the database
        localizacaoRepository.saveAndFlush(localizacao);

        // Get all the localizacaoList
        restLocalizacaoMockMvc.perform(get("/api/localizacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localizacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }
    
    @Test
    @Transactional
    public void getLocalizacao() throws Exception {
        // Initialize the database
        localizacaoRepository.saveAndFlush(localizacao);

        // Get the localizacao
        restLocalizacaoMockMvc.perform(get("/api/localizacaos/{id}", localizacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localizacao.getId().intValue()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalizacao() throws Exception {
        // Get the localizacao
        restLocalizacaoMockMvc.perform(get("/api/localizacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalizacao() throws Exception {
        // Initialize the database
        localizacaoService.save(localizacao);

        int databaseSizeBeforeUpdate = localizacaoRepository.findAll().size();

        // Update the localizacao
        Localizacao updatedLocalizacao = localizacaoRepository.findById(localizacao.getId()).get();
        // Disconnect from session so that the updates on updatedLocalizacao are not directly saved in db
        em.detach(updatedLocalizacao);
        updatedLocalizacao
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .country(UPDATED_COUNTRY);

        restLocalizacaoMockMvc.perform(put("/api/localizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalizacao)))
            .andExpect(status().isOk());

        // Validate the Localizacao in the database
        List<Localizacao> localizacaoList = localizacaoRepository.findAll();
        assertThat(localizacaoList).hasSize(databaseSizeBeforeUpdate);
        Localizacao testLocalizacao = localizacaoList.get(localizacaoList.size() - 1);
        assertThat(testLocalizacao.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testLocalizacao.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testLocalizacao.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testLocalizacao.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testLocalizacao.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalizacao() throws Exception {
        int databaseSizeBeforeUpdate = localizacaoRepository.findAll().size();

        // Create the Localizacao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalizacaoMockMvc.perform(put("/api/localizacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacao)))
            .andExpect(status().isBadRequest());

        // Validate the Localizacao in the database
        List<Localizacao> localizacaoList = localizacaoRepository.findAll();
        assertThat(localizacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalizacao() throws Exception {
        // Initialize the database
        localizacaoService.save(localizacao);

        int databaseSizeBeforeDelete = localizacaoRepository.findAll().size();

        // Delete the localizacao
        restLocalizacaoMockMvc.perform(delete("/api/localizacaos/{id}", localizacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Localizacao> localizacaoList = localizacaoRepository.findAll();
        assertThat(localizacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localizacao.class);
        Localizacao localizacao1 = new Localizacao();
        localizacao1.setId(1L);
        Localizacao localizacao2 = new Localizacao();
        localizacao2.setId(localizacao1.getId());
        assertThat(localizacao1).isEqualTo(localizacao2);
        localizacao2.setId(2L);
        assertThat(localizacao1).isNotEqualTo(localizacao2);
        localizacao1.setId(null);
        assertThat(localizacao1).isNotEqualTo(localizacao2);
    }
}
