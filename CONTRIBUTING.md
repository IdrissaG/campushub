# Guide de contribution

Merci de vouloir contribuer à ce projet ! Ce dépôt est public, mais l'accès en écriture est réservé au mainteneur. Toutes les contributions externes passent par le workflow **fork + pull request** décrit ci-dessous.

## Étapes pour contribuer

### 1. Forker le dépôt
Clique sur le bouton **Fork** en haut à droite de la page du dépôt. Cela crée une copie du projet sur ton propre compte GitHub.

### 2. Cloner ton fork en local
```bash
git clone https://github.com/TON_USERNAME/nom-du-repo.git
cd nom-du-repo
```

### 3. Ajouter le dépôt original comme "upstream"
Cela te permettra de rester à jour avec les derniers changements du projet.
```bash
git remote add upstream https://github.com/USERNAME_ORIGINAL/nom-du-repo.git
```

### 4. Créer une branche pour ta modification
Ne travaille jamais directement sur `main`. Crée une branche dédiée à ta fonctionnalité ou correction :
```bash
git checkout -b nom-de-ta-feature
```

Exemples de noms de branche :
- `fix/correction-bug-login`
- `feature/ajout-dark-mode`
- `docs/mise-a-jour-readme`

### 5. Faire tes modifications
Modifie le code, teste-le localement, puis commit tes changements avec un message clair :
```bash
git add .
git commit -m "Description claire du changement"
```

### 6. Pousser ta branche sur ton fork
```bash
git push origin nom-de-ta-feature
```

### 7. Ouvrir une Pull Request
Va sur GitHub, sur la page de **ton fork**. Un bouton "Compare & pull request" devrait apparaître automatiquement. Sinon :
- Base repository : dépôt original / `main`
- Head repository : ton fork / `nom-de-ta-feature`

Décris clairement :
- Ce que ton changement fait
- Pourquoi il est utile
- Comment le tester (si applicable)

### 8. Attendre la review
Le mainteneur va relire ta PR, potentiellement demander des ajustements, puis merger si tout est bon.

## Rester à jour avec le projet

Avant de commencer une nouvelle contribution, synchronise ton fork avec le dépôt original :
```bash
git fetch upstream
git checkout main
git merge upstream/main
git push origin main
```

## Bonnes pratiques

- Une branche = une fonctionnalité ou une correction (évite de tout mélanger dans une seule PR)
- Écris des messages de commit clairs et descriptifs
- Teste ton code avant de soumettre la PR
- Sois patient·e et respectueux·se lors des échanges de review

## Signaler un bug ou proposer une idée

Si tu ne souhaites pas coder toi-même, tu peux aussi ouvrir une **Issue** pour signaler un bug ou proposer une amélioration.

---

Merci pour ta contribution ! 🙌
