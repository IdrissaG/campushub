package gestion.campushub.service;

import gestion.campushub.DTO.CoursRequest;
import gestion.campushub.DTO.CoursResponse;
import gestion.campushub.mapper.CoursMapper;
import gestion.campushub.model.Cours;
import gestion.campushub.repository.CoursRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static gestion.campushub.mapper.CoursMapper.*;

@Service
public class CoursService {

    private final CoursRepository coursRepository;


    public CoursService(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    public List<CoursResponse> getAllCours() {
        return toResponses(coursRepository.findAll());
    }

    public Optional<CoursResponse> getCoursByCode(String code) {
        return coursRepository.findByCode(code).map(CoursMapper::toResponse);
    }

    public CoursResponse createCours(CoursRequest coursRequest) {
        return toResponse(coursRepository.save(toCours(coursRequest)));
    }

    public Optional<CoursResponse> updateCours(String code, CoursRequest cours) {
        if (!coursRepository.existsByCode(code)) {
            return Optional.empty();
        }
        Cours coursAjour = new Cours(code, cours.nom());
        return Optional.of(toResponse(coursRepository.save(coursAjour)));
    }

    public boolean deleteCours(String code) {
        return coursRepository.findByCode(code)
                .map(cours -> {
                    coursRepository.delete(cours);
                    return true;
                })
                .orElse(false);
    }
}
