package pt.cpmt.sht.repository;

import pt.cpmt.sht.domain.Localizacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Localizacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {

}
