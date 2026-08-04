package gestion.campushub.repository;

import gestion.campushub.model.Etudiant;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EtudiantRepository {

    // Stockage en mémoire avec une Map
    private final Map<Long, Etudiant> etudiantsDb = new HashMap<>();
    private Long idSequence = 1L;

    public EtudiantRepository() {
        // 2 étudiants par défaut pour tester les GET tout de suite
        save(new Etudiant(null, "Diop", "Awa", "awa.diop@example.com", "Informatique", 21));
        save(new Etudiant(null, "Fall", "Moussa", "moussa.fall@example.com", "Gestion", 23));
    }

    public List<Etudiant> findAll() {
        return new ArrayList<>(etudiantsDb.values());
    }

    public Optional<Etudiant> findById(Long id) {
        return Optional.ofNullable(etudiantsDb.get(id));
    }

    public Etudiant save(Etudiant etudiant) {
        Long id = (etudiant.id() != null) ? etudiant.id() : idSequence++;
        Etudiant saved = new Etudiant(id, etudiant.nom(), etudiant.prenom(), etudiant.email(), etudiant.filiere(), etudiant.age());
        etudiantsDb.put(id, saved);
        return saved;
    }

    public boolean deleteById(Long id) {
        return etudiantsDb.remove(id) != null;
    }

    public boolean existsById(Long id) {
        return etudiantsDb.containsKey(id);
    }
}