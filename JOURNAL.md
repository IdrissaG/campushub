\# Journal — Jour 3



\## Appris

\- Flyway applique les migrations SQL au démarrage de Spring Boot automatiquement

\- Sans spring-boot-starter-jdbc, Flyway ne peut pas se connecter à la base

\- docker exec permet de vérifier les tables directement dans le conteneur



\## Bloqué

\- Le pull origin main écrasait des fichiers non commités dans target/



\## Question

\- Quelle stratégie adopter si deux groupes modifient le même fichier SQL ?

### Jour 3 - G8 (Dieynaba)
- *Appris* : Configuration d'un vrai pipeline CI GitHub Actions avec Maven (`setup-java`) et création d'une documentation pour préparer l'environnement Cloud de production. J'ai aussi appris à synchroniser proprement mon fork (`git fetch upstream`).
- *Bloqué* : Mon pipeline a échoué car Maven ne trouvait pas le `pom.xml`. J'ai compris que le robot s'ouvrait à la racine du dépôt, j'ai débloqué ça en ajoutant `cd campushub` dans le script `build.yml`.
- *Question* : Pour le G4, confirmez-vous bien que nous utiliserons la base PostgreSQL managée intégrée à Render pour la production ?
