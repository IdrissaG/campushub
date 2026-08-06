CREATE TABLE utilisateur (
                             id BIGSERIAL PRIMARY KEY,
                             email VARCHAR(150) UNIQUE NOT NULL,
                             mot_de_passe_hash VARCHAR(255) NOT NULL,
                             role VARCHAR(20) NOT NULL
);
