package gestion.campushub.service;

import gestion.campushub.DTO.InscriptionRequest;
import gestion.campushub.DTO.InscriptionResponse;
import gestion.campushub.model.Cours;
import gestion.campushub.model.Etudiant;
import gestion.campushub.model.Inscription;
import gestion.campushub.repository.CoursRepository;
import gestion.campushub.repository.InscriptionRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InscriptionServiceTest {

    @Mock
    private InscriptionRepository inscriptionRepository;

    @Mock
    private CoursRepository coursRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private InscriptionService inscriptionService;

    @Test
    void toutesLesInscriptions_retourneLesInscriptionsDuRepository() {
        when(inscriptionRepository.findAll()).thenReturn(List.of(
                new Inscription(
                        new Etudiant("Diop", "Awa", "awa@test.com", 21, "Info"),
                        new Cours("JAVA101", "Introduction a Java"),
                        16.0,
                        LocalDate.of(2026, 8, 11)
                )
        ));

        List<InscriptionResponse> resultat = inscriptionService.toutesLesInscriptions();

        assertEquals(1, resultat.size());
        assertEquals("Diop", resultat.get(0).etudiantNom());
        assertEquals("JAVA101", resultat.get(0).coursCode());
        verify(inscriptionRepository).findAll();
    }

    @Test
    void inscriptionsParEtudiant_retourneLesInscriptionsDeLEtudiant() {
        when(inscriptionRepository.findByEtudiantId(1L)).thenReturn(List.of(
                new Inscription(
                        new Etudiant("Diop", "Awa", "awa@test.com", 21, "Info"),
                        new Cours("JAVA101", "Introduction a Java"),
                        15.0,
                        LocalDate.of(2026, 8, 11)
                )
        ));

        List<InscriptionResponse> resultat = inscriptionService.inscriptionsParEtudiant(1L);

        assertEquals(1, resultat.size());
        assertEquals(15.0, resultat.get(0).note());
        verify(inscriptionRepository).findByEtudiantId(1L);
    }

    @Test
    void inscriptionsParCours_retourneLesInscriptionsDuCours() {
        when(inscriptionRepository.findByCoursId(2L)).thenReturn(List.of(
                new Inscription(
                        new Etudiant("Fall", "Moussa", "moussa@test.com", 23, "Gestion"),
                        new Cours("SQL101", "Bases SQL"),
                        14.0,
                        LocalDate.of(2026, 8, 11)
                )
        ));

        List<InscriptionResponse> resultat = inscriptionService.inscriptionsParCours(2L);

        assertEquals(1, resultat.size());
        assertEquals("SQL101", resultat.get(0).coursCode());
        verify(inscriptionRepository).findByCoursId(2L);
    }

    @Test
    void inscriptionsAvecNoteMinimum_retourneLesInscriptionsFiltrees() {
        when(inscriptionRepository.findAvecNoteMinimum(12.0)).thenReturn(List.of(
                new Inscription(
                        new Etudiant("Diop", "Awa", "awa@test.com", 21, "Info"),
                        new Cours("JAVA101", "Introduction a Java"),
                        17.0,
                        LocalDate.of(2026, 8, 11)
                )
        ));

        List<InscriptionResponse> resultat = inscriptionService.inscriptionsAvecNoteMinimum(12.0);

        assertEquals(1, resultat.size());
        assertEquals(17.0, resultat.get(0).note());
        verify(inscriptionRepository).findAvecNoteMinimum(12.0);
    }

    @Test
    void creerInscription_quandEtudiantEtCoursExistent_sauvegardeEtRetourneInscription() {
        Etudiant etudiant = new Etudiant("Diop", "Awa", "awa@test.com", 21, "Info");
        Cours cours = new Cours("JAVA101", "Introduction a Java");
        LocalDate date = LocalDate.of(2026, 8, 11);
        InscriptionRequest request = new InscriptionRequest(1L, 2L, 16.5, date);

        when(entityManager.find(Etudiant.class, 1L)).thenReturn(etudiant);
        when(coursRepository.findById(2L)).thenReturn(Optional.of(cours));
        when(inscriptionRepository.save(any(Inscription.class)))
                .thenReturn(new Inscription(etudiant, cours, 16.5, date));

        Optional<InscriptionResponse> resultat = inscriptionService.creerInscription(request);

        assertTrue(resultat.isPresent());
        assertEquals("Awa", resultat.get().etudiantPrenom());
        assertEquals("JAVA101", resultat.get().coursCode());
        assertEquals(16.5, resultat.get().note());
        assertEquals(date, resultat.get().dateInscription());
        verify(inscriptionRepository).save(any(Inscription.class));
    }

    @Test
    void creerInscription_quandDateAbsente_utiliseLaDateDuJour() {
        Etudiant etudiant = new Etudiant("Diop", "Awa", "awa@test.com", 21, "Info");
        Cours cours = new Cours("JAVA101", "Introduction a Java");
        InscriptionRequest request = new InscriptionRequest(1L, 2L, 16.5, null);

        when(entityManager.find(Etudiant.class, 1L)).thenReturn(etudiant);
        when(coursRepository.findById(2L)).thenReturn(Optional.of(cours));
        when(inscriptionRepository.save(any(Inscription.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        inscriptionService.creerInscription(request);

        ArgumentCaptor<Inscription> captor = ArgumentCaptor.forClass(Inscription.class);
        verify(inscriptionRepository).save(captor.capture());
        assertEquals(LocalDate.now(), captor.getValue().getDateInscription());
    }

    @Test
    void creerInscription_quandEtudiantAbsent_retourneEmptySansSauvegarder() {
        InscriptionRequest request = new InscriptionRequest(99L, 2L, 12.0, LocalDate.of(2026, 8, 11));
        when(entityManager.find(Etudiant.class, 99L)).thenReturn(null);
        when(coursRepository.findById(2L)).thenReturn(Optional.of(new Cours("JAVA101", "Introduction a Java")));

        Optional<InscriptionResponse> resultat = inscriptionService.creerInscription(request);

        assertTrue(resultat.isEmpty());
        verify(inscriptionRepository, never()).save(any(Inscription.class));
    }

    @Test
    void creerInscription_quandCoursAbsent_retourneEmptySansSauvegarder() {
        InscriptionRequest request = new InscriptionRequest(1L, 99L, 12.0, LocalDate.of(2026, 8, 11));
        when(entityManager.find(Etudiant.class, 1L))
                .thenReturn(new Etudiant("Diop", "Awa", "awa@test.com", 21, "Info"));
        when(coursRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<InscriptionResponse> resultat = inscriptionService.creerInscription(request);

        assertFalse(resultat.isPresent());
        verify(inscriptionRepository, never()).save(any(Inscription.class));
    }
}
