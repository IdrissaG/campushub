package gestion.campushub.service;

import gestion.campushub.DTO.CoursRequest;
import gestion.campushub.DTO.CoursResponse;
import gestion.campushub.model.Cours;
import gestion.campushub.repository.CoursRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class CoursServiceTest {

    @Mock
    private CoursRepository coursRepository;

    @InjectMocks
    private CoursService coursService;

    @Test
    void getAllCours_retourneLesCoursDuRepository() {
        when(coursRepository.findAll()).thenReturn(List.of(
                new Cours("JAVA101", "Introduction a Java"),
                new Cours("SQL101", "Bases SQL")
        ));

        List<CoursResponse> resultat = coursService.getAllCours();

        assertEquals(2, resultat.size());
        assertEquals("JAVA101", resultat.get(0).code());
        assertEquals("SQL101", resultat.get(1).code());
        verify(coursRepository).findAll();
    }

    @Test
    void getCoursByCode_quandCoursExiste_retourneLeCours() {
        when(coursRepository.findByCode("JAVA101"))
                .thenReturn(Optional.of(new Cours("JAVA101", "Introduction a Java")));

        Optional<CoursResponse> resultat = coursService.getCoursByCode("JAVA101");

        assertTrue(resultat.isPresent());
        assertEquals("JAVA101", resultat.get().code());
        assertEquals("Introduction a Java", resultat.get().nom());
    }

    @Test
    void getCoursByCode_quandCoursAbsent_retourneEmpty() {
        when(coursRepository.findByCode("UNKNOWN")).thenReturn(Optional.empty());

        Optional<CoursResponse> resultat = coursService.getCoursByCode("UNKNOWN");

        assertTrue(resultat.isEmpty());
    }

    @Test
    void createCours_sauvegardeEtRetourneLeCours() {
        CoursRequest request = new CoursRequest("JAVA101", "Introduction a Java");
        when(coursRepository.save(any(Cours.class)))
                .thenReturn(new Cours("JAVA101", "Introduction a Java"));

        CoursResponse resultat = coursService.createCours(request);

        assertEquals("JAVA101", resultat.code());
        assertEquals("Introduction a Java", resultat.nom());
        verify(coursRepository).save(any(Cours.class));
    }

    @Test
    void updateCours_quandCodeExiste_modifieEtRetourneLeCours() {
        CoursRequest request = new CoursRequest("NOUVEAU", "Java avance");
        when(coursRepository.existsByCode("JAVA101")).thenReturn(true);
        when(coursRepository.save(any(Cours.class)))
                .thenReturn(new Cours("JAVA101", "Java avance"));

        Optional<CoursResponse> resultat = coursService.updateCours("JAVA101", request);

        assertTrue(resultat.isPresent());
        assertEquals("JAVA101", resultat.get().code());
        assertEquals("Java avance", resultat.get().nom());
        verify(coursRepository).save(any(Cours.class));
    }

    @Test
    void updateCours_quandCodeAbsent_retourneEmptySansSauvegarder() {
        CoursRequest request = new CoursRequest("UNKNOWN", "Cours inconnu");
        when(coursRepository.existsByCode("UNKNOWN")).thenReturn(false);

        Optional<CoursResponse> resultat = coursService.updateCours("UNKNOWN", request);

        assertTrue(resultat.isEmpty());
        verify(coursRepository, never()).save(any(Cours.class));
    }

    @Test
    void deleteCours_quandCoursExiste_supprimeEtRetourneTrue() {
        Cours cours = new Cours("JAVA101", "Introduction a Java");
        when(coursRepository.findByCode("JAVA101")).thenReturn(Optional.of(cours));

        boolean resultat = coursService.deleteCours("JAVA101");

        assertTrue(resultat);
        verify(coursRepository).delete(cours);
    }

    @Test
    void deleteCours_quandCoursAbsent_retourneFalseSansSupprimer() {
        when(coursRepository.findByCode("UNKNOWN")).thenReturn(Optional.empty());

        boolean resultat = coursService.deleteCours("UNKNOWN");

        assertFalse(resultat);
        verify(coursRepository, never()).delete(any(Cours.class));
    }
}
