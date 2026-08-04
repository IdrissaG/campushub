package gestion.campushub.mapper;

import gestion.campushub.DTO.InscriptionRequest;
import gestion.campushub.DTO.InscriptionResponse;
import gestion.campushub.model.Cours;
import gestion.campushub.model.Etudiant;
import gestion.campushub.model.Inscription;

import java.time.LocalDate;
import java.util.List;

public class InscriptionMapper {

    public static InscriptionResponse toResponse(Inscription inscription) {
        Etudiant etudiant = inscription.getEtudiant();
        Cours cours = inscription.getCours();

        return new InscriptionResponse(
                inscription.getId(), etudiant.getId(), etudiant.getNom(), etudiant.getPrenom(), cours.getId(), cours.getCode(), cours.getNom(), inscription.getNote(), inscription.getDateInscription()
        );
    }

    public static List<InscriptionResponse> toResponses(List<Inscription> inscriptions) {
        return inscriptions.stream()
                .map(InscriptionMapper::toResponse)
                .toList();
    }

    public static Inscription toEntity(
            InscriptionRequest request,
            Etudiant etudiant,
            Cours cours,
            LocalDate dateInscription
    ) {
        return new Inscription(etudiant, cours, request.note(), dateInscription);
    }
}
