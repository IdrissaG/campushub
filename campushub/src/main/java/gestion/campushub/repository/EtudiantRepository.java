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
        save(new Etudiant( "Diop", "Awa",  "Informatique", 21,"awa.diop@example.com"));
        save(new Etudiant( "Fall", "Moussa", "moussa.fall@example.com",  23,"Gestion"));
    }

    public List<Etudiant> findAll() {
        return new ArrayList<>(etudiantsDb.values());
    }

    public Optional<Etudiant> findById(Long id) {
        return Optional.ofNullable(etudiantsDb.get(id));
    }

    public Etudiant save(Etudiant etudiant) {
        Long id = (etudiant.getId() != null) ? etudiant.getId() : idSequence++;
        Etudiant saved = new Etudiant( etudiant.getNom(), etudiant.getPrenom(), etudiant.getEmail(), etudiant.getAge(),etudiant.getFiliere());
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