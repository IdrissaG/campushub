package com.campushub.campushub.service;

import com.campushub.campushub.model.Etudiant;
import com.campushub.campushub.model.Inscription;
import com.campushub.campushub.testutil.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class GestionEtudiantsTest {

    private GestionEtudiants gestionEtudiants;
    private List<Etudiant> etudiants;
    private List<Inscription> inscriptions;

    @BeforeEach
    void setUp() {
        gestionEtudiants = new GestionEtudiants();
        etudiants = DataGenerator.generateSampleEtudiants();
        inscriptions = DataGenerator.generateSampleInscriptions(etudiants);
    }

    @Test
    @DisplayName("1. Devrait calculer la moyenne d'âge exacte des étudiants")
    void devraitCalculerMoyenneAge() {
        double moyenne = gestionEtudiants.calculerMoyenneAge(etudiants);
        // Utilisation d'une marge (offset) pour gérer précisément les centièmes (21.67)
        assertThat(moyenne).isCloseTo(21.67, within(0.01));
    }

    @Test
    @DisplayName("2. Devrait regrouper correctement les étudiants par filière")
    void devraitGrouperLesEtudiantsParFiliere() {
        Map<String, List<Etudiant>> parFiliere = gestionEtudiants.grouperParFiliere(etudiants);

        assertThat(parFiliere).containsKey("Informatique");
        assertThat(parFiliere).containsKey("Management");
        assertThat(parFiliere.get("Informatique")).hasSize(2);
        assertThat(parFiliere.get("Management")).hasSize(1);
    }

    @Test
    @DisplayName("3. Devrait extraire exactement le Top 3 des meilleures inscriptions par note")
    void devraitObtenirLeTop3DesNotes() {
        List<Inscription> top3 = gestionEtudiants.obtenirTop3ParNote(inscriptions);

        assertThat(top3).hasSize(3);
        assertThat(top3.get(0).note()).isEqualTo(19.0);
        assertThat(top3.get(1).note()).isEqualTo(18.0);
        assertThat(top3.get(2).note()).isEqualTo(14.5);
    }
}