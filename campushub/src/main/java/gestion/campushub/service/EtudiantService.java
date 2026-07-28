package gestion.campushub.service;

import gestion.campushub.model.Etudiant;
import gestion.campushub.repository.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    private final EtudiantRepository repository;

    public EtudiantService(EtudiantRepository repository) {
        this.repository = repository;
    }

    public List<Etudiant> getAll() {
        return repository.findAll();
    }

    public Optional<Etudiant> getById(Long id) {
        return repository.findById(id);
    }

    public Etudiant create(Etudiant etudiant) {
        return repository.save(etudiant);
    }

    public Optional<Etudiant> update(Long id, Etudiant etudiant) {
        return repository.update(id, etudiant);
    }

    public boolean delete(Long id) {
        return repository.deleteById(id);
    }
}
