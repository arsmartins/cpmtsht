package pt.cpmt.sht.repository;

import pt.cpmt.sht.domain.PostoTrabalho;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PostoTrabalho entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostoTrabalhoRepository extends JpaRepository<PostoTrabalho, Long> {

}
