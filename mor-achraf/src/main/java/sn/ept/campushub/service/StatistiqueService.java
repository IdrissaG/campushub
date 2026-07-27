package sn.ept.campushub.service;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import sn.ept.campushub.domain.Etudiant;
import sn.ept.campushub.domain.Filiere;

@Service
public class StatistiqueService {

    public OptionalDouble moyenneAge(List<Etudiant> etudiants) {
        return etudiants.stream()
                .mapToInt(Etudiant::age)
                .average();
    }

    public Map<Filiere, List<Etudiant>> grouperParFiliere(List<Etudiant> etudiants) {
        return etudiants.stream()
                .collect(Collectors.groupingBy(Etudiant::filiere));
    }
}