package pt.cpmt.sht.web.rest;
import pt.cpmt.sht.domain.Estabelecimento;
import pt.cpmt.sht.service.EstabelecimentoService;
import pt.cpmt.sht.web.rest.errors.BadRequestAlertException;
import pt.cpmt.sht.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Estabelecimento.
 */
@RestController
@RequestMapping("/api")
public class EstabelecimentoResource {

    private final Logger log = LoggerFactory.getLogger(EstabelecimentoResource.class);

    private static final String ENTITY_NAME = "estabelecimento";

    private final EstabelecimentoService estabelecimentoService;

    public EstabelecimentoResource(EstabelecimentoService estabelecimentoService) {
        this.estabelecimentoService = estabelecimentoService;
    }

    /**
     * POST  /estabelecimentos : Create a new estabelecimento.
     *
     * @param estabelecimento the estabelecimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estabelecimento, or with status 400 (Bad Request) if the estabelecimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estabelecimentos")
    public ResponseEntity<Estabelecimento> createEstabelecimento(@Valid @RequestBody Estabelecimento estabelecimento) throws URISyntaxException {
        log.debug("REST request to save Estabelecimento : {}", estabelecimento);
        if (estabelecimento.getId() != null) {
            throw new BadRequestAlertException("A new estabelecimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estabelecimento result = estabelecimentoService.save(estabelecimento);
        return ResponseEntity.created(new URI("/api/estabelecimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estabelecimentos : Updates an existing estabelecimento.
     *
     * @param estabelecimento the estabelecimento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estabelecimento,
     * or with status 400 (Bad Request) if the estabelecimento is not valid,
     * or with status 500 (Internal Server Error) if the estabelecimento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estabelecimentos")
    public ResponseEntity<Estabelecimento> updateEstabelecimento(@Valid @RequestBody Estabelecimento estabelecimento) throws URISyntaxException {
        log.debug("REST request to update Estabelecimento : {}", estabelecimento);
        if (estabelecimento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Estabelecimento result = estabelecimentoService.save(estabelecimento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estabelecimento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estabelecimentos : get all the estabelecimentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of estabelecimentos in body
     */
    @GetMapping("/estabelecimentos")
    public List<Estabelecimento> getAllEstabelecimentos() {
        log.debug("REST request to get all Estabelecimentos");
        return estabelecimentoService.findAll();
    }

    /**
     * GET  /estabelecimentos/:id : get the "id" estabelecimento.
     *
     * @param id the id of the estabelecimento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estabelecimento, or with status 404 (Not Found)
     */
    @GetMapping("/estabelecimentos/{id}")
    public ResponseEntity<Estabelecimento> getEstabelecimento(@PathVariable Long id) {
        log.debug("REST request to get Estabelecimento : {}", id);
        Optional<Estabelecimento> estabelecimento = estabelecimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estabelecimento);
    }

    /**
     * DELETE  /estabelecimentos/:id : delete the "id" estabelecimento.
     *
     * @param id the id of the estabelecimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estabelecimentos/{id}")
    public ResponseEntity<Void> deleteEstabelecimento(@PathVariable Long id) {
        log.debug("REST request to delete Estabelecimento : {}", id);
        estabelecimentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
