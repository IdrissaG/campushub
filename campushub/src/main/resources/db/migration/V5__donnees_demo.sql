-- V5__donnees_demo.sql
-- Donnees de demonstration pour G6/G7
-- Ne pas utiliser en production

INSERT INTO etudiant (nom, prenom, email, age, filiere) VALUES
('Diop',   'Awa',      'awa.diop@campushub.sn',       22, 'Informatique'),
('Fall',   'Moussa',   'moussa.fall@campushub.sn',     24, 'Gestion'),
('Ba',     'Fatou',    'fatou.ba@campushub.sn',        20, 'Informatique'),
('Ndiaye', 'Ibrahima', 'ibrahima.ndiaye@campushub.sn', 23, 'Reseaux'),
('Sow',    'Mariama',  'mariama.sow@campushub.sn',     21, 'Gestion');

INSERT INTO cours (code, nom) VALUES
('INFO101', 'Programmation Java'),
('INFO102', 'Base de donnees'),
('RES101',  'Reseaux et protocoles'),
('GEST101', 'Comptabilite generale');

INSERT INTO inscription (etudiant_id, cours_id, note, date_inscription) VALUES
(1, 1, 15.5, '2026-01-10'),
(1, 2, 13.0, '2026-01-10'),
(2, 4, 17.0, '2026-01-11'),
(3, 1, 14.5, '2026-01-11'),
(3, 2, 16.0, '2026-01-11'),
(4, 3, 18.0, '2026-01-12'),
(5, 4, 12.5, '2026-01-12');