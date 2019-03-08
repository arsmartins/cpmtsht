package pt.cpmt.sht.service;

import pt.cpmt.sht.domain.Estabelecimento;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Estabelecimento.
 */
public interface EstabelecimentoService {

    /**
     * Save a estabelecimento.
     *
     * @param estabelecimento the entity to save
     * @return the persisted entity
     */
    Estabelecimento save(Estabelecimento estabelecimento);

    /**
     * Get all the estabelecimentos.
     *
     * @return the list of entities
     */
    List<Estabelecimento> findAll();


    /**
     * Get the "id" estabelecimento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Estabelecimento> findOne(Long id);

    /**
     * Delete the "id" estabelecimento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
