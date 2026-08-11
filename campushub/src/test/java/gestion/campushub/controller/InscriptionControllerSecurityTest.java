package gestion.campushub.controller;

import gestion.campushub.model.Cours;
import gestion.campushub.model.Etudiant;
import gestion.campushub.model.Role;
import gestion.campushub.model.Utilisateur;
import gestion.campushub.repository.CoursRepository;
import gestion.campushub.repository.EtudiantRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class InscriptionControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void getInscriptionsSansAuth_retourne401() throws Exception {
        mockMvc.perform(get("/api/inscriptions"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getInscriptionsAvecTokenEtudiant_retourne403() throws Exception {
        String token = tokenPour(Role.ETUDIANT);

        mockMvc.perform(get("/api/inscriptions")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void getInscriptionsAvecTokenAdmin_retourne200() throws Exception {
        String token = tokenPour(Role.ADMIN);

        mockMvc.perform(get("/api/inscriptions")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void getInscriptionsAvecNoteMinimumAvecTokenEtudiant_retourne200() throws Exception {
        String token = tokenPour(Role.ETUDIANT);

        mockMvc.perform(get("/api/inscriptions/notes")
                        .header("Authorization", "Bearer " + token)
                        .param("seuil", "12"))
                .andExpect(status().isOk());
    }

    @Test
    void postInscriptionSansAuth_retourne401() throws Exception {
        mockMvc.perform(post("/api/inscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "etudiantId": 1,
                              "coursId": 1,
                              "note": 15.0,
                              "dateInscription": "2026-08-11"
                            }
                            """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postInscriptionAvecTokenEtudiant_retourne403() throws Exception {
        String token = tokenPour(Role.ETUDIANT);

        mockMvc.perform(post("/api/inscriptions")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "etudiantId": 1,
                              "coursId": 1,
                              "note": 15.0,
                              "dateInscription": "2026-08-11"
                            }
                            """))
                .andExpect(status().isForbidden());
    }

    @Test
    void postInscriptionAvecTokenAdmin_retourne201() throws Exception {
        Etudiant etudiant = etudiantRepository.save(
                new Etudiant("Diop", "Awa", "awa." + UUID.randomUUID() + "@test.com", 21, "Info")
        );
        Cours cours = coursRepository.save(new Cours("JAVA" + UUID.randomUUID().toString().substring(0, 8), "Java"));
        String token = tokenPour(Role.ADMIN);

        mockMvc.perform(post("/api/inscriptions")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "etudiantId": %d,
                              "coursId": %d,
                              "note": 15.0,
                              "dateInscription": "2026-08-11"
                            }
                            """.formatted(etudiant.getId(), cours.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.etudiantNom").value("Diop"))
                .andExpect(jsonPath("$.coursNom").value("Java"))
                .andExpect(jsonPath("$.note").value(15.0));
    }

    @Test
    void postInscriptionAvecTokenAdminEtRessourceIntrouvable_retourne404() throws Exception {
        String token = tokenPour(Role.ADMIN);

        mockMvc.perform(post("/api/inscriptions")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "etudiantId": 9999,
                              "coursId": 9999,
                              "note": 15.0,
                              "dateInscription": "2026-08-11"
                            }
                            """))
                .andExpect(status().isNotFound());
    }

    @Test
    void postInscriptionAvecTokenAdminEtJsonInvalide_retourne400() throws Exception {
        String token = tokenPour(Role.ADMIN);

        mockMvc.perform(post("/api/inscriptions")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "note": 15.0
                            }
                            """))
                .andExpect(status().isBadRequest());
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
