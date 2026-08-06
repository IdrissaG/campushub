-- DEFAULT temporaire pour ne pas casser d'éventuelles lignes déjà existantes ;
-- à retirer plus tard si la table est encore vide au moment de l'application.
ALTER TABLE utilisateur ADD COLUMN nom VARCHAR(150) NOT NULL DEFAULT 'À renseigner';