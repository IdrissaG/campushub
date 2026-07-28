package com.campushub.service;

import com.campushub.model.Cours;
import com.campushub.model.Etudiant;
import com.campushub.model.Inscription;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantServiceTest {

        private final EtudiantService service = new EtudiantService();

        private final List<Etudiant> etudiants = List.of(
                new Etudiant(1L, "Ali", 22, "Génie Civil"),
                new Etudiant(2L, "Fatou", 21, "Génie Electromécanique"),
                new Etudiant(3L, "Moussa", 24, "Génie Informatique"),
                new Etudiant(4L, "Awa", 20, "Génie Aéronautique"),
                new Etudiant(5L, "Mamadou", 21, "Génie Informatique")
        );

        private final Cours java = new Cours(1L, "Java");
        private final Cours reseaux = new Cours(2L, "Réseaux");

        private final List<Inscription> inscriptions = List.of(
                new Inscription(etudiants.get(0), java, 15),
                new Inscription(etudiants.get(1), java, 18),
                new Inscription(etudiants.get(2), reseaux, 17),
                new Inscription(etudiants.get(3), reseaux, 19)
        );

        @Test
        void doitCalculerMoyenneAge() {

                assertEquals(
                        21.6,
                        service.moyenneAge(etudiants),
                        0.001
                );
        }

        @Test
        void doitGrouperParFiliere() {

                var resultat = service.grouperParFiliere(etudiants);

                assertEquals(1, resultat.get("Génie Civil").size());
                assertEquals(1, resultat.get("Génie Electromécanique").size());
                assertEquals(2, resultat.get("Génie Informatique").size());
                assertEquals(1, resultat.get("Génie Aéronautique").size());
        }

        @Test
        void doitRetournerTop3() {

        var resultat = service.top3ParNote(inscriptions);

        assertEquals(3, resultat.size());

        // 1er
        assertEquals("Awa", resultat.get(0).etudiant().nom());
        assertEquals(19, resultat.get(0).note());

        // 2e
        assertEquals("Fatou", resultat.get(1).etudiant().nom());
        assertEquals(18, resultat.get(1).note());

        // 3e
        assertEquals("Moussa", resultat.get(2).etudiant().nom());
        assertEquals(17, resultat.get(2).note());
        }
}