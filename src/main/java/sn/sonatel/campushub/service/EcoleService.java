package sn.sonatel.campushub.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sn.sonatel.campushub.entity.Inscription;

public class EcoleService {
    public double calculerMoyenneAge(List<Inscription> inscriptions) {
        return inscriptions.stream()
            .map(Inscription::etudiant)
            .distinct()
            .mapToInt(etudiant -> etudiant.age())
            .average()
            .orElse(0.0);
        }
        
    public Map<String, List<Inscription>> grouperParFiliere(List<Inscription> inscriptions) {
        return inscriptions.stream()
                .collect(Collectors.groupingBy(ins -> ins.etudiant().filiere().name()));
    }

    public List<Inscription> obtenirTop3ParNote(List<Inscription> inscriptions) {
        return inscriptions.stream()
                .sorted((ins1, ins2) -> Double.compare(ins2.note(), ins1.note()))
                .limit(3)
                .toList();
    }
}




