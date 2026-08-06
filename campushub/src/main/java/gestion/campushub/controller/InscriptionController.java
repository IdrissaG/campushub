package gestion.campushub.controller;

import gestion.campushub.DTO.InscriptionRequest;
import gestion.campushub.DTO.InscriptionResponse;
import gestion.campushub.service.InscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
@Tag(name = "Inscriptions", description = "Consultation des inscriptions aux cours")
public class InscriptionController {
    private final InscriptionService inscriptionService;
    public InscriptionController(InscriptionService inscriptionService){
        this.inscriptionService = inscriptionService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les inscriptions")
    @ApiResponse(responseCode = "200", description = "Liste des inscriptions récupérée avec succès")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InscriptionResponse>> getAll(){
        return ResponseEntity.ok(inscriptionService.toutesLesInscriptions());
    }

    @GetMapping("/etudiants/{etudiantId}")
    @Operation(summary = "Lister les inscriptions d'un étudiant")
    @ApiResponse(responseCode = "200", description = "Inscriptions de l'étudiant récupérées avec succès")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InscriptionResponse>> getByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(inscriptionService.inscriptionsParEtudiant(etudiantId));
    }

    @GetMapping("/cours/{coursId}")
    @Operation(summary = "Lister les inscriptions d'un cours")
    @ApiResponse(responseCode = "200", description = "Inscriptions du cours récupérées avec succès")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InscriptionResponse>> getByCours(@PathVariable Long coursId) {
        return ResponseEntity.ok(inscriptionService.inscriptionsParCours(coursId));
    }

    @GetMapping("/notes")
    @Operation(summary = "Lister les inscriptions avec une note minimum")
    @ApiResponse(responseCode = "200", description = "Inscriptions filtrées par note récupérées avec succès")
    public ResponseEntity<List<InscriptionResponse>> getAvecNoteMinimum(@RequestParam double seuil) {
        return ResponseEntity.ok(inscriptionService.inscriptionsAvecNoteMinimum(seuil));
    }

    @PostMapping
    @Operation(summary = "Créer une inscription")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscription créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Étudiant ou cours introuvable")
    })
    public ResponseEntity<InscriptionResponse> create(@Valid @RequestBody InscriptionRequest request) {
        return inscriptionService.creerInscription(request)
                .map(inscription -> ResponseEntity.status(HttpStatus.CREATED).body(inscription))
                .orElse(ResponseEntity.notFound().build());
    }
}
