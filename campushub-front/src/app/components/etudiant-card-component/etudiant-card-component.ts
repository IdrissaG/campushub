import { Component, input, output } from '@angular/core';
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
  supprimer = output<number>();
}
// card attend obligatoirement un étudiant venant de l'extérieur.
