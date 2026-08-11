-- TEMPORAIRE — comptes de test pour G7 (tests frontend)
-- À retirer une fois que G5 confirme la vraie source des comptes
-- Mot de passe en clair pour les deux comptes : Test1234!

INSERT INTO utilisateur (email, mot_de_passe_hash, role, nom) VALUES
('etudiant.test@campushub.sn', '$2a$10$Ehcufp7g7QGGJBaLYZqj2OLKsLWCUPKmHYfifm15hGy7dXleG4ANm', 'ETUDIANT', 'Etudiant Test'),
('admin.test@campushub.sn',    '$2a$10$Ehcufp7g7QGGJBaLYZqj2OLKsLWCUPKmHYfifm15hGy7dXleG4ANm', 'ADMIN',    'Admin Test');