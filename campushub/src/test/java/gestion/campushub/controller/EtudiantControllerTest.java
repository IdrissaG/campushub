package gestion.campushub.controller;

import gestion.campushub.model.Etudiant;
import gestion.campushub.service.EtudiantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EtudiantController.class)
class EtudiantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EtudiantService service;

    @Test
    void getAllRenvoie200() throws Exception {
        when(service.getAll()).thenReturn(List.of(new Etudiant(1L, "Awa", 20, "Informatique")));

        mockMvc.perform(get("/api/etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Awa"));
    }

    @Test
    void getByIdRenvoie200QuandTrouve() throws Exception {
        when(service.getById(1L)).thenReturn(Optional.of(new Etudiant(1L, "Awa", 20, "Informatique")));

        mockMvc.perform(get("/api/etudiants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Awa"));
    }

    @Test
    void getByIdRenvoie404QuandInconnu() throws Exception {
        when(service.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/etudiants/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void postRenvoie201() throws Exception {
        when(service.create(any(Etudiant.class))).thenReturn(new Etudiant(1L, "Awa", 20, "Informatique"));

        mockMvc.perform(post("/api/etudiants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Awa\",\"age\":20,\"filiere\":\"Informatique\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void putRenvoie200QuandTrouve() throws Exception {
        when(service.update(eq(1L), any(Etudiant.class)))
                .thenReturn(Optional.of(new Etudiant(1L, "Awa Diop", 21, "Informatique")));

        mockMvc.perform(put("/api/etudiants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Awa Diop\",\"age\":21,\"filiere\":\"Informatique\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Awa Diop"));
    }

    @Test
    void putRenvoie404QuandInconnu() throws Exception {
        when(service.update(eq(999L), any(Etudiant.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/etudiants/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"X\",\"age\":1,\"filiere\":\"Y\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRenvoie204QuandTrouve() throws Exception {
        when(service.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/etudiants/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(service).delete(1L);
    }

    @Test
    void deleteRenvoie404QuandInconnu() throws Exception {
        when(service.delete(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/etudiants/999"))
                .andExpect(status().isNotFound());
    }
}
