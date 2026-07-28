package gestion.campushub.repository;

import gestion.campushub.model.Etudiant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EtudiantRepository {

    private final Map<Long, Etudiant> etudiants = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public List<Etudiant> findAll() {
        return List.copyOf(etudiants.values());
    }

    public Optional<Etudiant> findById(Long id) {
        return Optional.ofNullable(etudiants.get(id));
    }

    public Etudiant save(Etudiant etudiant) {
        long id = sequence.incrementAndGet();
        Etudiant saved = new Etudiant(id, etudiant.nom(), etudiant.age(), etudiant.filiere());
        etudiants.put(id, saved);
        return saved;
    }

    public Optional<Etudiant> update(Long id, Etudiant etudiant) {
        if (!etudiants.containsKey(id)) {
            return Optional.empty();
        }
        Etudiant updated = new Etudiant(id, etudiant.nom(), etudiant.age(), etudiant.filiere());
        etudiants.put(id, updated);
        return Optional.of(updated);
    }

    public boolean deleteById(Long id) {
        return etudiants.remove(id) != null;
    }
}
