-- V6__donnees_demo_complementaires.sql
-- Donnees de demo complementaires pour G6/G7
-- ATTENTION : NE PAS UTILISER EN PRODUCTION

INSERT INTO etudiant (nom, prenom, email, age, filiere) VALUES
    ('Diallo',   'Cheikh',   'cheikh.diallo@campushub.sn',   22, 'Informatique'),
    ('Gueye',    'Aminata',  'aminata.gueye@campushub.sn',   20, 'Reseaux'),
    ('Sarr',     'Mamadou',  'mamadou.sarr@campushub.sn',    25, 'Gestion'),
    ('Cisse',    'Khadija',  'khadija.cisse@campushub.sn',   21, 'Informatique'),
    ('Faye',     'Ousmane',  'ousmane.faye@campushub.sn',    23, 'Reseaux'),
    ('Toure',    'Bineta',   'bineta.toure@campushub.sn',    22, 'Informatique'),
    ('Mbaye',    'Alioune',  'alioune.mbaye@campushub.sn',   24, 'Gestion'),
    ('Sy',       'Rokhaya',  'rokhaya.sy@campushub.sn',      20, 'Reseaux'),
    ('Kane',     'Modou',    'modou.kane@campushub.sn',      23, 'Informatique'),
    ('Niang',    'Astou',    'astou.niang@campushub.sn',     21, 'Gestion');


INSERT INTO cours (code, nom) VALUES
    ('INFO103', 'Algorithmique avancee'),
    ('RES102',  'Securite des reseaux');


INSERT INTO inscription (etudiant_id, cours_id, note, date_inscription) VALUES
    (6,  1, 11.0, '2026-01-12'),
    (6,  5, 16.5, '2026-01-13'),
    (7,  3, 14.0, '2026-01-13'),
    (7,  6, 15.0, '2026-01-13'),
    (8,  4, 10.5, '2026-01-13'),
    (9,  1, 17.5, '2026-01-14'),
    (9,  5, 13.5, '2026-01-14'),
    (10, 3, 12.0, '2026-01-14'),
    (10, 6, 18.5, '2026-01-14'),
    (11, 2, 14.5, '2026-01-15'),
    (11, 5, 16.0, '2026-01-15'),
    (12, 4, 9.5,  '2026-01-15'),
    (13, 3, 15.5, '2026-01-15'),
    (13, 6, 17.0, '2026-01-16'),
    (14, 1, 13.0, '2026-01-16'),
    (14, 2, 12.5, '2026-01-16'),
    (15, 4, 16.5, '2026-01-16'),
    (15, 5, 14.0, '2026-01-17');