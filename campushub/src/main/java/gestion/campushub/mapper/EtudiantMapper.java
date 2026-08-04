package gestion.campushub.mapper;

import gestion.campushub.DTO.EtudiantRequest;
import gestion.campushub.DTO.EtudiantResponse;
import gestion.campushub.model.Etudiant;

public class EtudiantMapper {

    public static EtudiantResponse toResponse(Etudiant etudiant) {
        return new EtudiantResponse(
            etudiant.getId(),
            etudiant.getNom(),
            etudiant.getPrenom(),
            etudiant.getEmail(),
            etudiant.getAge(),
            etudiant.getFiliere()
        );
    }

    public static Etudiant toEntity(EtudiantRequest request) {
        return new Etudiant(
            request.nom(),
            request.prenom(),
            request.email(),
            request.age(),
            request.filiere()

        );
    }
}
