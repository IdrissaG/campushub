import { Component, input, output } from '@angular/core';
import { RouterLink } from '@angular/router'; // ← ajouté
import { Etudiant } from '../../model/etudiant.interface';

@Component({
  selector: 'app-etudiant-card-component',
  standalone: true,
  imports: [RouterLink], 
  templateUrl: './etudiant-card-component.html',
  styleUrl: './etudiant-card-component.scss',
})
export class EtudiantCardComponent {
  etudiant = input.required<Etudiant>();
  supprimer = output<number>();
}