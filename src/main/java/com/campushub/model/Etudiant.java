package com.campushub.model;

// Record = classe immuable generee automatiquement (constructeur, getters, equals/hashCode/toString)
public record Etudiant(Long id, String prenom, String nom, int age, String filiere) {
}
