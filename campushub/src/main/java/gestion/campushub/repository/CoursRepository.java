package gestion.campushub.repository;

import gestion.campushub.model.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoursRepository extends JpaRepository<Cours,Long> {

    Optional<Cours> findByCode(String code);

    boolean existsByCode(String code);
}
