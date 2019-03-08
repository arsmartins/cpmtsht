package pt.cpmt.sht.service;

import pt.cpmt.sht.domain.TipoRisco;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TipoRisco.
 */
public interface TipoRiscoService {

    /**
     * Save a tipoRisco.
     *
     * @param tipoRisco the entity to save
     * @return the persisted entity
     */
    TipoRisco save(TipoRisco tipoRisco);

    /**
     * Get all the tipoRiscos.
     *
     * @return the list of entities
     */
    List<TipoRisco> findAll();


    /**
     * Get the "id" tipoRisco.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoRisco> findOne(Long id);

    /**
     * Delete the "id" tipoRisco.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
