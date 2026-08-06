Schéma relationnel: CampusHub

Vue d'ensemble
Le schéma repose sur 4 tables principales : etudiant, cours, inscription (relation many-to-many enrichie entre étudiant et cours) et utilisateur.

Détail des tables



- etudiant

| Colonne  | Type          | Contraintes           |
|----------|---------------|------------------------|
| id       | BIGSERIAL     | PK, auto-increment    |
| nom      | VARCHAR(100)  | NOT NULL               |
| prenom   | VARCHAR(100)  | NOT NULL               |
| email    | VARCHAR(150)  | NOT NULL, UNIQUE (V2)  |
| age      | INT           | NOT NULL, CHECK > 0     |
| filiere  | VARCHAR(100)  | NOT NULL               |



- cours

| Colonne | Type          | Contraintes  |
|---------|---------------|--------------|
| id      | BIGSERIAL     | PK           |
| code    | VARCHAR(20)   | NOT NULL, UNIQUE |
| nom     | VARCHAR(150)  | NOT NULL     |

- inscription

Table de jonction enrichie représentant la relation many-to-many entre `etudiant` et `cours`.

| Colonne          | Type    | Contraintes                       |
|------------------|---------|-------------------------------------|
| id               | BIGSERIAL | PK                                 |
| etudiant_id      | BIGINT  | FK -> etudiant.id, NOT NULL        |
| cours_id         | BIGINT  | FK -> cours.id, NOT NULL           |
| note             | DOUBLE  |                                     |
| date_inscription | DATE    | NOT NULL (V2)                      |



Contrainte : UNIQUE(etudiant_id, cours_id)  un étudiant ne peut s'inscrire qu'une seule fois au même cours.

- utilisateur (V3 + V4 - jour 5)
| Colonne           | Type         | Contraintes            |
|-------------------|--------------|------------------------|
| id                | BIGSERIAL    | PK                     |
| email             | VARCHAR(150) | NOT NULL, UNIQUE       |
| mot_de_passe_hash | VARCHAR(255) | NOT NULL — hash BCrypt |
| role | VARCHAR(20)| NOT NULL     |
| nom | VARCHAR(150)| NOT NULL, ajouté en V4 (requis par l'entité de G5) |



 Historique des migrations



- *V1* : schéma initial (etudiant, cours, inscription)
- *V2* : ajout email (etudiant) et date_inscription (inscription)
- *V3* : ajout de la table utilisateur
- *V4* : Ajout du champ `nom` sur `utilisateur` 

