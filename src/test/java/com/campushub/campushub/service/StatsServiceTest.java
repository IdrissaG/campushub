package com.campushub.campushub.service;

import com.campushub.campushub.model.Cours;
import com.campushub.campushub.model.Etudiant;
import com.campushub.campushub.model.Inscription;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatsServiceTest {

    private final StatsService service = new StatsService();

    @Test
    void moyenneAge_retourneLaMoyenneCorrecte() {
        List<Etudiant> etudiants = List.of(
                new Etudiant(1L, "Ali", 20, "Info"),
                new Etudiant(2L, "Awa", 22, "Info")
        );

        double moyenne = service.moyenneAge(etudiants);

        assertEquals(21.0, moyenne);
    }

    @Test
    void groupementParFiliere_grouperCorrectement() {
        List<Etudiant> etudiants = List.of(
                new Etudiant(1L, "Ali", 20, "Info"),
                new Etudiant(2L, "Awa", 22, "Math"),
                new Etudiant(3L, "Fatou", 21, "Info")
        );

        Map<String, List<Etudiant>> parFiliere = service.groupementParFiliere(etudiants);

        assertEquals(2, parFiliere.size());
        assertEquals(2, parFiliere.get("Info").size());
        assertEquals(1, parFiliere.get("Math").size());
    }

    @Test
    void top3ParNote_retourneLes3MeilleuresNotes() {
        Etudiant e = new Etudiant(1L, "X", 20, "Info");
        Cours c = new Cours(1L, "Java", 3);
        List<Inscription> inscriptions = List.of(
                new Inscription(e, c, 12),
                new Inscription(e, c, 18),
                new Inscription(e, c, 15),
                new Inscription(e, c, 10)
        );

        List<Inscription> top = service.top3ParNote(inscriptions);

        assertEquals(3, top.size());
        assertEquals(18, top.get(0).note());
        assertEquals(15, top.get(1).note());
        assertEquals(12, top.get(2).note());
        assertTrue(top.stream().noneMatch(i -> i.note() == 10));
    }
}
