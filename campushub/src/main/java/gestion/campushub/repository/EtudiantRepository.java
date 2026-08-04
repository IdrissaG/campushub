package gestion.campushub.repository;

import gestion.campushub.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    List<Etudiant> findByFiliere(String filiere);

    Optional<Etudiant> findByEmail(String email);
}
