package gestion.campushub.service;

import gestion.campushub.model.Etudiant;
import gestion.campushub.repository.EtudiantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EtudiantsService {

    private final EtudiantRepository repository;

    // Injection par constructeur (Exigence du sujet : pas de @Autowired)
    public EtudiantsService(EtudiantRepository repository) {
        this.repository = repository;
    }

    public Page<Etudiant> getAllEtudiants(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Etudiant> getEtudiantById(Long id) {
        return repository.findById(id);
    }

    public Etudiant createEtudiant(Etudiant etudiant) {
        return repository.save(etudiant);
    }

    public Optional<Etudiant> updateEtudiant(Long id, Etudiant newEtudiant) {
        return repository.findById(id).map(existing -> {
            existing.setNom(newEtudiant.getNom());
            existing.setPrenom(newEtudiant.getPrenom());
            existing.setEmail(newEtudiant.getEmail());
            existing.setAge(newEtudiant.getAge());
            existing.setFiliere(newEtudiant.getFiliere());
            return repository.save(existing);
        });
    }

    public boolean deleteEtudiant(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}