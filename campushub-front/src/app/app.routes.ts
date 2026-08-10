import { Routes } from '@angular/router';
import { EtudiantListComponent } from './components/etudiant-list-component/etudiant-list-component';
import { EtudiantFormComponent } from './components/etudiant-form-component/etudiant-form-component';
import { EtudiantDetailComponent } from './components/etudiant-detail-component/etudiant-detail-component';
import { NotFoundComponent } from './components/not-found-component/not-found-component';
import { authGuard } from './guards/auth.guard';
import { LoginComponent } from './components/login-component/login-component';
import { adminGuard } from './guards/admin.guards';

export const routes: Routes = [
  { path: '', redirectTo: 'etudiants', pathMatch: 'full' },

  { path: 'etudiants', component: EtudiantListComponent },

  { path: 'etudiants/nouveau', component: EtudiantFormComponent, canActivate: [adminGuard] },

  { path: 'etudiants/:id/modifier', component: EtudiantFormComponent, canActivate: [adminGuard] },

  { path: 'etudiants/:id', component: EtudiantDetailComponent },
  
  { path: 'login', component: LoginComponent},

  { path: '**', component: NotFoundComponent }

];