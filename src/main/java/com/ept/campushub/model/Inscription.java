package com.ept.campushub.model;

import java.time.LocalDate;

public record Inscription(
        Long etudiantId,
        Long coursId,
        double note,
        LocalDate dateInscription) {
}
