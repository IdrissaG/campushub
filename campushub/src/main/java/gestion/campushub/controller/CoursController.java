package gestion.campushub.controller;


import gestion.campushub.model.Cours;
import gestion.campushub.service.CoursService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {
    private final CoursService coursService;


    public CoursController(CoursService coursService) {
        this.coursService = coursService;
    }

    @GetMapping
    public ResponseEntity<List<Cours>> lesCours(){
        return ResponseEntity.ok(coursService.getAllCours());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Cours> trouverParCode(@PathVariable String code){
        return coursService.getCoursByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cours> sauvegarderCours(@RequestBody Cours monNouveauCours){
        Cours coursCree = coursService.createCours(monNouveauCours);
        return ResponseEntity.status(201).body(coursCree);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Cours> modifierCours(@PathVariable String code, @RequestBody Cours cours){
        return coursService.updateCours(code, cours)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> supprimerCours(@PathVariable String code){
        coursService.deleteCours(code);
        return ResponseEntity.noContent().build();
    }






}
