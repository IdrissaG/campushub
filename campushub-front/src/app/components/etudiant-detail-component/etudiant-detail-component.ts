import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EtudiantService } from '../../services/etudiant-service';
import { EtudiantResponse } from '../../model/etudiant-api.interface';

@Component({
  selector: 'app-etudiant-detail-component',
  imports: [],
  templateUrl: './etudiant-detail-component.html',
  styleUrl: './etudiant-detail-component.scss',
})
export class EtudiantDetailComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private etudiantService = inject(EtudiantService);

  etudiant = signal<EtudiantResponse | null>(null);
  erreur = signal<string | null>(null);

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (!idParam) return;

    const id = Number(idParam);

    this.etudiantService.getById(id).subscribe({
      next: (data) => this.etudiant.set(data),
      error: () => this.erreur.set("Étudiant introuvable.")
    });
  }
}
