import { Routes } from '@angular/router';
import { EtudiantListComponent } from './components/etudiant-list-component/etudiant-list-component';
import { EtudiantFormComponent } from './components/etudiant-form-component/etudiant-form-component';
import { EtudiantDetailComponent } from './components/etudiant-detail-component/etudiant-detail-component';
import { NotFoundComponent } from './components/not-found-component/not-found-component';

export const routes: Routes = [
  //Redirection vers la liste des étudiants par défaut
  { path: '', redirectTo: 'etudiants', pathMatch: 'full' },
  //Route 1 : Liste des étudiants
  { path: 'etudiants', component: EtudiantListComponent },
  //Route 2 : Fiche détaillée d'un étudiant(informations personnelles, filière, etc.)
  { path: 'etudiants/:id', component: EtudiantDetailComponent },
  //Route 3 : Formulaire de modification
  { path: 'etudiants/:id/modifier', component: EtudiantFormComponent },
  //Route 4 : Formulaire de création
  { path: 'etudiants/nouveau', component: EtudiantFormComponent },
  //Route 5 : Page 404
  { path: '**', component: NotFoundComponent },
];