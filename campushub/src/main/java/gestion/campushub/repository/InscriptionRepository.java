package gestion.campushub.repository;

import gestion.campushub.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    List<Inscription> findByEtudiantId(Long etudiantId);

    List<Inscription> findByCoursId(Long coursId);

    @Query("SELECT i FROM Inscription i WHERE i.note >= :seuil ORDER BY i.note DESC")
    List<Inscription> findAvecNoteMinimum(@Param("seuil") double seuil);
}
