package gestion.campushub.controller;

import gestion.campushub.model.Role;
import gestion.campushub.model.Utilisateur;
import gestion.campushub.repository.CoursRepository;
import gestion.campushub.repository.UtilisateurRepository;
import gestion.campushub.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CoursControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String COURS_JSON = """
        {
          "code": "JAVA101",
          "nom": "Introduction a Java"
        }
        """;

    @Test
    void getCoursSansAuth_retourne200() throws Exception {
        mockMvc.perform(get("/api/cours"))
                .andExpect(status().isOk());
    }

    @Test
    void postCoursSansAuth_retourne401() throws Exception {
        mockMvc.perform(post("/api/cours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(COURS_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postCoursAvecTokenEtudiant_retourne403() throws Exception {
        String token = tokenPour(Role.ETUDIANT);

        mockMvc.perform(post("/api/cours")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(COURS_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void postCoursAvecTokenAdmin_retourne201() throws Exception {
        String token = tokenPour(Role.ADMIN);

        mockMvc.perform(post("/api/cours")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(COURS_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("JAVA101"))
                .andExpect(jsonPath("$.nom").value("Introduction a Java"));
    }

    @Test
    void postCoursAvecTokenAdminEtJsonInvalide_retourne400() throws Exception {
        String token = tokenPour(Role.ADMIN);

        mockMvc.perform(post("/api/cours")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "code": "",
                              "nom": ""
                            }
                            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void putCoursAvecTokenAdmin_retourne200() throws Exception {
        coursRepository.save(new gestion.campushub.model.Cours("JAVA102", "Java debutant"));
        String token = tokenPour(Role.ADMIN);

        mockMvc.perform(put("/api/cours/JAVA102")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "code": "JAVA102",
                              "nom": "Java avance"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("JAVA102"))
                .andExpect(jsonPath("$.nom").value("Java avance"));
    }

    @Test
    void deleteCoursAvecTokenAdminEtCodeInexistant_retourne404() throws Exception {
        String token = tokenPour(Role.ADMIN);

        mockMvc.perform(delete("/api/cours/UNKNOWN")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    private String tokenPour(Role role) {
        Utilisateur utilisateur = utilisateurRepository.save(
                new Utilisateur(
                        role.name().toLowerCase() + "." + UUID.randomUUID() + "@campushub.test",
                        passwordEncoder.encode("password"),
                        role
                )
        );
        return jwtService.generateToken(utilisateur);
    }
}
