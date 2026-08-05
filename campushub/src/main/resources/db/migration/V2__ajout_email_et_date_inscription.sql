ALTER TABLE etudiant
    ADD COLUMN IF NOT EXISTS email VARCHAR(150);

UPDATE etudiant
SET email = 'temp' || id || '@campushub.sn'
WHERE email IS NULL;

ALTER TABLE etudiant
    ALTER COLUMN email SET NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'uq_etudiant_email'
    ) THEN
ALTER TABLE etudiant
    ADD CONSTRAINT uq_etudiant_email UNIQUE (email);
END IF;
END $$;

ALTER TABLE inscription
    ADD COLUMN IF NOT EXISTS date_inscription DATE NOT NULL DEFAULT CURRENT_DATE;
