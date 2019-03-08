package pt.cpmt.sht.web.rest;

import pt.cpmt.sht.CpmtshtApp;

import pt.cpmt.sht.domain.PostoTrabalho;
import pt.cpmt.sht.repository.PostoTrabalhoRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static pt.cpmt.sht.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PostoTrabalhoResource REST controller.
 *
 * @see PostoTrabalhoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpmtshtApp.class)
public class PostoTrabalhoResourceIntTest {

    private static final String DEFAULT_DESIGNACAO = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNACAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PostoTrabalhoRepository postoTrabalhoRepository;

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

    private MockMvc restPostoTrabalhoMockMvc;

    private PostoTrabalho postoTrabalho;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PostoTrabalhoResource postoTrabalhoResource = new PostoTrabalhoResource(postoTrabalhoRepository);
        this.restPostoTrabalhoMockMvc = MockMvcBuilders.standaloneSetup(postoTrabalhoResource)
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
    public static PostoTrabalho createEntity(EntityManager em) {
        PostoTrabalho postoTrabalho = new PostoTrabalho()
            .designacao(DEFAULT_DESIGNACAO)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFim(DEFAULT_DATA_FIM);
        return postoTrabalho;
    }

    @Before
    public void initTest() {
        postoTrabalho = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostoTrabalho() throws Exception {
        int databaseSizeBeforeCreate = postoTrabalhoRepository.findAll().size();

        // Create the PostoTrabalho
        restPostoTrabalhoMockMvc.perform(post("/api/posto-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postoTrabalho)))
            .andExpect(status().isCreated());

        // Validate the PostoTrabalho in the database
        List<PostoTrabalho> postoTrabalhoList = postoTrabalhoRepository.findAll();
        assertThat(postoTrabalhoList).hasSize(databaseSizeBeforeCreate + 1);
        PostoTrabalho testPostoTrabalho = postoTrabalhoList.get(postoTrabalhoList.size() - 1);
        assertThat(testPostoTrabalho.getDesignacao()).isEqualTo(DEFAULT_DESIGNACAO);
        assertThat(testPostoTrabalho.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testPostoTrabalho.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
    }

    @Test
    @Transactional
    public void createPostoTrabalhoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postoTrabalhoRepository.findAll().size();

        // Create the PostoTrabalho with an existing ID
        postoTrabalho.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostoTrabalhoMockMvc.perform(post("/api/posto-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postoTrabalho)))
            .andExpect(status().isBadRequest());

        // Validate the PostoTrabalho in the database
        List<PostoTrabalho> postoTrabalhoList = postoTrabalhoRepository.findAll();
        assertThat(postoTrabalhoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPostoTrabalhos() throws Exception {
        // Initialize the database
        postoTrabalhoRepository.saveAndFlush(postoTrabalho);

        // Get all the postoTrabalhoList
        restPostoTrabalhoMockMvc.perform(get("/api/posto-trabalhos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postoTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].designacao").value(hasItem(DEFAULT_DESIGNACAO.toString())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFim").value(hasItem(DEFAULT_DATA_FIM.toString())));
    }
    
    @Test
    @Transactional
    public void getPostoTrabalho() throws Exception {
        // Initialize the database
        postoTrabalhoRepository.saveAndFlush(postoTrabalho);

        // Get the postoTrabalho
        restPostoTrabalhoMockMvc.perform(get("/api/posto-trabalhos/{id}", postoTrabalho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(postoTrabalho.getId().intValue()))
            .andExpect(jsonPath("$.designacao").value(DEFAULT_DESIGNACAO.toString()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFim").value(DEFAULT_DATA_FIM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPostoTrabalho() throws Exception {
        // Get the postoTrabalho
        restPostoTrabalhoMockMvc.perform(get("/api/posto-trabalhos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostoTrabalho() throws Exception {
        // Initialize the database
        postoTrabalhoRepository.saveAndFlush(postoTrabalho);

        int databaseSizeBeforeUpdate = postoTrabalhoRepository.findAll().size();

        // Update the postoTrabalho
        PostoTrabalho updatedPostoTrabalho = postoTrabalhoRepository.findById(postoTrabalho.getId()).get();
        // Disconnect from session so that the updates on updatedPostoTrabalho are not directly saved in db
        em.detach(updatedPostoTrabalho);
        updatedPostoTrabalho
            .designacao(UPDATED_DESIGNACAO)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFim(UPDATED_DATA_FIM);

        restPostoTrabalhoMockMvc.perform(put("/api/posto-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPostoTrabalho)))
            .andExpect(status().isOk());

        // Validate the PostoTrabalho in the database
        List<PostoTrabalho> postoTrabalhoList = postoTrabalhoRepository.findAll();
        assertThat(postoTrabalhoList).hasSize(databaseSizeBeforeUpdate);
        PostoTrabalho testPostoTrabalho = postoTrabalhoList.get(postoTrabalhoList.size() - 1);
        assertThat(testPostoTrabalho.getDesignacao()).isEqualTo(UPDATED_DESIGNACAO);
        assertThat(testPostoTrabalho.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testPostoTrabalho.getDataFim()).isEqualTo(UPDATED_DATA_FIM);
    }

    @Test
    @Transactional
    public void updateNonExistingPostoTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = postoTrabalhoRepository.findAll().size();

        // Create the PostoTrabalho

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostoTrabalhoMockMvc.perform(put("/api/posto-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(postoTrabalho)))
            .andExpect(status().isBadRequest());

        // Validate the PostoTrabalho in the database
        List<PostoTrabalho> postoTrabalhoList = postoTrabalhoRepository.findAll();
        assertThat(postoTrabalhoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostoTrabalho() throws Exception {
        // Initialize the database
        postoTrabalhoRepository.saveAndFlush(postoTrabalho);

        int databaseSizeBeforeDelete = postoTrabalhoRepository.findAll().size();

        // Delete the postoTrabalho
        restPostoTrabalhoMockMvc.perform(delete("/api/posto-trabalhos/{id}", postoTrabalho.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PostoTrabalho> postoTrabalhoList = postoTrabalhoRepository.findAll();
        assertThat(postoTrabalhoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostoTrabalho.class);
        PostoTrabalho postoTrabalho1 = new PostoTrabalho();
        postoTrabalho1.setId(1L);
        PostoTrabalho postoTrabalho2 = new PostoTrabalho();
        postoTrabalho2.setId(postoTrabalho1.getId());
        assertThat(postoTrabalho1).isEqualTo(postoTrabalho2);
        postoTrabalho2.setId(2L);
        assertThat(postoTrabalho1).isNotEqualTo(postoTrabalho2);
        postoTrabalho1.setId(null);
        assertThat(postoTrabalho1).isNotEqualTo(postoTrabalho2);
    }
}
