\# Schéma relationnel — CampusHub



\## Vue d'ensemble



Le schéma repose sur 3 tables principales : `etudiant`, `cours`, `inscription` (relation many-to-many enrichie entre étudiant et cours).



!\[Schéma CampusHub](images/schema.png)



\## Détail des tables



\### etudiant

| Colonne  | Type          | Contraintes           |

|----------|---------------|------------------------|

| id       | BIGSERIAL     | PK, auto-increment    |

| nom      | VARCHAR(100)  | NOT NULL               |

| prenom   | VARCHAR(100)  | NOT NULL               |

| email    | VARCHAR(150)  | NOT NULL, UNIQUE (V2)  |

| age      | INT           | NOT NULL, CHECK > 0     |

| filiere  | VARCHAR(100)  | NOT NULL               |



\### cours

| Colonne | Type          | Contraintes  |

|---------|---------------|--------------|

| id      | BIGSERIAL     | PK           |

| code    | VARCHAR(20)   | NOT NULL, UNIQUE |

| nom     | VARCHAR(150)  | NOT NULL     |



\### inscription

Table de jonction enrichie représentant la relation many-to-many entre `etudiant` et `cours`.



| Colonne          | Type    | Contraintes                       |

|------------------|---------|-------------------------------------|

| id               | BIGSERIAL | PK                                 |

| etudiant\_id      | BIGINT  | FK -> etudiant.id, NOT NULL        |

| cours\_id         | BIGINT  | FK -> cours.id, NOT NULL           |

| note             | DOUBLE  |                                     |

| date\_inscription | DATE    | NOT NULL (V2)                      |



Contrainte : `UNIQUE(etudiant\_id, cours\_id)` — un étudiant ne peut s'inscrire qu'une seule fois au même cours.



\## Historique des migrations



\- \*\*V1\*\* — schéma initial (etudiant, cours, inscription)

\- \*\*V2\*\* — ajout email (etudiant) et date\_inscription (inscription)

