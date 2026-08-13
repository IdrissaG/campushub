import { computed, inject, Injectable, signal } from "@angular/core";
import { AuthResponse, LoginRequest, RegisterRequest } from "../model/auth.interface";
import { environment } from "../../environments/environment";
import { Observable, tap } from "rxjs";
import { Router } from "@angular/router";  // ← corrigé
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: 'root' })
export class AuthService {

  private http = inject(HttpClient);
  private router = inject(Router);

  private tokenSignal = signal<string | null>(
    typeof window !== 'undefined' ? this.lireTokenValide() : null
  );
  private roleSignal = signal<string | null>(
    typeof window !== 'undefined' && this.lireTokenValide() ? localStorage.getItem('role') : null
  );

  estConnecte = computed(() => this.tokenSignal() !== null);
  estAdmin    = computed(() => this.roleSignal() === 'ADMIN');

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.apiUrl}/auth/login`, credentials)
      .pipe(tap(response => this.stockerSession(response)));
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.tokenSignal.set(null);
    this.roleSignal.set(null);
    this.router.navigate(['/etudiants']);
  }

  register(data: RegisterRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${environment.apiUrl}/auth/register`, data)
      .pipe(tap(response => this.stockerSession(response)));
  }

  getToken(): string | null {
    return this.tokenSignal();
  }

  private stockerSession(response: AuthResponse): void {
    localStorage.setItem('token', response.token);
    localStorage.setItem('role', response.role);
    this.tokenSignal.set(response.token);
    this.roleSignal.set(response.role);
  }

  private lireTokenValide(): string | null {
    const token = localStorage.getItem('token');
    if (!token) return null;

    if (this.estTokenExpire(token)) {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      return null;
    }
    return token;
  }

  private estTokenExpire(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expirationMs = payload.exp * 1000;
      return Date.now() >= expirationMs;
    } catch {
      return true;
    }
  }
}