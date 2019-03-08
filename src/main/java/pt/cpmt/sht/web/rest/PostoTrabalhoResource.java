package pt.cpmt.sht.web.rest;
import pt.cpmt.sht.domain.PostoTrabalho;
import pt.cpmt.sht.repository.PostoTrabalhoRepository;
import pt.cpmt.sht.web.rest.errors.BadRequestAlertException;
import pt.cpmt.sht.web.rest.util.HeaderUtil;
import pt.cpmt.sht.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PostoTrabalho.
 */
@RestController
@RequestMapping("/api")
public class PostoTrabalhoResource {

    private final Logger log = LoggerFactory.getLogger(PostoTrabalhoResource.class);

    private static final String ENTITY_NAME = "postoTrabalho";

    private final PostoTrabalhoRepository postoTrabalhoRepository;

    public PostoTrabalhoResource(PostoTrabalhoRepository postoTrabalhoRepository) {
        this.postoTrabalhoRepository = postoTrabalhoRepository;
    }

    /**
     * POST  /posto-trabalhos : Create a new postoTrabalho.
     *
     * @param postoTrabalho the postoTrabalho to create
     * @return the ResponseEntity with status 201 (Created) and with body the new postoTrabalho, or with status 400 (Bad Request) if the postoTrabalho has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/posto-trabalhos")
    public ResponseEntity<PostoTrabalho> createPostoTrabalho(@RequestBody PostoTrabalho postoTrabalho) throws URISyntaxException {
        log.debug("REST request to save PostoTrabalho : {}", postoTrabalho);
        if (postoTrabalho.getId() != null) {
            throw new BadRequestAlertException("A new postoTrabalho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostoTrabalho result = postoTrabalhoRepository.save(postoTrabalho);
        return ResponseEntity.created(new URI("/api/posto-trabalhos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /posto-trabalhos : Updates an existing postoTrabalho.
     *
     * @param postoTrabalho the postoTrabalho to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated postoTrabalho,
     * or with status 400 (Bad Request) if the postoTrabalho is not valid,
     * or with status 500 (Internal Server Error) if the postoTrabalho couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/posto-trabalhos")
    public ResponseEntity<PostoTrabalho> updatePostoTrabalho(@RequestBody PostoTrabalho postoTrabalho) throws URISyntaxException {
        log.debug("REST request to update PostoTrabalho : {}", postoTrabalho);
        if (postoTrabalho.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostoTrabalho result = postoTrabalhoRepository.save(postoTrabalho);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, postoTrabalho.getId().toString()))
            .body(result);
    }

    /**
     * GET  /posto-trabalhos : get all the postoTrabalhos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of postoTrabalhos in body
     */
    @GetMapping("/posto-trabalhos")
    public ResponseEntity<List<PostoTrabalho>> getAllPostoTrabalhos(Pageable pageable) {
        log.debug("REST request to get a page of PostoTrabalhos");
        Page<PostoTrabalho> page = postoTrabalhoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/posto-trabalhos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /posto-trabalhos/:id : get the "id" postoTrabalho.
     *
     * @param id the id of the postoTrabalho to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the postoTrabalho, or with status 404 (Not Found)
     */
    @GetMapping("/posto-trabalhos/{id}")
    public ResponseEntity<PostoTrabalho> getPostoTrabalho(@PathVariable Long id) {
        log.debug("REST request to get PostoTrabalho : {}", id);
        Optional<PostoTrabalho> postoTrabalho = postoTrabalhoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(postoTrabalho);
    }

    /**
     * DELETE  /posto-trabalhos/:id : delete the "id" postoTrabalho.
     *
     * @param id the id of the postoTrabalho to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/posto-trabalhos/{id}")
    public ResponseEntity<Void> deletePostoTrabalho(@PathVariable Long id) {
        log.debug("REST request to delete PostoTrabalho : {}", id);
        postoTrabalhoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
