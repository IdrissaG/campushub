package gestion.campushub.service;

import gestion.campushub.model.Cours;
import gestion.campushub.model.Etudiant;
import gestion.campushub.model.Inscription;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EtudiantsServiceTest {

    private final EtudiantsService service = new EtudiantsService();

    @Test
    void doitCalculerLaMoyenneAge() {
        List<Etudiant> etudiants = List.of(
                new Etudiant("Awa", 20, "Informatique"),
                new Etudiant("Moussa", 22, "Informatique"),
                new Etudiant("Fatou", 24, "Reseaux")
        );
        double moyenne = service.calculerMoyenneAge(etudiants);
        assertEquals(22.0, moyenne);
    }

    @Test
    void doitRegrouperLesEtudiantsParFiliere() {
        Etudiant awa = new Etudiant("Awa", 20, "Informatique");
        Etudiant moussa = new Etudiant("Moussa", 22, "Informatique");
        Etudiant fatou = new Etudiant("Fatou", 24, "Reseaux");

        Map<String, List<Etudiant>> resultat = service.regrouperParFiliere(List.of(awa, moussa, fatou));

        assertEquals(List.of(awa, moussa), resultat.get("Informatique"));
        assertEquals(List.of(fatou), resultat.get("Reseaux"));
    }

    @Test
    void doitRetournerLesTroisMeilleuresNotes() {
        Cours java = new Cours("JAVA", "Java");
        Cours spring = new Cours("SPRING", "Spring Boot");

        List<Inscription> inscriptions = List.of(
                new Inscription(new Etudiant("Awa", 20, "Informatique"), java, 12.0),
                new Inscription(new Etudiant("Moussa", 22, "Informatique"), spring, 18.0),
                new Inscription(new Etudiant("Fatou", 24, "Reseaux"), java, 15.0),
                new Inscription(new Etudiant("Ibrahima", 21, "Reseaux"), spring, 17.0)
        );

        List<Inscription> resultat = service.top3ParNote(inscriptions);

        assertEquals(3, resultat.size());
        assertEquals(18.0, resultat.get(0).note());
        assertEquals(17.0, resultat.get(1).note());
        assertEquals(15.0, resultat.get(2).note());
    }
}