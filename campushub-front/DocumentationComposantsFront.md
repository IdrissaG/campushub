# Documentation des composants Frontend — CampusHub

## 1. Objectif

Les composants d'affichage du frontend ont pour objectif de présenter les données de manière réutilisable et de communiquer les actions de l'utilisateur au composant parent.

Les composants concernés sont :

* `EtudiantListComponent`
* `EtudiantCardComponent`

Ils doivent rester simples, réutilisables et avoir des responsabilités clairement définies.

`EtudiantListComponent` assure principalement la gestion de l'affichage de la liste, du chargement, des erreurs, de la pagination et des actions provenant des cartes.

`EtudiantCardComponent` est responsable de l'affichage d'un étudiant et de la transmission des actions de l'utilisateur au composant parent.

---

# 2. EtudiantCardComponent

## Rôle

`EtudiantCardComponent` est un composant réutilisable permettant d'afficher les informations d'un étudiant.

Il reçoit l'étudiant depuis son composant parent grâce à un `input` et peut notifier le parent lorsqu'une action est effectuée, notamment lorsqu'une suppression est demandée.

### Responsabilités

Le composant doit :

* afficher les informations de l'étudiant ;
* recevoir l'étudiant à afficher ;
* signaler les actions effectuées par l'utilisateur ;
* rester indépendant de la communication directe avec l'API.

Il ne doit pas :

* appeler directement `EtudiantService` pour supprimer un étudiant ;
* gérer la récupération de la liste des étudiants ;
* gérer la pagination ;
* gérer la logique globale du CRUD.

---

## 2.1. Input

Le composant utilise :

```ts
etudiant = input.required<Etudiant>();
```

Cette propriété permet au composant parent de transmettre l'étudiant à afficher.

`required` signifie que la valeur de l'étudiant est obligatoire pour utiliser correctement le composant.

L'utilisation de `input()` fait de cette propriété un Signal. Sa valeur est donc récupérée avec :

```ts
etudiant()
```

### Utilisation depuis EtudiantListComponent

Dans `EtudiantListComponent`, chaque étudiant est transmis à une carte :

```html
@for (etudiant of etudiants(); track etudiant.id) {
  <app-etudiant-card-component
    [etudiant]="etudiant"
    (supprimer)="onSupprimer($event)" />
}
```

Ainsi, chaque instance de `EtudiantCardComponent` reçoit un étudiant différent.

---

# 2.2. Output

Le composant expose :

```ts
supprimer = output<number>();
```

Cet `output` permet au composant enfant de signaler au composant parent qu'une suppression a été demandée.

Le type `number` indique que l'événement transmet l'identifiant de l'étudiant.

Dans la carte, l'action de suppression est déclenchée avec :

```html
<button (click)="supprimer.emit(etudiant().id)">
  Supprimer
</button>
```

Lors du clic, l'identifiant de l'étudiant est transmis au composant parent.

---

# 2.3. Communication enfant → parent

La communication entre les deux composants fonctionne de la manière suivante :

```text
EtudiantListComponent
        │
        │ [etudiant]
        ▼
EtudiantCardComponent
        │
        │ clic sur "Supprimer"
        ▼
supprimer.emit(id)
        │
        ▼
EtudiantListComponent
        │
        │ onSupprimer(id)
        ▼
EtudiantService
```

Dans `EtudiantListComponent`, l'événement est récupéré avec :

```html
(supprimer)="onSupprimer($event)"
```

`$event` contient l'identifiant de l'étudiant transmis par :

```ts
supprimer.emit(etudiant().id);
```

Le parent peut alors appeler le service :

```ts
onSupprimer(id: number) {
  this.etudiantService.delete(id).subscribe({
    next: () => {
      this.etudiants.set(
        this.etudiants().filter(etudiant => etudiant.id !== id)
      );
    },
    error: () => {
      this.erreur.set('Impossible de supprimer l\'étudiant');
    }
  });
}
```

La séparation des responsabilités est donc la suivante :

```text
EtudiantCardComponent
→ affiche l'étudiant
→ signale une demande de suppression

EtudiantListComponent
→ reçoit la demande
→ appelle le service
→ met à jour la liste
→ gère l'erreur

EtudiantService
→ communique avec l'API
```

---

# 3. EtudiantListComponent

## Rôle

`EtudiantListComponent` est le composant responsable de l'affichage de la liste des étudiants.

Il constitue le composant parent des différents `EtudiantCardComponent`.

Le composant utilise les Signals Angular pour gérer l'état de la liste, du chargement, des erreurs et de la page actuelle.

---

## 3.1. État des étudiants

La liste est stockée dans :

```ts
etudiants = signal<Etudiant[]>([]);
```

Le Signal contient un tableau d'étudiants.

Pour récupérer sa valeur dans le TypeScript :

```ts
this.etudiants()
```

Pour modifier sa valeur :

```ts
this.etudiants.set(nouvelleListe);
```

Dans le template, la liste est parcourue avec :

```html
@for (etudiant of etudiants(); track etudiant.id) {
  …
}
```

---

# 3.2. Gestion du chargement

Le composant possède un Signal dédié :

```ts
loading = signal(true);
```

Au démarrage, `loading` vaut `true`.

Pendant cette période, le template affiche des skeletons :

```html
@if (loading()) {
  ...
}
```

Une fois les étudiants récupérés :

```ts
next: (data) => {
  this.etudiants.set(data.content);
  this.loading.set(false);
}
```

Le chargement est terminé et les étudiants sont affichés.

---

# 3.3. Gestion des erreurs

Les erreurs sont stockées dans :

```ts
erreur = signal<string | null>(null);
```

Lorsqu'une erreur survient pendant le chargement :

```ts
error: (err) => {
  this.erreur.set('Impossible de charger les étudiants');
  this.loading.set(false);
}
```

Le template affiche alors le message :

```html
@else if (erreur()) {
  <div>
    {{ erreur() }}
  </div>
}
```

Lorsqu'une erreur survient pendant une suppression :

```ts
error: () => {
  this.erreur.set('Impossible de supprimer l\'étudiant');
}
```

Le même mécanisme permet d'afficher le message à l'utilisateur.

---

# 3.4. Gestion d'une liste vide

Après le chargement, le composant vérifie si aucun étudiant n'est disponible :

```html
@else if (etudiants().length === 0) {
  <div>
    <p>Aucun étudiant trouvé.</p>
  </div>
}
```

Cela permet d'éviter d'afficher une interface vide lorsque l'API ne retourne aucun étudiant.

---

# 3.5. Affichage des cartes

Lorsque les étudiants sont disponibles, ils sont affichés sous forme de cartes :

```html
@for (etudiant of etudiants(); track etudiant.id) {
  <app-etudiant-card-component
    [etudiant]="etudiant"
    (supprimer)="onSupprimer($event)" />
}
```

Chaque carte reçoit :

* un étudiant grâce à `[etudiant]` ;
* un événement de suppression grâce à `(supprimer)`.

---

# 4. Pagination

`EtudiantListComponent` gère également la pagination.

Les deux propriétés utilisées sont :

```ts
pageActuelle = signal(0);
taillePage = 4;
```

`pageActuelle` représente la page actuellement affichée.

`taillePage` indique que quatre étudiants sont demandés par page.

Lors du chargement, ces valeurs sont transmises au service :

```ts
this.etudiantService.getAll(
  this.pageActuelle(),
  this.taillePage
)
```

La réponse de l'API contient les étudiants dans :

```ts
data.content
```

qui est ensuite stocké dans :

```ts
this.etudiants.set(data.content);
```

---

## 4.1. Page précédente

La méthode :

```ts
pagePrecedente() {
  if (this.pageActuelle() > 0) {
    this.pageActuelle.update(p => p - 1);
    this.chargerEtudiants();
  }
}
```

permet de revenir à la page précédente.

La condition :

```ts
this.pageActuelle() > 0
```

empêche de passer avant la première page.

---

## 4.2. Page suivante

La méthode :

```ts
pageSuivante() {
  this.pageActuelle.update(p => {
    if (p < (this.etudiants().length / this.taillePage)) {
      return p + 1;
    }
    return p;
  });

  this.chargerEtudiants();
}
```

permet de demander la page suivante lorsque la condition définie dans le composant est respectée.

Le bouton correspondant est présent dans le template :

```html
<button (click)="pageSuivante()">
  Suivant
</button>
```

La page actuelle est affichée avec :

```html
Page {{ pageActuelle() + 1 }}
```

Le `+1` permet d'afficher la pagination à partir de la page 1 pour l'utilisateur, alors que l'index utilisé par le code commence à 0.

---

# 5. Chargement des étudiants

Le chargement initial est déclenché dans `ngOnInit()` :

```ts
ngOnInit() {
  this.chargerEtudiants();
}
```

La méthode `chargerEtudiants()` appelle le service :

```ts
this.etudiantService.getAll(
  this.pageActuelle(),
  this.taillePage
)
```

Le fonctionnement est donc :

```text
Initialisation du composant
        │
        ▼
chargerEtudiants()
        │
        ▼
EtudiantService
        │
        ▼
API Backend
        │
        ▼
data.content
        │
        ▼
etudiants.set(...)
        │
        ▼
Affichage des cartes
```

---

# 6. Suppression d'un étudiant

La suppression est déclenchée depuis `EtudiantCardComponent`.

Le composant enfant émet l'identifiant :

```ts
supprimer.emit(etudiant().id);
```

`EtudiantListComponent` reçoit cet identifiant :

```html
(supprimer)="onSupprimer($event)"
```

Puis appelle :

```ts
this.etudiantService.delete(id)
```

En cas de succès, l'étudiant supprimé est retiré de la liste locale :

```ts
this.etudiants.set(
  this.etudiants().filter(etudiant => etudiant.id !== id)
);
```

Cela permet de mettre immédiatement à jour l'affichage sans avoir besoin de recharger toute la page.

---

# 7. Séparation des responsabilités

L'architecture actuelle peut être représentée ainsi :

```text
┌──────────────────────────────────┐
│ EtudiantListComponent            │
│                                  │
│ - récupération des étudiants     │
│ - affichage de la liste          │
│ - chargement                     │
│ - erreurs                        │
│ - pagination                     │
│ - gestion de la suppression      │
└───────────────┬──────────────────┘
                │
                │ [etudiant]
                ▼
┌──────────────────────────────────┐
│ EtudiantCardComponent            │
│                                  │
│ - affichage d'un étudiant        │
│ - émission des actions           │
└───────────────┬──────────────────┘
                │
                │ supprimer.emit(id)
                ▼
┌──────────────────────────────────┐
│ EtudiantListComponent            │
│                                  │
│ onSupprimer(id)                  │
└───────────────┬──────────────────┘
                │
                ▼
┌──────────────────────────────────┐
│ EtudiantService                  │
│                                  │
│ communication avec l'API         │
└──────────────────────────────────┘
```

Cette séparation permet de conserver des composants plus faciles à comprendre, maintenir et réutiliser.

---

# 8. Réutilisation par G7

Les composants peuvent être réutilisés par G7 dans les fonctionnalités liées aux formulaires.

Par exemple, `EtudiantCardComponent` peut recevoir un objet `Etudiant` provenant d'un formulaire et servir à afficher une prévisualisation.

```text
Formulaire G7
      │
      ▼
Données de l'étudiant
      │
      ▼
EtudiantCardComponent
      │
      ▼
Prévisualisation
```

Le composant n'a pas besoin de connaître l'origine de l'objet `Etudiant`.

Il lui suffit de recevoir une valeur correspondant à l'`input` attendu :

```ts
etudiant = input.required<Etudiant>();
```

Cela permet de conserver le composant indépendant du contexte dans lequel il est utilisé.

---

# 9. Principes de réutilisabilité

Pour conserver des composants facilement réutilisables :

### 1. Inputs explicites

Les données nécessaires au composant doivent être clairement déclarées :

```ts
etudiant = input.required<Etudiant>();
```

### 2. Outputs explicites

Les actions que le composant peut signaler doivent être clairement déclarées :

```ts
supprimer = output<number>();
```

### 3. Pas de logique API dans EtudiantCardComponent

La carte ne doit pas appeler directement `EtudiantService`.

Elle se contente d'émettre l'identifiant de l'étudiant.

### 4. Gestion de l'API dans le composant parent ou le service

Actuellement, `EtudiantListComponent` reçoit l'action puis utilise `EtudiantService` pour effectuer la suppression.

### 5. Gestion claire des états

`EtudiantListComponent` prend en compte plusieurs états d'affichage :

* chargement ;
* erreur ;
* liste vide ;
* liste contenant des étudiants.

### 6. Utilisation des Signals

Les données dynamiques sont gérées avec des Signals :

```ts
etudiants = signal<Etudiant[]>([]);
loading = signal(true);
erreur = signal<string | null>(null);
pageActuelle = signal(0);
```

---

# 10. Préparation de la gestion des rôles

La gestion des rôles sera implémentée ultérieurement avec l'authentification JWT.

Les composants pourront alors adapter l'affichage selon le rôle de l'utilisateur.

Par exemple :

```text
ADMIN
→ Voir Modifier
→ Voir Supprimer

ETUDIANT
→ Consulter
→ Pas de bouton Supprimer
```

Cette évolution devra principalement agir sur l'affichage des actions sans remettre en cause la structure actuelle des composants.

Concernant G7, aucun bug d'affichage n'a été signalé.
