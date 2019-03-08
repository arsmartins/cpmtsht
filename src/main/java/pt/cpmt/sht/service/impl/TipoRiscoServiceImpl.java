package pt.cpmt.sht.service.impl;

import pt.cpmt.sht.service.TipoRiscoService;
import pt.cpmt.sht.domain.TipoRisco;
import pt.cpmt.sht.repository.TipoRiscoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing TipoRisco.
 */
@Service
@Transactional
public class TipoRiscoServiceImpl implements TipoRiscoService {

    private final Logger log = LoggerFactory.getLogger(TipoRiscoServiceImpl.class);

    private final TipoRiscoRepository tipoRiscoRepository;

    public TipoRiscoServiceImpl(TipoRiscoRepository tipoRiscoRepository) {
        this.tipoRiscoRepository = tipoRiscoRepository;
    }

    /**
     * Save a tipoRisco.
     *
     * @param tipoRisco the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoRisco save(TipoRisco tipoRisco) {
        log.debug("Request to save TipoRisco : {}", tipoRisco);
        return tipoRiscoRepository.save(tipoRisco);
    }

    /**
     * Get all the tipoRiscos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoRisco> findAll() {
        log.debug("Request to get all TipoRiscos");
        return tipoRiscoRepository.findAll();
    }


    /**
     * Get one tipoRisco by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoRisco> findOne(Long id) {
        log.debug("Request to get TipoRisco : {}", id);
        return tipoRiscoRepository.findById(id);
    }

    /**
     * Delete the tipoRisco by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoRisco : {}", id);
        tipoRiscoRepository.deleteById(id);
    }
}
