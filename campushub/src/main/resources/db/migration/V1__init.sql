CREATE TABLE etudiant (
    id      BIGSERIAL    PRIMARY KEY,
    nom     VARCHAR(100) NOT NULL,
    prenom  VARCHAR(100) NOT NULL,
    filiere VARCHAR(100) NOT NULL,
    age     INT          NOT NULL CHECK (age > 0)
);

CREATE TABLE cours (
    id   BIGSERIAL   PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    nom  VARCHAR(150) NOT NULL
);

CREATE TABLE inscription (
    id          BIGSERIAL        PRIMARY KEY,
    etudiant_id BIGINT           NOT NULL REFERENCES etudiant(id),
    cours_id    BIGINT           NOT NULL REFERENCES cours(id),
    note        DOUBLE PRECISION,
    UNIQUE (etudiant_id, cours_id)
);