package pt.cpmt.sht.web.rest;
import pt.cpmt.sht.domain.TipoRisco;
import pt.cpmt.sht.service.TipoRiscoService;
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
 * REST controller for managing TipoRisco.
 */
@RestController
@RequestMapping("/api")
public class TipoRiscoResource {

    private final Logger log = LoggerFactory.getLogger(TipoRiscoResource.class);

    private static final String ENTITY_NAME = "tipoRisco";

    private final TipoRiscoService tipoRiscoService;

    public TipoRiscoResource(TipoRiscoService tipoRiscoService) {
        this.tipoRiscoService = tipoRiscoService;
    }

    /**
     * POST  /tipo-riscos : Create a new tipoRisco.
     *
     * @param tipoRisco the tipoRisco to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoRisco, or with status 400 (Bad Request) if the tipoRisco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-riscos")
    public ResponseEntity<TipoRisco> createTipoRisco(@RequestBody TipoRisco tipoRisco) throws URISyntaxException {
        log.debug("REST request to save TipoRisco : {}", tipoRisco);
        if (tipoRisco.getId() != null) {
            throw new BadRequestAlertException("A new tipoRisco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoRisco result = tipoRiscoService.save(tipoRisco);
        return ResponseEntity.created(new URI("/api/tipo-riscos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-riscos : Updates an existing tipoRisco.
     *
     * @param tipoRisco the tipoRisco to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoRisco,
     * or with status 400 (Bad Request) if the tipoRisco is not valid,
     * or with status 500 (Internal Server Error) if the tipoRisco couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-riscos")
    public ResponseEntity<TipoRisco> updateTipoRisco(@RequestBody TipoRisco tipoRisco) throws URISyntaxException {
        log.debug("REST request to update TipoRisco : {}", tipoRisco);
        if (tipoRisco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoRisco result = tipoRiscoService.save(tipoRisco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoRisco.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-riscos : get all the tipoRiscos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoRiscos in body
     */
    @GetMapping("/tipo-riscos")
    public List<TipoRisco> getAllTipoRiscos() {
        log.debug("REST request to get all TipoRiscos");
        return tipoRiscoService.findAll();
    }

    /**
     * GET  /tipo-riscos/:id : get the "id" tipoRisco.
     *
     * @param id the id of the tipoRisco to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoRisco, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-riscos/{id}")
    public ResponseEntity<TipoRisco> getTipoRisco(@PathVariable Long id) {
        log.debug("REST request to get TipoRisco : {}", id);
        Optional<TipoRisco> tipoRisco = tipoRiscoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoRisco);
    }

    /**
     * DELETE  /tipo-riscos/:id : delete the "id" tipoRisco.
     *
     * @param id the id of the tipoRisco to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-riscos/{id}")
    public ResponseEntity<Void> deleteTipoRisco(@PathVariable Long id) {
        log.debug("REST request to delete TipoRisco : {}", id);
        tipoRiscoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
