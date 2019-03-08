package pt.cpmt.sht.web.rest;
import pt.cpmt.sht.domain.Risco;
import pt.cpmt.sht.service.RiscoService;
import pt.cpmt.sht.web.rest.errors.BadRequestAlertException;
import pt.cpmt.sht.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Risco.
 */
@RestController
@RequestMapping("/api")
public class RiscoResource {

    private final Logger log = LoggerFactory.getLogger(RiscoResource.class);

    private static final String ENTITY_NAME = "risco";

    private final RiscoService riscoService;

    public RiscoResource(RiscoService riscoService) {
        this.riscoService = riscoService;
    }

    /**
     * POST  /riscos : Create a new risco.
     *
     * @param risco the risco to create
     * @return the ResponseEntity with status 201 (Created) and with body the new risco, or with status 400 (Bad Request) if the risco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/riscos")
    public ResponseEntity<Risco> createRisco(@RequestBody Risco risco) throws URISyntaxException {
        log.debug("REST request to save Risco : {}", risco);
        if (risco.getId() != null) {
            throw new BadRequestAlertException("A new risco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Risco result = riscoService.save(risco);
        return ResponseEntity.created(new URI("/api/riscos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /riscos : Updates an existing risco.
     *
     * @param risco the risco to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated risco,
     * or with status 400 (Bad Request) if the risco is not valid,
     * or with status 500 (Internal Server Error) if the risco couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/riscos")
    public ResponseEntity<Risco> updateRisco(@RequestBody Risco risco) throws URISyntaxException {
        log.debug("REST request to update Risco : {}", risco);
        if (risco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Risco result = riscoService.save(risco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, risco.getId().toString()))
            .body(result);
    }

    /**
     * GET  /riscos : get all the riscos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of riscos in body
     */
    @GetMapping("/riscos")
    public List<Risco> getAllRiscos() {
        log.debug("REST request to get all Riscos");
        return riscoService.findAll();
    }

    /**
     * GET  /riscos/:id : get the "id" risco.
     *
     * @param id the id of the risco to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the risco, or with status 404 (Not Found)
     */
    @GetMapping("/riscos/{id}")
    public ResponseEntity<Risco> getRisco(@PathVariable Long id) {
        log.debug("REST request to get Risco : {}", id);
        Optional<Risco> risco = riscoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(risco);
    }

    /**
     * DELETE  /riscos/:id : delete the "id" risco.
     *
     * @param id the id of the risco to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/riscos/{id}")
    public ResponseEntity<Void> deleteRisco(@PathVariable Long id) {
        log.debug("REST request to delete Risco : {}", id);
        riscoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
