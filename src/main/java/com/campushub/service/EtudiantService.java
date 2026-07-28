package com.campushub.service;

import com.campushub.model.Etudiant;
import com.campushub.model.Inscription;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EtudiantService {


    public double moyenneAge(List<Etudiant> etudiants) {

        return etudiants.stream()
                .mapToInt(Etudiant::age)
                .average()
                .orElse(0);
    }


    public Map<String, List<Etudiant>> grouperParFiliere(
            List<Etudiant> etudiants) {

        return etudiants.stream()
                .collect(Collectors.groupingBy(
                        Etudiant::filiere
                ));
    }


    public List<Inscription> top3ParNote(List<Inscription> inscriptions) {

        return inscriptions.stream()
                .sorted(
                        Comparator.comparing(Inscription::note)
                                .reversed()
                )
                .limit(3)
                .toList();
    }
}
