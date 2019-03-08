package pt.cpmt.sht.repository;

import pt.cpmt.sht.domain.Coordenadas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Coordenadas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoordenadasRepository extends JpaRepository<Coordenadas, Long> {

}
