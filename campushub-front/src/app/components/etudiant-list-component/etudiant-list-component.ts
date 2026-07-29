import { Component } from '@angular/core';
import { Etudiant } from '../../model/etudiant.interface';
import { EtudiantCardComponent } from '../etudiant-card-component/etudiant-card-component';

@Component({
  selector: 'app-etudiant-list-component',
  imports: [EtudiantCardComponent],
  templateUrl: './etudiant-list-component.html',
  styleUrl: './etudiant-list-component.scss',
})
export class EtudiantListComponent {
  etudiants: Etudiant[] = [
  { id: 1, nom: 'Diop',   prenom: 'Awa',    filiere: 'Info',    email: 'awa.diop@gmail.com' },
  { id: 2, nom: 'Fall',   prenom: 'Moussa', filiere: 'Gestion', email: 'moussa.fall@gmail.com' },
  { id: 3, nom: 'Ndiaye', prenom: 'Astou',  filiere: 'Chimie',  email: 'astou.ndiaye@gmail.com' },
  { id: 4, nom: 'Faye',   prenom: 'Aly',    filiere: 'Maths',   email: 'aly.faye@gmail.com' },
];
}
