package pt.cpmt.sht.service.impl;

import pt.cpmt.sht.service.LocalizacaoService;
import pt.cpmt.sht.domain.Localizacao;
import pt.cpmt.sht.repository.LocalizacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Localizacao.
 */
@Service
@Transactional
public class LocalizacaoServiceImpl implements LocalizacaoService {

    private final Logger log = LoggerFactory.getLogger(LocalizacaoServiceImpl.class);

    private final LocalizacaoRepository localizacaoRepository;

    public LocalizacaoServiceImpl(LocalizacaoRepository localizacaoRepository) {
        this.localizacaoRepository = localizacaoRepository;
    }

    /**
     * Save a localizacao.
     *
     * @param localizacao the entity to save
     * @return the persisted entity
     */
    @Override
    public Localizacao save(Localizacao localizacao) {
        log.debug("Request to save Localizacao : {}", localizacao);
        return localizacaoRepository.save(localizacao);
    }

    /**
     * Get all the localizacaos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Localizacao> findAll() {
        log.debug("Request to get all Localizacaos");
        return localizacaoRepository.findAll();
    }


    /**
     * Get one localizacao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Localizacao> findOne(Long id) {
        log.debug("Request to get Localizacao : {}", id);
        return localizacaoRepository.findById(id);
    }

    /**
     * Delete the localizacao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Localizacao : {}", id);
        localizacaoRepository.deleteById(id);
    }
}
