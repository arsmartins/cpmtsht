package pt.cpmt.sht.service.impl;

import pt.cpmt.sht.service.EstabelecimentoService;
import pt.cpmt.sht.domain.Estabelecimento;
import pt.cpmt.sht.repository.EstabelecimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Estabelecimento.
 */
@Service
@Transactional
public class EstabelecimentoServiceImpl implements EstabelecimentoService {

    private final Logger log = LoggerFactory.getLogger(EstabelecimentoServiceImpl.class);

    private final EstabelecimentoRepository estabelecimentoRepository;

    public EstabelecimentoServiceImpl(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    /**
     * Save a estabelecimento.
     *
     * @param estabelecimento the entity to save
     * @return the persisted entity
     */
    @Override
    public Estabelecimento save(Estabelecimento estabelecimento) {
        log.debug("Request to save Estabelecimento : {}", estabelecimento);
        return estabelecimentoRepository.save(estabelecimento);
    }

    /**
     * Get all the estabelecimentos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Estabelecimento> findAll() {
        log.debug("Request to get all Estabelecimentos");
        return estabelecimentoRepository.findAll();
    }


    /**
     * Get one estabelecimento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Estabelecimento> findOne(Long id) {
        log.debug("Request to get Estabelecimento : {}", id);
        return estabelecimentoRepository.findById(id);
    }

    /**
     * Delete the estabelecimento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estabelecimento : {}", id);
        estabelecimentoRepository.deleteById(id);
    }
}
