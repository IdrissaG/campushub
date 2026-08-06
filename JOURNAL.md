### Journal — Jour 3
- *Appris*

1. *Flyway applique les migrations SQL au démarrage de Spring Boot automatiquement*
2. *Sans spring-boot-starter-jdbc, Flyway ne peut pas se connecter à la base*
3. *docker exec permet de vérifier les tables directement dans le conteneur*

- *Bloqué*
Le pull origin main écrasait des fichiers non commités dans target/

- *Question*
Quelle stratégie adopter si deux groupes modifient le même fichier SQL ?

### Jour 3 - G8 (Dieynaba)
- *Appris* : Configuration d'un vrai pipeline CI GitHub Actions avec Maven (`setup-java`) et création d'une documentation pour préparer l'environnement Cloud de production. J'ai aussi appris à synchroniser proprement mon fork (`git fetch upstream`).
- *Bloqué* : Mon pipeline a échoué car Maven ne trouvait pas le `pom.xml`. J'ai compris que le robot s'ouvrait à la racine du dépôt, j'ai débloqué ça en ajoutant `cd campushub` dans le script `build.yml`.
- *Question* : Pour le G4, confirmez-vous bien que nous utiliserons la base PostgreSQL managée intégrée à Render pour la production ?

### Jour 3 - G6 (Fatou Cissé Ndong, Ibrahima Sow)
- *Pour ce qu'on a appris* : On a rècupérer des données á partir d'une API, faire de l'injection de dépendances, s'abonner à une API
- *Problèmes rencontrés*: Il nous fallait attendre G2 pour qu'il termine leur tâche ce qui nous a retardé. Il y'a aussi le fait que les codes fournis ne suivent pas la structure des nouvelles versions d'angular avec lesquelles nous travaillons ce qui a fait qu'on a du les modifer.
- *Questions*: Comment faire pour la prochaine fois, garder les codes fournis ou l'adapter avec notre version ?

### Jour 5 (G4 - Khadija Dieng, Mohamed Aminou Niang)
- *Ce qu'on a fait*
- Créé et testé `V3__utilisateur.sql` (table utilisateur pour JWT)
- Créé `V4__utilisateur_ajustements.sql` suite à un décalage détecté avec l'entité `Utilisateur.java` de G5 (champ `nom` manquant)
- Vérifié que V1→V4 s'appliquent proprement sur une base fraîche
- Mis à jour `/schema.md` avec le diagramme incluant `utilisateur`

### Note pour G5 : Vérification entité Utilisateur
En comparant votre entité `Utilisateur.java` avec la table créée en V3, on a remarqué deux points :
1. **Champ `nom` manquant en base**
   Votre entité déclare `private String nom;`, mais la migration V3
   ne contenait que `email`, `mot_de_passe_hash`, `role`, pas de colonne `nom`.
   Donc on a créé `V4__utilisateur_ajustements.sql` qui ajoute uniquement `nom`.

2. **Mapping de colonne à corriger**
   Votre champ `motDePasse` va par défaut chercher une colonne `mot_de_passe`, mais la table a `mot_de_passe_hash`.
   Il faut ajouter :
```java
   @Column(name = "mot_de_passe_hash", nullable = false)
   private String motDePasse;
```

