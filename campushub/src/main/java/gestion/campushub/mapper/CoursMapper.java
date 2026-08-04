package gestion.campushub.mapper;


import gestion.campushub.DTO.CoursRequest;
import gestion.campushub.DTO.CoursResponse;
import gestion.campushub.model.Cours;

import java.util.List;

public class CoursMapper {
    public static CoursResponse toResponse(Cours cours){
        CoursResponse coursResponse = new CoursResponse(cours.code(), cours.nom());
        return coursResponse;
    }

    public static CoursRequest toRequest(Cours cours){
        CoursRequest coursRequest = new CoursRequest(cours.code(), cours.nom());
        return coursRequest;
    }

    public static Cours toCours(CoursRequest coursRequest){
        Cours cours = new Cours(coursRequest.code(), coursRequest.nom());
        return cours;
    }

    public static List<CoursRequest> toRequests(List<Cours> cours){
        List<CoursRequest> coursRequests = cours.stream().map(CoursMapper::toRequest).toList();
        return coursRequests;
    }

    public static List<CoursResponse> toResponses(List<Cours> cours){
        List<CoursResponse> coursResponses = cours.stream().map(CoursMapper::toResponse).toList();
        return coursResponses;
    }


    public static List<Cours> toCoursList(List<CoursRequest> requests) {
        return requests.stream().map(CoursMapper::toCours).toList();
    }


}
