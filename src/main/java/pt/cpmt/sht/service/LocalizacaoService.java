package pt.cpmt.sht.service;

import pt.cpmt.sht.domain.Localizacao;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Localizacao.
 */
public interface LocalizacaoService {

    /**
     * Save a localizacao.
     *
     * @param localizacao the entity to save
     * @return the persisted entity
     */
    Localizacao save(Localizacao localizacao);

    /**
     * Get all the localizacaos.
     *
     * @return the list of entities
     */
    List<Localizacao> findAll();


    /**
     * Get the "id" localizacao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Localizacao> findOne(Long id);

    /**
     * Delete the "id" localizacao.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
