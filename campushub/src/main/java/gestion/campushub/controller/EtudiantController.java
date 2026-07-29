package gestion.campushub.controller;

import gestion.campushub.dto.EtudiantRequest;
import gestion.campushub.dto.EtudiantResponse;
import gestion.campushub.mapper.EtudiantMapper;
import gestion.campushub.model.Etudiant; // Package officiel restauré
import gestion.campushub.service.EtudiantsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final EtudiantsService service;
    private final EtudiantMapper etudiantMapper;

    public EtudiantController(EtudiantsService service, EtudiantMapper etudiantMapper) {
        this.service = service;
        this.etudiantMapper = etudiantMapper;
    }

    @GetMapping
    public ResponseEntity<List<EtudiantResponse>> getAllEtudiants() {
        List<EtudiantResponse> responses = service.getAllEtudiants().stream()
                .map(etudiantMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtudiantResponse> getEtudiantById(@PathVariable String id) {
        return service.getEtudiantById(Long.valueOf(id))
                .map(etudiantMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EtudiantResponse> createEtudiant(@Valid @RequestBody EtudiantRequest request) {
        Etudiant etudiant = etudiantMapper.toEntity(request);
        Etudiant created = service.createEtudiant(etudiant);
        return ResponseEntity.status(HttpStatus.CREATED).body(etudiantMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtudiantResponse> updateEtudiant(@PathVariable String id, @Valid @RequestBody EtudiantRequest request) {
        Etudiant etudiantModifie = etudiantMapper.toEntity(request);
        return service.updateEtudiant(Long.valueOf(id), etudiantModifie)
                .map(etudiantMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable String id) {
        boolean deleted = service.deleteEtudiant(Long.valueOf(id));
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}