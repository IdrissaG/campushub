ALTER TABLE etudiant
    ADD COLUMN email VARCHAR(150);

UPDATE etudiant
    SET email = 'temp' || id || '@campushub.sn'
    WHERE email IS NULL;

ALTER TABLE etudiant
    ALTER COLUMN email SET NOT NULL;

ALTER TABLE etudiant
    ADD CONSTRAINT uq_etudiant_email UNIQUE (email);

ALTER TABLE inscription
    ADD COLUMN date_inscription DATE NOT NULL DEFAULT CURRENT_DATE;
