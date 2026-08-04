import { Component, input } from '@angular/core';
import { Etudiant } from '../../model/etudiant.interface';


@Component({
  selector: 'app-etudiant-card-component',
  standalone: true,
  imports: [],
  templateUrl: './etudiant-card-component.html',
  styleUrl: './etudiant-card-component.scss',
})
export class EtudiantCardComponent {
  etudiant = input.required<Etudiant>();
}
// card attend obligatoirement un étudiant venant de l'extérieur.