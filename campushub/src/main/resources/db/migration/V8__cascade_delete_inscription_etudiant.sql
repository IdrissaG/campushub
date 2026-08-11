ALTER TABLE inscription DROP CONSTRAINT inscription_etudiant_id_fkey;
ALTER TABLE inscription
    ADD CONSTRAINT inscription_etudiant_id_fkey
    FOREIGN KEY (etudiant_id) REFERENCES etudiant(id) ON DELETE CASCADE;
