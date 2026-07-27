package sn.ept.campushub.domain;

import java.util.Objects;

public record Cours(
        Long id,
        String code,
        String intitule,
        int credits
) {

    public Cours {
        Objects.requireNonNull(id, "id est obligatoire");
        Objects.requireNonNull(code, "code est obligatoire");
        if (credits <= 0) {
            throw new IllegalArgumentException("credits doit etre strictement positif : " + credits);
        }
    }
}