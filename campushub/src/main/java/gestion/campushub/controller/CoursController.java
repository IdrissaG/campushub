package gestion.campushub.controller;


import gestion.campushub.DTO.CoursRequest;
import gestion.campushub.DTO.CoursResponse;
import gestion.campushub.model.Cours;
import gestion.campushub.repository.CoursRepository;
import gestion.campushub.service.CoursService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gestion.campushub.mapper.CoursMapper.toCours;
import static gestion.campushub.mapper.CoursMapper.toRequest;

@RestController
@RequestMapping("/api/cours")
public class CoursController {
    private final CoursService coursService;


    public CoursController(CoursService coursService) {
        this.coursService = coursService;
    }

    @Operation(summary = "Lister tous les cours")
    @ApiResponse(responseCode = "200", description = "Liste des cours récupérée avec succès")
    @GetMapping
    public ResponseEntity<List<CoursResponse>> lesCours(){
        return ResponseEntity.ok(coursService.getAllCours());
    }
    @Operation(summary = "retrouver un cours a partir de son code")
    @ApiResponse(responseCode = "200", description = "cours trouvé")
    @ApiResponse(responseCode = "404", description = "Cours introuvable")
    @GetMapping("/{code}")
    public ResponseEntity<CoursResponse> trouverParCode(@PathVariable String code){
        return coursService.getCoursByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer un cours")
    @ApiResponse(responseCode = "201", description = "Cours créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    public ResponseEntity<CoursResponse> sauvegarderCours(@Valid @RequestBody CoursRequest monNouveauCours){
        CoursResponse coursResponse = coursService.createCours(monNouveauCours);
        return ResponseEntity.status(201).body(coursResponse);
    }

    @Operation(summary = "Modifier un cours")
    @ApiResponse(responseCode = "200", description = "Cours modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Cours introuvable")
    @PutMapping("/{code}")
    public ResponseEntity<CoursResponse> modifierCours(@PathVariable String code, @Valid @RequestBody CoursRequest coursRequest){
        return coursService.updateCours(code, coursRequest).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Supprimer un cours")
    @ApiResponse(responseCode = "204", description = "Cours supprimé")
    @ApiResponse(responseCode = "404", description = "Cours introuvable")
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> supprimerCours(@PathVariable String code){
        boolean supprime = coursService.deleteCours(code);

        if (!supprime) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }






}
