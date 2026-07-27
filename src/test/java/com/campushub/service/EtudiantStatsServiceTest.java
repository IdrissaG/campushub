package com.campushub.service;

import com.campushub.model.ClassementEtudiant;
import com.campushub.model.Etudiant;
import com.campushub.model.Inscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class EtudiantStatsServiceTest {

    private final EtudiantStatsService service = new EtudiantStatsService();

    private List<Etudiant> etudiants;
    private List<Inscription> inscriptions;

    @BeforeEach
    void setUp() {
        etudiants = List.of(
                new Etudiant(1L, "Awa", "Diop", 22, "Informatique"),
                new Etudiant(2L, "Moussa", "Ba", 24, "Informatique"),
                new Etudiant(3L, "Fatou", "Sarr", 21, "Gestion"),
                new Etudiant(4L, "Ibrahima", "Ndiaye", 23, "Gestion"),
                new Etudiant(5L, "Khady", "Fall", 25, "Informatique")
        );

        inscriptions = List.of(
                new Inscription(1L, 1L, 1L, 15.0),
                new Inscription(2L, 1L, 2L, 17.0),
                new Inscription(3L, 2L, 1L, 12.0),
                new Inscription(4L, 3L, 1L, 18.0),
                new Inscription(5L, 4L, 1L, 14.0),
                new Inscription(6L, 4L, 2L, 16.0),
                new Inscription(7L, 5L, 1L, 19.0),
                new Inscription(8L, 5L, 2L, 20.0)
        );
    }

    @Test
    void moyenneAge_calculeLaMoyenneCorrecte() {
        // (22 + 24 + 21 + 23 + 25) / 5 = 23.0
        double moyenne = service.moyenneAge(etudiants);

        assertThat(moyenne).isCloseTo(23.0, within(0.001));
    }

    @Test
    void groupParFiliere_regroupeLesEtudiantsParFiliere() {
        Map<String, List<Etudiant>> parFiliere = service.groupParFiliere(etudiants);

        assertThat(parFiliere).containsOnlyKeys("Informatique", "Gestion");
        assertThat(parFiliere.get("Informatique")).hasSize(3)
                .extracting(Etudiant::prenom)
                .containsExactlyInAnyOrder("Awa", "Moussa", "Khady");
        assertThat(parFiliere.get("Gestion")).hasSize(2)
                .extracting(Etudiant::prenom)
                .containsExactlyInAnyOrder("Fatou", "Ibrahima");
    }

    @Test
    void top3ParNote_retourneLes3MeilleursEtudiantsParOrdreDecroissant() {
        // Moyennes attendues : Khady 19.5, Fatou 18.0, Awa 16.0, Ibrahima 15.0, Moussa 12.0
        List<ClassementEtudiant> top3 = service.top3ParNote(etudiants, inscriptions);

        assertThat(top3).hasSize(3);
        assertThat(top3.get(0).etudiant().prenom()).isEqualTo("Khady");
        assertThat(top3.get(0).moyenneNote()).isCloseTo(19.5, within(0.001));
        assertThat(top3.get(1).etudiant().prenom()).isEqualTo("Fatou");
        assertThat(top3.get(2).etudiant().prenom()).isEqualTo("Awa");
    }
}
