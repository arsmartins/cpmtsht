package pt.cpmt.sht.repository;

import pt.cpmt.sht.domain.TipoRisco;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoRisco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoRiscoRepository extends JpaRepository<TipoRisco, Long> {

}
