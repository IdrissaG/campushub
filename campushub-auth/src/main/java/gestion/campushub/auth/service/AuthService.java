package gestion.campushub.auth.service;

import gestion.campushub.auth.dto.AuthResponse;
import gestion.campushub.auth.dto.LoginRequest;
import gestion.campushub.auth.dto.RegisterRequest;
import gestion.campushub.auth.repository.UtilisateurRepository;
import gestion.campushub.auth.model.Role;
import gestion.campushub.auth.model.Utilisateur;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UtilisateurRepository utilisateurRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Cet email est deja utilise");
        }

        Role role = (request.role() == null || request.role().isBlank())
                ? Role.ETUDIANT
                : Role.valueOf(request.role().toUpperCase());

        Utilisateur utilisateur = new Utilisateur(
                request.email(),
                passwordEncoder.encode(request.motDePasse()),
                role
        );
        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
        String token = jwtService.generateToken(savedUser);
        return new AuthResponse(token, savedUser.getRole().name());
    }
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.motDePasse()
                )
        );

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        String token = jwtService.generateToken(utilisateur);

        return new AuthResponse(token, utilisateur.getRole().name());
    }
}
