package pt.cpmt.sht.web.rest;
import pt.cpmt.sht.domain.Funcionario;
import pt.cpmt.sht.repository.FuncionarioRepository;
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
 * REST controller for managing Funcionario.
 */
@RestController
@RequestMapping("/api")
public class FuncionarioResource {

    private final Logger log = LoggerFactory.getLogger(FuncionarioResource.class);

    private static final String ENTITY_NAME = "funcionario";

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioResource(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * POST  /funcionarios : Create a new funcionario.
     *
     * @param funcionario the funcionario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new funcionario, or with status 400 (Bad Request) if the funcionario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/funcionarios")
    public ResponseEntity<Funcionario> createFuncionario(@RequestBody Funcionario funcionario) throws URISyntaxException {
        log.debug("REST request to save Funcionario : {}", funcionario);
        if (funcionario.getId() != null) {
            throw new BadRequestAlertException("A new funcionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Funcionario result = funcionarioRepository.save(funcionario);
        return ResponseEntity.created(new URI("/api/funcionarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /funcionarios : Updates an existing funcionario.
     *
     * @param funcionario the funcionario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated funcionario,
     * or with status 400 (Bad Request) if the funcionario is not valid,
     * or with status 500 (Internal Server Error) if the funcionario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/funcionarios")
    public ResponseEntity<Funcionario> updateFuncionario(@RequestBody Funcionario funcionario) throws URISyntaxException {
        log.debug("REST request to update Funcionario : {}", funcionario);
        if (funcionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Funcionario result = funcionarioRepository.save(funcionario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, funcionario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /funcionarios : get all the funcionarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of funcionarios in body
     */
    @GetMapping("/funcionarios")
    public ResponseEntity<List<Funcionario>> getAllFuncionarios(Pageable pageable) {
        log.debug("REST request to get a page of Funcionarios");
        Page<Funcionario> page = funcionarioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/funcionarios");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /funcionarios/:id : get the "id" funcionario.
     *
     * @param id the id of the funcionario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the funcionario, or with status 404 (Not Found)
     */
    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<Funcionario> getFuncionario(@PathVariable Long id) {
        log.debug("REST request to get Funcionario : {}", id);
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(funcionario);
    }

    /**
     * DELETE  /funcionarios/:id : delete the "id" funcionario.
     *
     * @param id the id of the funcionario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        log.debug("REST request to delete Funcionario : {}", id);
        funcionarioRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
