export interface EtudiantRequest {
  nom: string;
  prenom: string;
  email: string;
  age: number;
  filiere: string;
}

export interface EtudiantResponse {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  age: number;
  filiere: string;
}