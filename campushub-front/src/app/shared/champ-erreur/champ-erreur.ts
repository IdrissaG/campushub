import { Component, input } from '@angular/core';


@Component({
  selector: 'app-champ-erreur',
  standalone: true,
  template: `
    @if (message()) {
      <span class="erreur-champ">{{ message() }}</span>
    }
  `
})
export class ChampErreurComponent {
  message = input<string | null>(null);
}