package sn.ept.campushub.domain;

import java.util.Objects;

public record Inscription(
        Long etudiantId,
        Long coursId,
        Double note
) {

    public Inscription {
        Objects.requireNonNull(etudiantId, "etudiantId est obligatoire");
        Objects.requireNonNull(coursId, "coursId est obligatoire");
        if (note != null && (note < 0 || note > 20)) {
            throw new IllegalArgumentException("la note doit etre comprise entre 0 et 20 : " + note);
        }
    }

    public boolean estNotee() {
        return note != null;
    }
}