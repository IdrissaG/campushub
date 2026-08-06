package gestion.campushub.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EtudiantControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateSansAuth_retourne401() throws Exception {
        String json = """
            {
              "nom": "Diop",
              "prenom": "Awa",
              "email": "awa.diop@example.com",
              "age": 21,
              "filiere": "Informatique"
            }
            """;

        mockMvc.perform(post("/api/etudiants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isUnauthorized());
    }
}
