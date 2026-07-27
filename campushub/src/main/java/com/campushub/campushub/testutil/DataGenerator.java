package com.campushub.campushub.testutil;

import com.campushub.campushub.model.Cours;
import com.campushub.campushub.model.Etudiant;
import com.campushub.campushub.model.Inscription;

import java.time.LocalDate;
import java.util.List;

public class DataGenerator {

    public static List<Etudiant> generateSampleEtudiants() {
        // Dates fixées pour obtenir des âges précis en 2026 :
        // Né en 2006 -> 20 ans
        // Né en 2002 -> 24 ans
        // Né en 2004 -> 22 ans
        return List.of(
            new Etudiant("E1", "Diop", "Moussa", LocalDate.of(2006, 5, 10), "Informatique"),
            new Etudiant("E2", "Ndiaye", "Fatou", LocalDate.of(2002, 11, 23), "Informatique"),
            new Etudiant("E3", "Sow", "Amadou", LocalDate.of(2004, 3, 15), "Management")
        );
    }

    public static List<Inscription> generateSampleInscriptions(List<Etudiant> etudiants) {
        Cours java = new Cours("C1", "Dev-Java", "Introduction à Spring Boot");
        Cours algo = new Cours("C2", "Algo-101", "Algorithmique avancée");

        return List.of(
            new Inscription("I1", etudiants.get(0), java, 14.5, "TEMPS_COMPLET"),
            new Inscription("I2", etudiants.get(1), java, 18.0, "TEMPS_COMPLET"),
            new Inscription("I3", etudiants.get(2), algo, 11.0, "TEMPS_PARTIEL"),
            new Inscription("I4", etudiants.get(0), algo, 19.0, "TEMPS_COMPLET")
        );
    }
}