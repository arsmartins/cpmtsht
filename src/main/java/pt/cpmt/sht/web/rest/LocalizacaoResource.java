package pt.cpmt.sht.web.rest;
import pt.cpmt.sht.domain.Localizacao;
import pt.cpmt.sht.service.LocalizacaoService;
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
 * REST controller for managing Localizacao.
 */
@RestController
@RequestMapping("/api")
public class LocalizacaoResource {

    private final Logger log = LoggerFactory.getLogger(LocalizacaoResource.class);

    private static final String ENTITY_NAME = "localizacao";

    private final LocalizacaoService localizacaoService;

    public LocalizacaoResource(LocalizacaoService localizacaoService) {
        this.localizacaoService = localizacaoService;
    }

    /**
     * POST  /localizacaos : Create a new localizacao.
     *
     * @param localizacao the localizacao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localizacao, or with status 400 (Bad Request) if the localizacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/localizacaos")
    public ResponseEntity<Localizacao> createLocalizacao(@RequestBody Localizacao localizacao) throws URISyntaxException {
        log.debug("REST request to save Localizacao : {}", localizacao);
        if (localizacao.getId() != null) {
            throw new BadRequestAlertException("A new localizacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Localizacao result = localizacaoService.save(localizacao);
        return ResponseEntity.created(new URI("/api/localizacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /localizacaos : Updates an existing localizacao.
     *
     * @param localizacao the localizacao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localizacao,
     * or with status 400 (Bad Request) if the localizacao is not valid,
     * or with status 500 (Internal Server Error) if the localizacao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/localizacaos")
    public ResponseEntity<Localizacao> updateLocalizacao(@RequestBody Localizacao localizacao) throws URISyntaxException {
        log.debug("REST request to update Localizacao : {}", localizacao);
        if (localizacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Localizacao result = localizacaoService.save(localizacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localizacao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /localizacaos : get all the localizacaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of localizacaos in body
     */
    @GetMapping("/localizacaos")
    public List<Localizacao> getAllLocalizacaos() {
        log.debug("REST request to get all Localizacaos");
        return localizacaoService.findAll();
    }

    /**
     * GET  /localizacaos/:id : get the "id" localizacao.
     *
     * @param id the id of the localizacao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localizacao, or with status 404 (Not Found)
     */
    @GetMapping("/localizacaos/{id}")
    public ResponseEntity<Localizacao> getLocalizacao(@PathVariable Long id) {
        log.debug("REST request to get Localizacao : {}", id);
        Optional<Localizacao> localizacao = localizacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(localizacao);
    }

    /**
     * DELETE  /localizacaos/:id : delete the "id" localizacao.
     *
     * @param id the id of the localizacao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/localizacaos/{id}")
    public ResponseEntity<Void> deleteLocalizacao(@PathVariable Long id) {
        log.debug("REST request to delete Localizacao : {}", id);
        localizacaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
