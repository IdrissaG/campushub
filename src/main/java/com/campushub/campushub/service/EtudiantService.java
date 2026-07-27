package com.campushub.campushub.service;

import com.campushub.campushub.model.Etudiant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EtudiantService {

    public static double moyenneAge(List<Etudiant> etudiants) {
        return etudiants.stream()
                .mapToInt(Etudiant::age)
                .average()
                .orElse(0.0);
    }

    public static Map<String, List<Etudiant>> grouperParFiliere(List<Etudiant> etudiants) {
        return etudiants.stream()
                .collect(Collectors.groupingBy(Etudiant::filiere));
    }

    public static List<Etudiant> top3ParNote(List<Etudiant> etudiants) {
        return etudiants.stream()
                .sorted((a, b) -> Double.compare(b.note(), a.note()))
                .limit(3)
                .toList();
    }
}