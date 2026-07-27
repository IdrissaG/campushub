package com.campushub.model;

// Petit record utilitaire pour porter un etudiant + sa moyenne de notes (utilise par le classement top 3)
public record ClassementEtudiant(Etudiant etudiant, double moyenneNote) {
}
