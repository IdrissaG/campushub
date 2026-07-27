package sn.ept.campushub.service;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sn.ept.campushub.domain.Etudiant;
import sn.ept.campushub.domain.Filiere;

class StatistiqueServiceTest {

    private final StatistiqueService service = new StatistiqueService();

    private static final Etudiant MOR = new Etudiant(1L, "Mor", "DIOP", 22, Filiere.GIT);
    private static final Etudiant ACHRAF = new Etudiant(2L, "Achraf", "NIANG", 24, Filiere.GIT);
    private static final Etudiant AWA = new Etudiant(3L, "Awa", "FALL", 20, Filiere.GC);
    private static final Etudiant IBOU = new Etudiant(4L, "Ibou", "SARR", 26, Filiere.GEM);

    private static final List<Etudiant> ETUDIANTS = List.of(MOR, ACHRAF, AWA, IBOU);

    @Test
    @DisplayName("moyenneAge calcule la moyenne et reste vide sur une liste sans etudiant")
    void moyenneAge_calcule_la_moyenne_des_ages() {
        OptionalDouble moyenne = service.moyenneAge(ETUDIANTS);   // (22+24+20+26)/4 = 23

        assertThat(moyenne).hasValue(23.0);
        assertThat(service.moyenneAge(List.of())).isEmpty();
    }

    @Test
    @DisplayName("grouperParFiliere range chaque etudiant dans sa filiere")
    void grouperParFiliere_regroupe_les_etudiants() {
        Map<Filiere, List<Etudiant>> parFiliere = service.grouperParFiliere(ETUDIANTS);

        assertThat(parFiliere).containsOnlyKeys(Filiere.GIT, Filiere.GC, Filiere.GEM);
        assertThat(parFiliere.get(Filiere.GIT)).containsExactlyInAnyOrder(MOR, ACHRAF);
        assertThat(parFiliere.get(Filiere.GC)).containsExactly(AWA);
    }
}