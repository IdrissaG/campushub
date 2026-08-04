export interface Etudiant {
  id:  number;
  nom: String;
  prenom: String;
  email: String;
  age: number;
  filiere: String
}
// EtudiantRequest avec id pour la récupération d'un étudiant existant pour GET, PUT, DELETE
// EtudiantRequest sans id (pour la création d'un nouvel étudiant) pour POST
