package pt.cpmt.sht.web.rest;

import pt.cpmt.sht.CpmtshtApp;

import pt.cpmt.sht.domain.Coordenadas;
import pt.cpmt.sht.repository.CoordenadasRepository;
import pt.cpmt.sht.service.CoordenadasService;
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
 * Test class for the CoordenadasResource REST controller.
 *
 * @see CoordenadasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmtshtApp.class)
public class CoordenadasResourceIntTest {

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    @Autowired
    private CoordenadasRepository coordenadasRepository;

    @Autowired
    private CoordenadasService coordenadasService;

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

    private MockMvc restCoordenadasMockMvc;

    private Coordenadas coordenadas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoordenadasResource coordenadasResource = new CoordenadasResource(coordenadasService);
        this.restCoordenadasMockMvc = MockMvcBuilders.standaloneSetup(coordenadasResource)
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
    public static Coordenadas createEntity(EntityManager em) {
        Coordenadas coordenadas = new Coordenadas()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return coordenadas;
    }

    @Before
    public void initTest() {
        coordenadas = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoordenadas() throws Exception {
        int databaseSizeBeforeCreate = coordenadasRepository.findAll().size();

        // Create the Coordenadas
        restCoordenadasMockMvc.perform(post("/api/coordenadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadas)))
            .andExpect(status().isCreated());

        // Validate the Coordenadas in the database
        List<Coordenadas> coordenadasList = coordenadasRepository.findAll();
        assertThat(coordenadasList).hasSize(databaseSizeBeforeCreate + 1);
        Coordenadas testCoordenadas = coordenadasList.get(coordenadasList.size() - 1);
        assertThat(testCoordenadas.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCoordenadas.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createCoordenadasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coordenadasRepository.findAll().size();

        // Create the Coordenadas with an existing ID
        coordenadas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoordenadasMockMvc.perform(post("/api/coordenadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadas)))
            .andExpect(status().isBadRequest());

        // Validate the Coordenadas in the database
        List<Coordenadas> coordenadasList = coordenadasRepository.findAll();
        assertThat(coordenadasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCoordenadas() throws Exception {
        // Initialize the database
        coordenadasRepository.saveAndFlush(coordenadas);

        // Get all the coordenadasList
        restCoordenadasMockMvc.perform(get("/api/coordenadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordenadas.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCoordenadas() throws Exception {
        // Initialize the database
        coordenadasRepository.saveAndFlush(coordenadas);

        // Get the coordenadas
        restCoordenadasMockMvc.perform(get("/api/coordenadas/{id}", coordenadas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coordenadas.getId().intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoordenadas() throws Exception {
        // Get the coordenadas
        restCoordenadasMockMvc.perform(get("/api/coordenadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoordenadas() throws Exception {
        // Initialize the database
        coordenadasService.save(coordenadas);

        int databaseSizeBeforeUpdate = coordenadasRepository.findAll().size();

        // Update the coordenadas
        Coordenadas updatedCoordenadas = coordenadasRepository.findById(coordenadas.getId()).get();
        // Disconnect from session so that the updates on updatedCoordenadas are not directly saved in db
        em.detach(updatedCoordenadas);
        updatedCoordenadas
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restCoordenadasMockMvc.perform(put("/api/coordenadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCoordenadas)))
            .andExpect(status().isOk());

        // Validate the Coordenadas in the database
        List<Coordenadas> coordenadasList = coordenadasRepository.findAll();
        assertThat(coordenadasList).hasSize(databaseSizeBeforeUpdate);
        Coordenadas testCoordenadas = coordenadasList.get(coordenadasList.size() - 1);
        assertThat(testCoordenadas.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCoordenadas.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingCoordenadas() throws Exception {
        int databaseSizeBeforeUpdate = coordenadasRepository.findAll().size();

        // Create the Coordenadas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoordenadasMockMvc.perform(put("/api/coordenadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadas)))
            .andExpect(status().isBadRequest());

        // Validate the Coordenadas in the database
        List<Coordenadas> coordenadasList = coordenadasRepository.findAll();
        assertThat(coordenadasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoordenadas() throws Exception {
        // Initialize the database
        coordenadasService.save(coordenadas);

        int databaseSizeBeforeDelete = coordenadasRepository.findAll().size();

        // Delete the coordenadas
        restCoordenadasMockMvc.perform(delete("/api/coordenadas/{id}", coordenadas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coordenadas> coordenadasList = coordenadasRepository.findAll();
        assertThat(coordenadasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coordenadas.class);
        Coordenadas coordenadas1 = new Coordenadas();
        coordenadas1.setId(1L);
        Coordenadas coordenadas2 = new Coordenadas();
        coordenadas2.setId(coordenadas1.getId());
        assertThat(coordenadas1).isEqualTo(coordenadas2);
        coordenadas2.setId(2L);
        assertThat(coordenadas1).isNotEqualTo(coordenadas2);
        coordenadas1.setId(null);
        assertThat(coordenadas1).isNotEqualTo(coordenadas2);
    }
}
