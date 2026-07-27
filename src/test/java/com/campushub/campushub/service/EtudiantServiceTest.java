package com.campushub.campushub.service;

import com.campushub.campushub.model.Etudiant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantServiceTest {

    private List<Etudiant> etudiants;

    @BeforeEach
    void setup() {
        etudiants = List.of(
            new Etudiant("Alice", 20, "Informatique", 17.0),
            new Etudiant("Bob", 22, "Informatique", 12.0),
            new Etudiant("Charlie", 21, "Gestion", 15.0),
            new Etudiant("Diana", 23, "Gestion", 9.0),
            new Etudiant("Eve", 20, "Réseau", 18.0)
        );
    }

    @Test
    @DisplayName("Devrait calculer la moyenne d'âge")
    void devraitCalculerMoyenneDAge() {
        double moyenne = EtudiantService.moyenneAge(etudiants);
        assertEquals(21.2, moyenne, 0.001);
    }

    @Test
    @DisplayName("Devrait grouper les étudiants par filière")
    void devraitGrouperParFiliere() {
        Map<String, List<Etudiant>> groupes = EtudiantService.grouperParFiliere(etudiants);
        assertNotNull(groupes);
        assertEquals(3, groupes.size());
        assertEquals(2, groupes.get("Informatique").size());
    }

    @Test
    @DisplayName("Devrait retourner le top 3 par note")
    void devraitRetournerTop3() {
        List<Etudiant> top3 = EtudiantService.top3ParNote(etudiants);
        assertEquals(3, top3.size());
        assertEquals("Eve", top3.get(0).nom());
        assertEquals("Alice", top3.get(1).nom());
        assertEquals("Charlie", top3.get(2).nom());
    }
}