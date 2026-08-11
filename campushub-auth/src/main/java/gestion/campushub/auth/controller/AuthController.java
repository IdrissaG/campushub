package gestion.campushub.auth.controller;

import gestion.campushub.auth.dto.AuthResponse;
import gestion.campushub.auth.dto.LoginRequest;
import gestion.campushub.auth.dto.RegisterRequest;
import gestion.campushub.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Creer un compte utilisateur")
    @ApiResponse(responseCode = "201", description = "Compte cree")
    @ApiResponse(responseCode = "400", description = "Email deja utilise ou donnees invalides")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(201).body(authService.register(request));
    }

    @Operation(summary = "Se connecter")
    @ApiResponse(responseCode = "200", description = "Connexion reussie, retourne un token JWT")
    @ApiResponse(responseCode = "401", description = "Identifiants invalides")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
