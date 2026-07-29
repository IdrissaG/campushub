package gestion.campushub.mapper;

import gestion.campushub.dto.CoursRequest;
import gestion.campushub.dto.CoursResponse;
import gestion.campushub.model.Cours;
import org.springframework.stereotype.Component;

@Component
public class CoursMapper {

    public Cours toEntity(CoursRequest request) {
        if (request == null) {
            return null;
        }
        return new Cours(request.code(), request.nom(), request.credits());
    }

    public CoursResponse toResponse(Cours cours) {
        if (cours == null) {
            return null;
        }
        return new CoursResponse(cours.code(), cours.nom(), cours.credits());
    }
} //ici, nous avons un mapper pour les cours, qui convertit entre les objets CoursRequest, CoursResponse et l'entité Cours. Le mapper est annoté avec @Component pour être géré par Spring et injecté dans le contrôleur.

// en termes simples, le rôle du CoursMapper est de faciliter la conversion entre les différentes représentations d'un cours dans l'application, en s'assurant que les données sont correctement transférées entre les couches de l'application.