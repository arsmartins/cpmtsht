package pt.cpmt.sht.service;

import pt.cpmt.sht.domain.Coordenadas;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Coordenadas.
 */
public interface CoordenadasService {

    /**
     * Save a coordenadas.
     *
     * @param coordenadas the entity to save
     * @return the persisted entity
     */
    Coordenadas save(Coordenadas coordenadas);

    /**
     * Get all the coordenadas.
     *
     * @return the list of entities
     */
    List<Coordenadas> findAll();


    /**
     * Get the "id" coordenadas.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Coordenadas> findOne(Long id);

    /**
     * Delete the "id" coordenadas.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
