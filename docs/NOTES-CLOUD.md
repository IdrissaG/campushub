# Notes Cloud & Déploiement Production - CampusHub (G8)

## 1. Choix de la Plateforme Cloud
* **Plateforme retenue** : **Render** (https://render.com)
* **Justification** : 
  Render offre un plan gratuit idéal pour un prototype d'équipe, avec déploiement automatique sur `git push`, support Web Service pour Spring Boot et provisionnement natif d'une base de données PostgreSQL managée.

---

## 2. Base de Données Managée (Coordination G4)
* **Type de base** : PostgreSQL managée hébergée directement sur Render.
* **Gestion des schémas** : Les migrations de tables sont exécutées automatiquement par **Flyway** au démarrage du backend Spring Boot.

---

## 3. Variables d'Environnement et Secrets de Production

> **Sécurité** : Aucune valeur réelle n'est stockée dans le code source. Les variables suivantes seront renseignées dans le panneau de configuration Render.

| Variable | Description |
| :--- | :--- |
| `SPRING_PROFILES_ACTIVE` | Défini à `prod` pour charger le profil de production |
| `DB_URL` | URL JDBC de la base Postgres managée (`jdbc:postgresql://...`) |
| `DB_USERNAME` | Identifiant de connexion généré par Render |
| `DB_PASSWORD` | Mot de passe de la BDD managée |
| `JWT_SECRET` | Clé secrète de signature des tokens (256-bit générée aléatoirement) |

---

## 4. Aperçu du Déploiement Futur (Étape 6)
* **Connexion GitHub** : Connexion du dépôt `campushub` à Render via App GitHub.
* **Build Command** : `mvn -B clean package -DskipTests -f campushub/pom.xml`
* **Start Command** : `java -jar campushub/target/*.jar`