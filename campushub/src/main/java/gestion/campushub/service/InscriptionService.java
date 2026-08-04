package gestion.campushub.service;


import gestion.campushub.DTO.InscriptionRequest;
import gestion.campushub.DTO.InscriptionResponse;
import gestion.campushub.mapper.InscriptionMapper;
import gestion.campushub.model.Cours;
import gestion.campushub.model.Etudiant;
import gestion.campushub.repository.CoursRepository;
import gestion.campushub.repository.InscriptionRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService {
    private final InscriptionRepository inscriptionRepository;
    private final CoursRepository coursRepository;
    private final EntityManager entityManager;

    public InscriptionService(
            InscriptionRepository inscriptionRepository,
            CoursRepository coursRepository,
            EntityManager entityManager
    ) {
        this.inscriptionRepository = inscriptionRepository;
        this.coursRepository = coursRepository;
        this.entityManager = entityManager;
    }


    public List<InscriptionResponse> toutesLesInscriptions() {
        return InscriptionMapper.toResponses(inscriptionRepository.findAll());
    }


    public List<InscriptionResponse> inscriptionsParEtudiant(Long etudiantId) {
        return InscriptionMapper.toResponses(inscriptionRepository.findByEtudiantId(etudiantId));
    }


    public List<InscriptionResponse> inscriptionsParCours(Long coursId) {
        return InscriptionMapper.toResponses(inscriptionRepository.findByCoursId(coursId));
    }


    public List<InscriptionResponse> inscriptionsAvecNoteMinimum(double seuil) {
        return InscriptionMapper.toResponses(inscriptionRepository.findAvecNoteMinimum(seuil));
    }


    public Optional<InscriptionResponse> creerInscription(InscriptionRequest request) {
        Etudiant etudiant = entityManager.find(Etudiant.class, request.etudiantId());
        Optional<Cours> cours = coursRepository.findById(request.coursId());

        if (etudiant == null || cours.isEmpty()) {
            return Optional.empty();
        }

        LocalDate dateInscription;

        if (request.dateInscription() != null) {
            dateInscription = request.dateInscription();
        } else {
            dateInscription = LocalDate.now();
        }

        return Optional.of(
                InscriptionMapper.toResponse(
                        inscriptionRepository.save(
                                InscriptionMapper.toEntity(request, etudiant, cours.get(), dateInscription)
                        )
                )
        );
    }

}
