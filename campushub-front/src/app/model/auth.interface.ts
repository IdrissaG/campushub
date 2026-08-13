// Ce qu'on ENVOIE à G5 pour se connecter
export interface LoginRequest {
  email: string;
  motDePasse: string;
}

// Ce que G5 RENVOIE après connexion réussie
export interface AuthResponse {
  token: string; // JWT à inclure dans chaque requête protégée
  role: string;  // 'ADMIN' ou 'ETUDIANT'
}


export interface RegisterRequest {
  email: string;
  motDePasse: string;
  nom: string;
}
