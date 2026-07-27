package com.ept.campushub.service;

import com.ept.campushub.model.Etudiant;
import com.ept.campushub.model.Inscription;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CampusService {

    // 1. Moyenne d age des etudiants
    public double moyenneAge(List<Etudiant> etudiants) {
        return etudiants.stream()
                .mapToInt(Etudiant::age)
                .average()
                .orElse(0);
    }

    // 2. Groupement des etudiants par filière
    public Map<String, List<Etudiant>> groupementParFiliere(List<Etudiant> etudiants) {
        return etudiants.stream()
                .collect(Collectors.groupingBy(Etudiant::filiere));
    }

    // 3. Top 3 des inscriptions par note decroissante
    public List<Inscription> top3ParNote(List<Inscription> inscriptions) {
        return inscriptions.stream()
                .sorted(Comparator.comparingDouble(Inscription::note).reversed())
                .limit(3)
                .toList();
    }
}
