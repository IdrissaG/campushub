package gestion.campushub.service;

import gestion.campushub.model.Cours;
import gestion.campushub.repository.CoursRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoursService {

    private final CoursRepository coursRepository;


    public CoursService(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    public Optional<Cours> getCoursByCode(String code) {
        return coursRepository.findByCode(code);
    }

    public Cours createCours(Cours cours) {
        return coursRepository.save(cours);
    }

    public Optional<Cours> updateCours(String code, Cours cours) {
        if (!coursRepository.existsByCode(code)) {
            return Optional.empty();
        }
        Cours coursAjour = new Cours(code, cours.nom());
        return Optional.of(coursRepository.save(coursAjour));
    }

    public boolean deleteCours(String code) {
        return coursRepository.deleteByCode(code);
    }
}
