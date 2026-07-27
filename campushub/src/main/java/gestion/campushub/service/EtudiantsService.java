package gestion.campushub.service;

import gestion.campushub.model.Etudiant;
import gestion.campushub.model.Inscription;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EtudiantsService {
    public double calculerMoyenneAge(List<Etudiant> etudiants) {
        return etudiants.stream()
                .mapToInt(Etudiant::age)
                .average()
                .orElse(0.0);
    }

    public Map<String, List<Etudiant>> regrouperParFiliere(List<Etudiant> etudiants) {
        return etudiants.stream()
                .collect(Collectors.groupingBy(Etudiant::filiere));
    }

    public List<Inscription> top3ParNote(List<Inscription> inscriptions) {
        return inscriptions.stream()
                .sorted(Comparator.comparingDouble(Inscription::note).reversed())
                .limit(3)
                .toList();
    }
}
