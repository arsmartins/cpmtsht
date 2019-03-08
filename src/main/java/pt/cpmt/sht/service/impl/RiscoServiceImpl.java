package pt.cpmt.sht.service.impl;

import pt.cpmt.sht.service.RiscoService;
import pt.cpmt.sht.domain.Risco;
import pt.cpmt.sht.repository.RiscoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Risco.
 */
@Service
@Transactional
public class RiscoServiceImpl implements RiscoService {

    private final Logger log = LoggerFactory.getLogger(RiscoServiceImpl.class);

    private final RiscoRepository riscoRepository;

    public RiscoServiceImpl(RiscoRepository riscoRepository) {
        this.riscoRepository = riscoRepository;
    }

    /**
     * Save a risco.
     *
     * @param risco the entity to save
     * @return the persisted entity
     */
    @Override
    public Risco save(Risco risco) {
        log.debug("Request to save Risco : {}", risco);
        return riscoRepository.save(risco);
    }

    /**
     * Get all the riscos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Risco> findAll() {
        log.debug("Request to get all Riscos");
        return riscoRepository.findAll();
    }


    /**
     * Get one risco by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Risco> findOne(Long id) {
        log.debug("Request to get Risco : {}", id);
        return riscoRepository.findById(id);
    }

    /**
     * Delete the risco by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Risco : {}", id);
        riscoRepository.deleteById(id);
    }
}
