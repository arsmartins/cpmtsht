package pt.cpmt.sht.repository;

import pt.cpmt.sht.domain.Estabelecimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Estabelecimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {

}
