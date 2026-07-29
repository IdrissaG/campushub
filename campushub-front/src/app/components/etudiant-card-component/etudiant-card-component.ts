import { Component, input } from '@angular/core';
import { Etudiant } from '../../model/etudiant.interface';

@Component({
  selector: 'app-etudiant-card-component',
  imports: [],
  templateUrl: './etudiant-card-component.html',
  styleUrl: './etudiant-card-component.scss',
})
export class EtudiantCardComponent {
  etudiant = input<Etudiant>({
    id: 1, nom: 'Sow', prenom: 'Ibrahima', filiere: 'Info',email: "isow@gmail.com",
    age: 0
  });
}
