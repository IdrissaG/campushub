-- Insertion du compte ADMIN
-- Mot de passe : Admin@2026!
INSERT INTO utilisateur (email, mot_de_passe_hash, role, nom)
VALUES (
    'admin@campushub.sn',
    '$2a$10$HwmEb4qmw9Ys0Vh6B9NL2.KUt/YB9vybmofd0lDjPCKrIUXsP71cy',
    'ADMIN',
    'Administrateur System'
)
ON CONFLICT (email) DO NOTHING;

-- Insertion du compte ETUDIANT
-- Mot de passe : Etudiant@2026!
INSERT INTO utilisateur (email, mot_de_passe_hash, role, nom)
VALUES (
    'etudiant@campushub.sn',
    '$2a$10$VCaKF1ta7EaE.zIk7uqWeuCmPMcdSgir9BYTDZrhebcN3dEecUdN6',
    'ETUDIANT',
    'Etudiant Test'
)
ON CONFLICT (email) DO NOTHING;