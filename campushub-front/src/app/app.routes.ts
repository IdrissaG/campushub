import { Routes } from '@angular/router';
import { EtudiantListComponent } from './components/etudiant-list-component/etudiant-list-component';
import { EtudiantFormComponent } from './components/etudiant-form-component/etudiant-form-component';
import { EtudiantDetailComponent } from './components/etudiant-detail-component/etudiant-detail-component';
import { NotFoundComponent } from './components/not-found-component/not-found-component';

export const routes: Routes = [
  // Redirection par défaut vers /etudiants
  { path: '', redirectTo: 'etudiants', pathMatch: 'full' },

  // Route 1 : Liste
  { path: 'etudiants', component: EtudiantListComponent },

  // Route 2 : Formulaire de création 
  { path: 'etudiants/nouveau', component: EtudiantFormComponent },

  // Route 3 : Fiche détaillée d'un étudiant
  { path: 'etudiants/:id', component: EtudiantDetailComponent },

  // Route 4 : Page 404 
  { path: '**', component: NotFoundComponent },
];