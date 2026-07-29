package gestion.campushub.repository;

import gestion.campushub.model.Etudiant;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class EtudiantRepository {

    // Stockage en mémoire avec une Map
    private final Map<Long, Etudiant> etudiantsDb = new HashMap<>();
    private Long idSequence = 1L;

    public EtudiantRepository() {
        // CORRECTION : Utilisation de LocalDate et respect de l'ordre (id, nom, prenom, dateNaissance, filiere)
        save(new Etudiant(null, "Diop", "Awa", LocalDate.of(2005, 5, 12), "Informatique"));
        save(new Etudiant(null, "Fall", "Moussa", LocalDate.of(2003, 3, 20), "Gestion"));
    }

    public List<Etudiant> findAll() {
        return new ArrayList<>(etudiantsDb.values());
    }

    public Optional<Etudiant> findById(Long id) {
        return Optional.ofNullable(etudiantsDb.get(id));
    }

    public Etudiant save(Etudiant etudiant) {
        // CORRECTION : Conversion de l'ID en String pour le Record, tout en conservant la séquence Long
        Long numericId = (etudiant.id() != null) ? Long.valueOf(etudiant.id()) : idSequence++;
        
        Etudiant saved = new Etudiant(
            String.valueOf(numericId), 
            etudiant.nom(), 
            etudiant.prenom(), 
            etudiant.dateNaissance(), 
            etudiant.filiere()
        );
        etudiantsDb.put(numericId, saved);
        return saved;
    }

    public boolean deleteById(Long id) {
        return etudiantsDb.remove(id) != null;
    }

    public boolean existsById(Long id) {
        return etudiantsDb.containsKey(id);
    }
}