package gestion.campushub.controller;


import gestion.campushub.DTO.CoursRequest;
import gestion.campushub.DTO.CoursResponse;
import gestion.campushub.model.Cours;
import gestion.campushub.repository.CoursRepository;
import gestion.campushub.service.CoursService;
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
    private final CoursRepository coursRepository;


    public CoursController(CoursService coursService, CoursRepository coursRepository) {
        this.coursService = coursService;
        this.coursRepository = coursRepository;
    }

    @GetMapping
    public ResponseEntity<List<CoursResponse>> lesCours(){
        return ResponseEntity.ok(coursService.getAllCours());
    }

    @GetMapping("/{code}")
    public ResponseEntity<CoursResponse> trouverParCode(@PathVariable String code){
        return coursService.getCoursByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CoursResponse> sauvegarderCours(@Valid @RequestBody CoursRequest monNouveauCours){
        CoursResponse coursResponse = coursService.createCours(monNouveauCours);
        return ResponseEntity.status(201).body(coursResponse);
    }

    @PutMapping("/{code}")
    public ResponseEntity<CoursResponse> modifierCours(@PathVariable String code, @Valid @RequestBody CoursRequest coursRequest){
        return coursService.updateCours(code, coursRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> supprimerCours(@PathVariable String code){
        coursService.deleteCours(code);
        return ResponseEntity.noContent().build();
    }






}
