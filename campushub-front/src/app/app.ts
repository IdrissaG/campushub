import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { EtudiantListComponent } from "./components/etudiant-list-component/etudiant-list-component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, EtudiantListComponent,],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('campushub-front');
}
