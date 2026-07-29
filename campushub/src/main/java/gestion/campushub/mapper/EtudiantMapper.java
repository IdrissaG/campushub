package gestion.campushub.mapper;

import gestion.campushub.DTO.EtudiantRequest;
import gestion.campushub.DTO.EtudiantResponse;
import gestion.campushub.model.Etudiant;

public class EtudiantMapper {

    public static EtudiantResponse toResponse(Etudiant etudiant) {
        return new EtudiantResponse(
            etudiant.id(),
            etudiant.nom(),
            etudiant.prenom(),
            etudiant.email(),
            etudiant.age(),
            etudiant.filiere()
        );
    }

    public static Etudiant toEntity(EtudiantRequest request) {
        return new Etudiant(
            null,
            request.nom(),
            request.prenom(),
            request.email(),
            request.filiere(),
            request.age()
        );
    }
}
