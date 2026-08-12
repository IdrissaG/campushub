-- Insertion du compte ADMIN
-- Mot de passe : Admin@2026!
INSERT INTO utilisateur (email, mot_de_passe_hash, role, nom)
VALUES (
    'admin@campushub.sn',
    '$2a$10$wT/p7HqM8882iV.O08XUuubS9C6h56A4qF9e1z5bXyG2W8hH8lOda',
    'ADMIN',
    'Administrateur System'
)
ON CONFLICT (email) DO NOTHING;

-- Insertion du compte ETUDIANT
-- Mot de passe : Etudiant@2026!
INSERT INTO utilisateur (email, mot_de_passe_hash, role, nom)
VALUES (
    'etudiant@campushub.sn',
    '$2a$10$E291eI4q3.N.m0m4R7W/A.f8Yp9L0p0jO.L.x1z2A3B4C5D6E7F8G',
    'ETUDIANT',
    'Etudiant Test'
)
ON CONFLICT (email) DO NOTHING;