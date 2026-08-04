package gestion.campushub.controller;

import gestion.campushub.DTO.EtudiantRequest;
import gestion.campushub.DTO.EtudiantResponse;
import gestion.campushub.mapper.EtudiantMapper;
import gestion.campushub.model.Etudiant;
import gestion.campushub.service.EtudiantsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/etudiants")
@Tag(name = "Étudiants", description = "Gestion des étudiants")
@CrossOrigin(origins = "http://localhost:4200")
public class EtudiantController {

    private final EtudiantsService service;

    public EtudiantController(EtudiantsService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les étudiants (pagination et tri)")
    @ApiResponse(responseCode = "200", description = "Page d'étudiants")
    public ResponseEntity<Page<EtudiantResponse>> getAll(
            @Parameter(description = "Numéro de la page (commence à 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Nombre d'étudiants par page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ sur lequel trier (ex: nom, prenom, age, filiere)", example = "nom")
            @RequestParam(defaultValue = "nom") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(
            service.getAllEtudiants(pageable).map(EtudiantMapper::toResponse)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un étudiant par ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Étudiant trouvé"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    public ResponseEntity<EtudiantResponse> getById(@PathVariable Long id) {
        return service.getEtudiantById(id)
            .map(e -> ResponseEntity.ok(EtudiantMapper.toResponse(e)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel étudiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Étudiant créé"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<EtudiantResponse> create(@Valid @RequestBody EtudiantRequest request) {
        Etudiant entity = EtudiantMapper.toEntity(request);
        Etudiant created = service.createEtudiant(entity);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(EtudiantMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un étudiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Étudiant modifié"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<EtudiantResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EtudiantRequest request) {
        Etudiant entity = EtudiantMapper.toEntity(request);
        return service.updateEtudiant(id, entity)
            .map(e -> ResponseEntity.ok(EtudiantMapper.toResponse(e)))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un étudiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Étudiant supprimé"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.deleteEtudiant(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
