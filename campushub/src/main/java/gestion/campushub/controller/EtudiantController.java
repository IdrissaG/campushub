package gestion.campushub.controller;

import gestion.campushub.model.Etudiant;
import gestion.campushub.service.EtudiantsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final EtudiantsService service;

    // Injection par constructeur (Consigne respectée !)
    public EtudiantController(EtudiantsService service) {
        this.service = service;
    }

    // 1. GET /api/etudiants -> 200 OK
    @GetMapping
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        return ResponseEntity.ok(service.getAllEtudiants());
    }

    // 2. GET /api/etudiants/{id} -> 200 OK ou 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable Long id) {
        return service.getEtudiantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST /api/etudiants -> 201 Created
    @PostMapping
    public ResponseEntity<Etudiant> createEtudiant(@Valid @RequestBody Etudiant etudiant) {
        Etudiant created = service.createEtudiant(etudiant);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 4. PUT /api/etudiants/{id} -> 200 OK ou 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable Long id, @Valid @RequestBody Etudiant etudiant) {
        return service.updateEtudiant(id, etudiant)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE /api/etudiants/{id} -> 204 No Content ou 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        boolean deleted = service.deleteEtudiant(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}