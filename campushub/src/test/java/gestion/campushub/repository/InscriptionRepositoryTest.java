package gestion.campushub.repository;

import gestion.campushub.model.Cours;
import gestion.campushub.model.Etudiant;
import gestion.campushub.model.Inscription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InscriptionRepositoryTest {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByEtudiantIdRetourneLesInscriptionsDeLEtudiant() {
        DonneesTest donnees = creerDonneesTest();

        List<Inscription> inscriptions = inscriptionRepository.findByEtudiantId(donnees.etudiantAwa().getId());

        assertThat(inscriptions)
                .hasSize(2)
                .extracting(inscription -> inscription.getEtudiant().getId())
                .containsOnly(donnees.etudiantAwa().getId());
    }

    @Test
    void findByCoursIdRetourneLesInscriptionsDuCours() {
        DonneesTest donnees = creerDonneesTest();

        List<Inscription> inscriptions = inscriptionRepository.findByCoursId(donnees.coursJava().getId());

        assertThat(inscriptions)
                .hasSize(2)
                .extracting(inscription -> inscription.getCours().getId())
                .containsOnly(donnees.coursJava().getId());
    }

    @Test
    void findAvecNoteMinimumRetourneLesInscriptionsTrieesParNoteDecroissante() {
        creerDonneesTest();

        List<Inscription> inscriptions = inscriptionRepository.findAvecNoteMinimum(12.0);

        assertThat(inscriptions)
                .hasSize(2)
                .extracting(Inscription::getNote)
                .containsExactly(17.0, 14.0);
    }

    private DonneesTest creerDonneesTest() {
        Etudiant awa = entityManager.persist(new Etudiant(
                "Diop",
                "Awa",
                "awa.diop@example.com",
                21,
                "Informatique"
        ));
        Etudiant moussa = entityManager.persist(new Etudiant(
                "Fall",
                "Moussa",
                "moussa.fall@example.com",
                23,
                "Gestion"
        ));

        Cours java = entityManager.persist(new Cours("JAVA101", "Introduction a Java"));
        Cours sql = entityManager.persist(new Cours("SQL101", "Bases SQL"));

        entityManager.persist(new Inscription(awa, java, 17.0, LocalDate.of(2026, 8, 4)));
        entityManager.persist(new Inscription(awa, sql, 11.0, LocalDate.of(2026, 8, 4)));
        entityManager.persist(new Inscription(moussa, java, 14.0, LocalDate.of(2026, 8, 4)));
        entityManager.flush();

        return new DonneesTest(awa, java);
    }

    private record DonneesTest(Etudiant etudiantAwa, Cours coursJava) {
    }
}
