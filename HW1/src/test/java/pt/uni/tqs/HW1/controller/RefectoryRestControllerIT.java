package pt.uni.tqs.HW1.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.repository.RefectoryRepository;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class RefectoryRestControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RefectoryRepository repository;

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void whenGetAll_thenReturnAllRefectories() throws Exception {
        Refectory r1 = new Refectory("Moliceiro", "Campus A", 100);
        Refectory r2 = new Refectory("Barlavento", "Campus B", 80);
        repository.saveAndFlush(r1);
        repository.saveAndFlush(r2);

        mvc.perform(get("/api/refectories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Moliceiro")))
                .andExpect(jsonPath("$[1].name", is("Barlavento")));
    }

    @Test
    void whenGetValidId_thenReturnRefectory() throws Exception {
        Refectory r = repository.save(new Refectory("Moliceiro", "Campus A", 100));

        mvc.perform(get("/api/refectories/refectories/" + r.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Moliceiro")))
                .andExpect(jsonPath("$.location", is("Campus A")));
    }

    @Test
    void whenGetInvalidId_thenReturn404() throws Exception {
        mvc.perform(get("/api/refectories/refectories/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
