package sn.sonatel.campushub.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import sn.sonatel.campushub.entity.Cours;
import sn.sonatel.campushub.entity.Etudiant;
import sn.sonatel.campushub.entity.Inscription;
import sn.sonatel.campushub.enums.TypeFiliere;
import sn.sonatel.campushub.enums.TypeNiveau;
import sn.sonatel.campushub.service.EcoleService;

class EcoleServiceTest {

    private final EcoleService ecoleService = new EcoleService();

    @Test
    void calculerMoyenneAge() {
        List<Inscription> inscriptions = List.of(
            new Inscription(1L, 16.5, 3, new Cours(1L, "Programmation", 5), new Etudiant(1L, "Diop", "Awa", TypeFiliere.GI, TypeNiveau.DIC1, 22, 2024)),
            new Inscription(2L, 14.0, 2, new Cours(2L, "Base de donnees", 5), new Etudiant(2L, "Ndiaye", "Mamadou", TypeFiliere.GI, TypeNiveau.DIC2, 23, 2023)),
            new Inscription(3L, 18.0, 4, new Cours(3L, "Maths", 5), new Etudiant(3L, "Thiam", "Fatou", TypeFiliere.GC, TypeNiveau.DIC1, 21, 2024)),
            new Inscription(4L, 15.0, 3, new Cours(4L, "Algo", 5), new Etudiant(1L, "Diop", "Awa", TypeFiliere.GI, TypeNiveau.DIC1, 22, 2024)));
        assertEquals(22, ecoleService.calculerMoyenneAge(inscriptions));
    }

    @Test
    void grouperParFiliere() {
        Map<String, List<Inscription>> groupes = ecoleService.grouperParFiliere(List.of(
            new Inscription(1L, 16.5, 3, new Cours(1L, "Programmation", 5), new Etudiant(1L, "Diop", "Awa", TypeFiliere.GI, TypeNiveau.DIC1, 20, 2024)),
            new Inscription(2L, 14.0, 2, new Cours(2L, "Base de donnees", 5), new Etudiant(2L, "Ndiaye", "Mamadou", TypeFiliere.GI, TypeNiveau.DIC2, 23, 2023)),
            new Inscription(3L, 18.0, 4, new Cours(3L, "Maths", 5), new Etudiant(3L, "Thiam", "Fatou", TypeFiliere.GC, TypeNiveau.DIC1, 21, 2024))));
        assertEquals(2, groupes.size());
        assertEquals(2, groupes.get("GI").size());
        assertEquals(1, groupes.get("GC").size());
    }

    @Test
    void obtenirTop3ParNote() {
        List<Inscription> top3 = ecoleService.obtenirTop3ParNote(List.of(
            new Inscription(1L, 13.5, 3, new Cours(1L, "Programmation", 5), new Etudiant(1L, "Diop", "Awa", TypeFiliere.GI, TypeNiveau.DIC1, 20, 2024)),
            new Inscription(2L, 18.75, 2, new Cours(2L, "Base de donnees", 5), new Etudiant(2L, "Ndiaye", "Mamadou", TypeFiliere.GI, TypeNiveau.DIC2, 23, 2023)),
            new Inscription(3L, 15.0, 4, new Cours(3L, "Maths", 5), new Etudiant(3L, "Thiam", "Fatou", TypeFiliere.GC, TypeNiveau.DIC1, 21, 2024)),
            new Inscription(4L, 19.25, 3, new Cours(4L, "Algo", 5), new Etudiant(4L, "Sarr", "Cheikh", TypeFiliere.GEM, TypeNiveau.DIC1, 22, 2022))));
        assertEquals(3, top3.size());
        assertEquals(19.25, top3.get(0).note(), 0.0001);
        assertEquals(18.75, top3.get(1).note(), 0.0001);
        assertEquals(15.0, top3.get(2).note(), 0.0001);
    }
}
