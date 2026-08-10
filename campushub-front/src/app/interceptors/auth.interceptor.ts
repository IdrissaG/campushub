import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  // Si token présent, on clone la requête et on ajoute le header Authorization
  const requeteAvecToken = token
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req; // Pas de token (ex: login) → requête inchangée

  return next(requeteAvecToken).pipe(
    catchError((erreur: HttpErrorResponse) => {
      // Token expiré ou invalide → déconnexion automatique
      if (erreur.status === 401) {
        authService.logout();
      }
      // On propage l'erreur pour que le composant puisse la recevoir
      return throwError(() => erreur);
    })
  );
};