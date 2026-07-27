package com.ept.campushub.service;

import org.junit.jupiter.api.Test;
import com.ept.campushub.model.Etudiant;
import com.ept.campushub.model.Inscription;
import com.ept.campushub.model.Niveau;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampusServiceTest {

    private final CampusService service = new CampusService();

    @Test
    void testMoyenneAge() {
        List<Etudiant> etudiants = List.of(
                new Etudiant(1L, "Awa", 20, "GIT", Niveau.DIC1, LocalDate.of(2004, 1, 1)),
                new Etudiant(2L, "Moussa", 22, "GE", Niveau.DIC2, LocalDate.of(2002, 1, 1)),
                new Etudiant(3L, "Fatou", 24, "GIT", Niveau.DIC3, LocalDate.of(2000, 1, 1))
        );

        double resultat = service.moyenneAge(etudiants);

        assertEquals(22.0, resultat);
    }

    @Test
    void testGroupementParFiliere() {
        List<Etudiant> etudiants = List.of(
                new Etudiant(1L, "Awa", 20, "GIT", Niveau.DIC1, LocalDate.of(2004, 1, 1)),
                new Etudiant(2L, "Moussa", 22, "GE", Niveau.DIC2, LocalDate.of(2002, 1, 1)),
                new Etudiant(3L, "Fatou", 21, "GIT", Niveau.DIC1, LocalDate.of(2003, 1, 1))
        );

        Map<String, List<Etudiant>> resultat = service.groupementParFiliere(etudiants);

        assertEquals(2, resultat.size());
        assertEquals(2, resultat.get("GIT").size());
        assertEquals(1, resultat.get("GE").size());
    }

    @Test
    void testTop3ParNote() {
        List<Inscription> inscriptions = List.of(
                new Inscription(1L, 1L, 10.0, LocalDate.of(2025, 9, 1)),
                new Inscription(2L, 1L, 18.0, LocalDate.of(2025, 9, 1)),
                new Inscription(3L, 1L, 15.0, LocalDate.of(2025, 9, 1)),
                new Inscription(4L, 1L, 9.0, LocalDate.of(2025, 9, 1)),
                new Inscription(5L, 1L, 12.0, LocalDate.of(2025, 9, 1))
        );

        List<Inscription> resultat = service.top3ParNote(inscriptions);

        assertEquals(3, resultat.size());
        assertEquals(18.0, resultat.get(0).note());  // meilleure note en premier
        assertEquals(15.0, resultat.get(1).note());  // 2ème meilleure
        assertEquals(12.0, resultat.get(2).note());  // 3ème meilleure
    }

}
