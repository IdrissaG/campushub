package gestion.campushub.service;

import gestion.campushub.model.Etudiant;
import gestion.campushub.repository.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantsService {

    private final EtudiantRepository repository;

    // Injection par constructeur (Exigence du sujet : pas de @Autowired)
    public EtudiantsService(EtudiantRepository repository) {
        this.repository = repository;
    }

    public List<Etudiant> getAllEtudiants() {
        return repository.findAll();
    }

    public Optional<Etudiant> getEtudiantById(Long id) {
        return repository.findById(id);
    }

    public Etudiant createEtudiant(Etudiant etudiant) {
        return repository.save(etudiant);
    }

    public Optional<Etudiant> updateEtudiant(Long id, Etudiant newEtudiant) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        Etudiant updated = new Etudiant(
                newEtudiant.getNom(),
                newEtudiant.getPrenom(),
                newEtudiant.getEmail(),
                newEtudiant.getAge(),
                newEtudiant.getFiliere());
        return Optional.of(repository.save(updated));
    }

    public boolean deleteEtudiant(Long id) {
        return repository.deleteById(id);
    }
}