package gestion.campushub.controller;

import gestion.campushub.common.ErreurResponse;
import gestion.campushub.dto.CoursRequest;
import gestion.campushub.dto.CoursResponse;
import gestion.campushub.mapper.CoursMapper;
import gestion.campushub.model.Cours;
import gestion.campushub.service.CoursService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cours")
@Tag(name = "Gestion des Cours", description = "Endpoints permettant de gérer le catalogue de cours de CampusHub")
public class CoursController {

    private final CoursService coursService;
    private final CoursMapper coursMapper;

    public CoursController(CoursService coursService, CoursMapper coursMapper) {
        this.coursService = coursService;
        this.coursMapper = coursMapper;
    }

    @GetMapping
    @Operation(summary = "Récupérer la liste de tous les cours", description = "Retourne l'intégralité du catalogue des cours disponibles.")
    @ApiResponse(responseCode = "200", description = "Liste des cours récupérée avec succès")
    public ResponseEntity<List<CoursResponse>> lesCours() {
        List<CoursResponse> responses = coursService.getAllCours().stream()
                .map(coursMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{code}")
    @Operation(summary = "Trouver un cours par son code", description = "Recherche un cours spécifique à l'aide de sa clé unique (code).")
    @ApiResponse(responseCode = "200", description = "Cours trouvé")
    @ApiResponse(responseCode = "404", description = "Aucun cours ne correspond à ce code", content = @Content)
    public ResponseEntity<CoursResponse> trouverParCode(@PathVariable String code) {
        return coursService.getCoursByCode(code)
                .map(coursMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau cours", description = "Ajoute un cours au catalogue après validation des contraintes.")
    @ApiResponse(responseCode = "201", description = "Cours créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données d'entrée invalides", 
                 content = @Content(schema = @Schema(implementation = ErreurResponse.class)))
    public ResponseEntity<CoursResponse> sauvegarderCours(@Valid @RequestBody CoursRequest request) {
        Cours cours = coursMapper.toEntity(request);
        Cours coursCree = coursService.createCours(cours);
        return ResponseEntity.status(201).body(coursMapper.toResponse(coursCree));
    }

    @PutMapping("/{code}")
    @Operation(summary = "Modifier un cours existant", description = "Met à jour les informations d'un cours identifié par son code.")
    @ApiResponse(responseCode = "200", description = "Cours mis à jour avec succès")
    @ApiResponse(responseCode = "400", description = "Données de modification invalides", 
                 content = @Content(schema = @Schema(implementation = ErreurResponse.class)))
    @ApiResponse(responseCode = "404", description = "Cours introuvable", content = @Content)
    public ResponseEntity<CoursResponse> modifierCours(@PathVariable String code, @Valid @RequestBody CoursRequest request) {
        Cours coursModifie = coursMapper.toEntity(request);
        return coursService.updateCours(code, coursModifie)
                .map(coursMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    @Operation(summary = "Supprimer un cours", description = "Supprime un cours du catalogue à partir de son code.")
    @ApiResponse(responseCode = "204", description = "Cours supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Cours introuvable", content = @Content)
    public ResponseEntity<Void> supprimerCours(@PathVariable String code) {
        coursService.deleteCours(code);
        return ResponseEntity.noContent().build();
    }
}

// On a injecté les annotations OpenAPI @Tag, @Operation et @ApiResponse pour documenter les endpoints du contrôleur CoursController. Cela permet de générer automatiquement une documentation Swagger détaillée pour chaque opération, incluant les codes de réponse et les descriptions associées.

//  A Noter l'utilisation de ErreurResponse.class sur les réponses 400 et 500 pour que la structure d'erreur demandée apparaisse clairement sur l'interface Swagger.