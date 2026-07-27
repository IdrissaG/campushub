package com.example.campushub.Records;

public record Cours(
        Long id,
        String code,
        String nom,
        int nombreCredits
) {
}
