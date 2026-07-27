package com.ept.campushub.data;

import com.ept.campushub.model.Cours;
import com.ept.campushub.model.Etudiant;
import com.ept.campushub.model.Inscription;
import com.ept.campushub.model.Niveau;

import java.time.LocalDate;
import java.util.List;

public class DonneesTest {

    public static List<Etudiant> getEtudiants() {
        return List.of(
                new Etudiant(1L, "Awa Diop", 20, "GIT", Niveau.DIC1, LocalDate.of(2004, 3, 15)),
                new Etudiant(2L, "Moussa Ba", 22, "GE", Niveau.DIC2, LocalDate.of(2002, 7, 10)),
                new Etudiant(3L, "Fatou Sarr", 21, "GIT", Niveau.DIC1, LocalDate.of(2003, 11, 2)),
                new Etudiant(4L, "Ibrahima Ndiaye", 23, "GC", Niveau.DIC3, LocalDate.of(2001, 5, 20)),
                new Etudiant(5L, "Khadija Dieng", 20, "GIT", Niveau.DIC1, LocalDate.of(2004, 1, 8))
        );
    }

    public static List<Cours> getCours() {
        return List.of(
                new Cours(1L, "Algorithmique", "GIT", "M. Wade"),
                new Cours(2L, "Réseaux", "GE", "M. Ciss"),
                new Cours(3L, "Génie Civil Structures", "GC", "M. Guissé")
        );
    }

    public static List<Inscription> getInscriptions() {
        return List.of(
                new Inscription(1L, 1L, 15.5, LocalDate.of(2025, 9, 1)),
                new Inscription(2L, 2L, 12.0, LocalDate.of(2025, 9, 2)),
                new Inscription(3L, 1L, 17.0, LocalDate.of(2025, 9, 1)),
                new Inscription(4L, 3L, 9.5, LocalDate.of(2025, 9, 3)),
                new Inscription(5L, 1L, 14.0, LocalDate.of(2025, 9, 1))
        );
    }
}
