package gestion.campushub.controller;

import gestion.campushub.model.Role;
import gestion.campushub.model.Utilisateur;
import gestion.campushub.repository.UtilisateurRepository;
import gestion.campushub.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EtudiantControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String ETUDIANT_JSON = """
        {
          "nom": "Diop",
          "prenom": "Awa",
          "email": "awa.diop@example.com",
          "age": 21,
          "filiere": "Informatique"
        }
        """;

    @Test
    void testCreateSansAuth_retourne401() throws Exception {
        mockMvc.perform(post("/api/etudiants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ETUDIANT_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testCreateAvecTokenEtudiant_retourne403() throws Exception {
        Utilisateur etudiant = utilisateurRepository.save(
            new Utilisateur("etudiant.test@example.com", passwordEncoder.encode("password"), Role.ETUDIANT, "Etudiant Test")
        );
        String token = jwtService.generateToken(etudiant);

        mockMvc.perform(post("/api/etudiants")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ETUDIANT_JSON))
            .andExpect(status().isForbidden());
    }

    @Test
    void testCreateAvecTokenAdmin_retourne201() throws Exception {
        Utilisateur admin = utilisateurRepository.save(
            new Utilisateur("admin.test@example.com", passwordEncoder.encode("password"), Role.ADMIN, "Admin Test")
        );
        String token = jwtService.generateToken(admin);

        mockMvc.perform(post("/api/etudiants")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ETUDIANT_JSON))
            .andExpect(status().isCreated());
    }
}
