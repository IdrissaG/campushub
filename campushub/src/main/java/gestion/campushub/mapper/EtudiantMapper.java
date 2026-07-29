package gestion.campushub.mapper;

import gestion.campushub.dto.EtudiantRequest;
import gestion.campushub.dto.EtudiantResponse;
import gestion.campushub.model.Etudiant;
import org.springframework.stereotype.Component;

@Component
public class EtudiantMapper {

    public Etudiant toEntity(EtudiantRequest request) {
        if (request == null) {
            return null;
        }
        // Ordre : id, nom, prenom, dateNaissance, filiere
        return new Etudiant(
            null, 
            request.nom(), 
            request.prenom(), 
            request.dateNaissance(), 
            request.filiere()
        );
    }

    public EtudiantResponse toResponse(Etudiant etudiant) {
        if (etudiant == null) {
            return null;
        }
        return new EtudiantResponse(
            etudiant.id(),
            etudiant.nom(),
            etudiant.prenom(),
            etudiant.dateNaissance(),
            etudiant.getAge(),
            etudiant.filiere()
        );
    }
}