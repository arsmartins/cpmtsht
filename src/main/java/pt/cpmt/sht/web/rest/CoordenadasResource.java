package pt.cpmt.sht.web.rest;
import pt.cpmt.sht.domain.Coordenadas;
import pt.cpmt.sht.service.CoordenadasService;
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
 * REST controller for managing Coordenadas.
 */
@RestController
@RequestMapping("/api")
public class CoordenadasResource {

    private final Logger log = LoggerFactory.getLogger(CoordenadasResource.class);

    private static final String ENTITY_NAME = "coordenadas";

    private final CoordenadasService coordenadasService;

    public CoordenadasResource(CoordenadasService coordenadasService) {
        this.coordenadasService = coordenadasService;
    }

    /**
     * POST  /coordenadas : Create a new coordenadas.
     *
     * @param coordenadas the coordenadas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coordenadas, or with status 400 (Bad Request) if the coordenadas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coordenadas")
    public ResponseEntity<Coordenadas> createCoordenadas(@RequestBody Coordenadas coordenadas) throws URISyntaxException {
        log.debug("REST request to save Coordenadas : {}", coordenadas);
        if (coordenadas.getId() != null) {
            throw new BadRequestAlertException("A new coordenadas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coordenadas result = coordenadasService.save(coordenadas);
        return ResponseEntity.created(new URI("/api/coordenadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coordenadas : Updates an existing coordenadas.
     *
     * @param coordenadas the coordenadas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coordenadas,
     * or with status 400 (Bad Request) if the coordenadas is not valid,
     * or with status 500 (Internal Server Error) if the coordenadas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coordenadas")
    public ResponseEntity<Coordenadas> updateCoordenadas(@RequestBody Coordenadas coordenadas) throws URISyntaxException {
        log.debug("REST request to update Coordenadas : {}", coordenadas);
        if (coordenadas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Coordenadas result = coordenadasService.save(coordenadas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coordenadas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coordenadas : get all the coordenadas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coordenadas in body
     */
    @GetMapping("/coordenadas")
    public List<Coordenadas> getAllCoordenadas() {
        log.debug("REST request to get all Coordenadas");
        return coordenadasService.findAll();
    }

    /**
     * GET  /coordenadas/:id : get the "id" coordenadas.
     *
     * @param id the id of the coordenadas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coordenadas, or with status 404 (Not Found)
     */
    @GetMapping("/coordenadas/{id}")
    public ResponseEntity<Coordenadas> getCoordenadas(@PathVariable Long id) {
        log.debug("REST request to get Coordenadas : {}", id);
        Optional<Coordenadas> coordenadas = coordenadasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coordenadas);
    }

    /**
     * DELETE  /coordenadas/:id : delete the "id" coordenadas.
     *
     * @param id the id of the coordenadas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coordenadas/{id}")
    public ResponseEntity<Void> deleteCoordenadas(@PathVariable Long id) {
        log.debug("REST request to delete Coordenadas : {}", id);
        coordenadasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
