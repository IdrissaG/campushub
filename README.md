# campushub

Exercie Jour 1:
1- Dans un projet SpringBoot simple, modéliser Etudiant, Cours, Inscription (records), puis avec les Streams : moyenne d'âge, groupement par filière, top 3 par note. Ajouter 3 tests JUnit 5. Livrable : PR mergée avec code + tests verts. Validation : historique Git propre.

# 🌿 Convention de nommage des branches
Nous utilisons le modèle standard basé sur les branches **main/master**, **develop** et les branches secondaires :

```
main/master        → code en production
develop            → code validé & en attente de recette
feature/...        → nouvelles fonctionnalités
bugfix/...         → corrections non urgentes
hotfix/...         → correctifs urgents pour production
release/vX.Y.Z     → stabilisation avant déploiement
```

Utilisez le format suivant :
```
<type>/<initiales>/<slug>
```
Exemples :
- `feature/adama/auth-ldap`
- `bugfix/youssouf/dashboard`

# 📝 Convention de commit 
[Documentation](https://www.conventionalcommits.org/en/v1.0.0/#specification)

Format :
```text
<type>(<scope>): <message court>

Description (optionnelle)

Closes: #<ticket> (optionnel)
```
**Types autorisés :**

- `feat` : nouvelle fonctionnalité
- `fix` : correction de bug
- `docs` : documentation
- `style` : formatage / lint
- `refactor` : refactoring sans changement de comportement
- `test` : ajout / modification de tests
- `chore` : maintenance (scripts, dépendances...)

**Exemples :**
```bash
git commit -m "feat(auth): support LDAP

Closes: #245"
```

## 3. Pull Request

### 🎯 Avant de créer la PR :
- La branche est **poussée** : `git push origin feature/...`
- Le pipeline CI/CD est **vert** (tests, build, qualité)
- Les **tests unitaires** et **tests de non-régression** sont validés

### 🛠 Création de la PR :
- Cible : **source** = notre branche, **destination** = `develop`
- Titre clair : `feat(auth): support LDAP via AD`
- Description structurée :
    - 📋 Objet de la PR
    - ✅ Checklist :
        - [x] Code testé localement
        - [x] Tests unitaires ajoutés
        - [x] Documentation à jour
    - 🧪 Scénarios testés
