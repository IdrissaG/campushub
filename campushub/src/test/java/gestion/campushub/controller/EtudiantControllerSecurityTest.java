package gestion.campushub.controller;

import gestion.campushub.model.Etudiant;
import gestion.campushub.model.Role;
import gestion.campushub.model.Utilisateur;
import gestion.campushub.repository.EtudiantRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private EtudiantRepository etudiantRepository;

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
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.erreurs").isArray());
    }

    @Test
    void testCreateAvecTokenEtudiant_retourne403() throws Exception {
        Utilisateur etudiant = utilisateurRepository.save(
                new Utilisateur("etudiant.test@example.com", passwordEncoder.encode("password"), Role.ETUDIANT)
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
                new Utilisateur("admin.test@example.com", passwordEncoder.encode("password"), Role.ADMIN)
        );
        String token = jwtService.generateToken(admin);

        mockMvc.perform(post("/api/etudiants")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ETUDIANT_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testDeleteSansAuth_retourne401() throws Exception {
        mockMvc.perform(delete("/api/etudiants/999"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.erreurs").isArray());
    }

    @Test
    void testDeleteAvecTokenEtudiant_retourne403() throws Exception {
        Utilisateur etudiant = utilisateurRepository.save(
                new Utilisateur("etudiant.delete@example.com", passwordEncoder.encode("password"), Role.ETUDIANT)
        );
        String token = jwtService.generateToken(etudiant);

        mockMvc.perform(delete("/api/etudiants/999")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteAvecTokenAdmin_retourne204() throws Exception {
        Etudiant etudiant = etudiantRepository.save(
                new Etudiant("Test", "Delete", "test.delete@example.com", 20, "Informatique")
        );

        Utilisateur admin = utilisateurRepository.save(
                new Utilisateur("admin.delete@example.com", passwordEncoder.encode("password"), Role.ADMIN)
        );
        String token = jwtService.generateToken(admin);

        mockMvc.perform(delete("/api/etudiants/" + etudiant.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAvecTokenInvalide_retourne401() throws Exception {
        mockMvc.perform(post("/api/etudiants")
                        .header("Authorization", "Bearer token.completement.invalide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ETUDIANT_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.erreurs").isArray());
    }
}
