package com.campushub.campushub.service;

import com.campushub.campushub.model.Etudiant;
import com.campushub.campushub.model.Inscription;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsService {

    public double moyenneAge(List<Etudiant> etudiants){
        return etudiants.stream()
                .mapToInt(Etudiant::age)
                .average()
                .orElse(0);
    }

    public Map<String, List<Etudiant>> groupementParFiliere(List<Etudiant> etudiants) { // map pour grouper les etudiants par filiere, la clé est la filiere et la valeur est la liste des etudiants de cette filiere
        return etudiants.stream()
                .collect(Collectors.groupingBy(Etudiant::filiere));
    }

    public List<Inscription> top3ParNote(List<Inscription> inscriptions) { //list pour retourner les 3 meilleurs étudiants par note, on utilise un stream pour trier les inscriptions par note et on limite à 3 stream c'est un flux de données qui permet de traiter les données de manière fonctionnelle
        return inscriptions.stream()
                .sorted(Comparator.comparingDouble(Inscription::note).reversed()) //coumparingDouble pour trier par note double pour avoir un tri correct
                .limit(3)
                .toList();
    }
} 
