import { Component, inject, OnInit, signal } from '@angular/core';
import { Etudiant } from '../../model/etudiant.interface';
import { EtudiantCardComponent } from '../etudiant-card-component/etudiant-card-component';
import { EtudiantService } from '../../services/etudiant-service';

@Component({
  selector: 'app-etudiant-list-component',
  standalone: true,
  imports: [EtudiantCardComponent],

  templateUrl: './etudiant-list-component.html',
  styleUrl: './etudiant-list-component.scss',
})

export class EtudiantListComponent implements OnInit {

  etudiants = signal<Etudiant[]>([]);

  private etudiantService = inject(EtudiantService);

  loading = signal(true);
  erreur = signal<string | null>(null);

  ngOnInit() {
    this.etudiantService.getAll().subscribe({
      next: (data) => {
        this.etudiants.set(data.content);
        this.loading.set(false);
      },
      error: (err) => {
        this.erreur.set('Impossible de charger les étudiants');
        this.loading.set(false);
      }
    });
  }

  onSupprimer(id: number) {
    this.etudiantService.delete(id).subscribe({
      next: () => {
        this.etudiants.set(this.etudiants().filter(etudiant => etudiant.id !== id));
      },
      error: () => {
        this.erreur.set('Impossible de supprimer l\'étudiant');
      }
    });
  }
}
