package pt.cpmt.sht.service.impl;

import pt.cpmt.sht.service.CoordenadasService;
import pt.cpmt.sht.domain.Coordenadas;
import pt.cpmt.sht.repository.CoordenadasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Coordenadas.
 */
@Service
@Transactional
public class CoordenadasServiceImpl implements CoordenadasService {

    private final Logger log = LoggerFactory.getLogger(CoordenadasServiceImpl.class);

    private final CoordenadasRepository coordenadasRepository;

    public CoordenadasServiceImpl(CoordenadasRepository coordenadasRepository) {
        this.coordenadasRepository = coordenadasRepository;
    }

    /**
     * Save a coordenadas.
     *
     * @param coordenadas the entity to save
     * @return the persisted entity
     */
    @Override
    public Coordenadas save(Coordenadas coordenadas) {
        log.debug("Request to save Coordenadas : {}", coordenadas);
        return coordenadasRepository.save(coordenadas);
    }

    /**
     * Get all the coordenadas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Coordenadas> findAll() {
        log.debug("Request to get all Coordenadas");
        return coordenadasRepository.findAll();
    }


    /**
     * Get one coordenadas by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Coordenadas> findOne(Long id) {
        log.debug("Request to get Coordenadas : {}", id);
        return coordenadasRepository.findById(id);
    }

    /**
     * Delete the coordenadas by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coordenadas : {}", id);
        coordenadasRepository.deleteById(id);
    }
}
