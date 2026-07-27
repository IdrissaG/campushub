package com.campushub.campushub.service;

import com.campushub.campushub.model.Etudiant;
import com.campushub.campushub.model.Inscription;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GestionEtudiants {

    /**
     * 1. Calcule la moyenne d'âge de tous les étudiants reçus en paramètre
     */
    public double calculerMoyenneAge(List<Etudiant> etudiants) {
        return etudiants.stream()
                .mapToInt(Etudiant::getAge)
                .average()
                .orElse(0.0);
    }

    /**
     * 2. Regroupe les étudiants par filière
     */
    public Map<String, List<Etudiant>> grouperParFiliere(List<Etudiant> etudiants) {
        return etudiants.stream()
                .collect(Collectors.groupingBy(Etudiant::filiere));
    }

    /**
     * 3. Extrait le Top 3 des meilleures inscriptions basées sur la note
     */
    public List<Inscription> obtenirTop3ParNote(List<Inscription> inscriptions) {
        return inscriptions.stream()
                .sorted((i1, i2) -> Double.compare(i2.note(), i1.note())) // Tri décroissant (du meilleur au moins bon)
                .limit(3)
                .toList();
    }
}