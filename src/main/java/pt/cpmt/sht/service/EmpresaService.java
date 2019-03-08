package pt.cpmt.sht.service;

import pt.cpmt.sht.domain.Empresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Empresa.
 */
public interface EmpresaService {

    /**
     * Save a empresa.
     *
     * @param empresa the entity to save
     * @return the persisted entity
     */
    Empresa save(Empresa empresa);

    /**
     * Get all the empresas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Empresa> findAll(Pageable pageable);


    /**
     * Get the "id" empresa.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Empresa> findOne(Long id);

    /**
     * Delete the "id" empresa.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
