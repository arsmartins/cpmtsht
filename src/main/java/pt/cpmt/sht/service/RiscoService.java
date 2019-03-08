package pt.cpmt.sht.service;

import pt.cpmt.sht.domain.Risco;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Risco.
 */
public interface RiscoService {

    /**
     * Save a risco.
     *
     * @param risco the entity to save
     * @return the persisted entity
     */
    Risco save(Risco risco);

    /**
     * Get all the riscos.
     *
     * @return the list of entities
     */
    List<Risco> findAll();


    /**
     * Get the "id" risco.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Risco> findOne(Long id);

    /**
     * Delete the "id" risco.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
