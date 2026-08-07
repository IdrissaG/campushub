import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: 'etudiants/:id',
    renderMode: RenderMode.Client
  },
  {
    path: 'etudiants/:id/modifier',
    renderMode: RenderMode.Client
  },
  {
    path: '**',
    renderMode: RenderMode.Prerender
  }
];