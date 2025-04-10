package pt.uni.tqs.HW1.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.repository.MenuRepository;
import pt.uni.tqs.HW1.repository.RefectoryRepository;
import pt.uni.tqs.HW1.repository.ReservationRepository;
import pt.uni.tqs.HW1.utils.MealType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ReservationRestFullFlowIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RefectoryRepository refectoryRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @AfterEach
    void cleanUp() {
        reservationRepository.deleteAll();
        menuRepository.deleteAll();
        refectoryRepository.deleteAll();
    }

    @Test
    void fullFlow_createValidateAndCancel() throws Exception {
        Refectory ref = refectoryRepository.save(new Refectory("Campus A", "Zone A", 3));
        Menu menu = menuRepository.save(new Menu(LocalDate.now(), "Feijoada", MealType.LUNCH, ref));

        MvcResult createResult = mvc.perform(post("/api/reservations?menuId=" + menu.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();
        String token = JsonPath.read(createResult.getResponse().getContentAsString(), "$.token");

        mvc.perform(get("/api/reservations/" + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation is valid."));

        mvc.perform(delete("/api/reservations/" + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation has been cancelled."));
    }

    @Test
    void fullFlow_createAndCheckIn() throws Exception {
        Refectory ref = refectoryRepository.save(new Refectory("Campus B", "Zone B", 2));
        Menu menu = menuRepository.save(new Menu(LocalDate.now(), "Arroz de pato", MealType.DINNER, ref));

        MvcResult result = mvc.perform(post("/api/reservations?menuId=" + menu.getId()))
                .andExpect(status().isOk())
                .andReturn();
        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

        mvc.perform(post("/api/reservations/" + token + "/checkin"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation successfully checked in."));
    }

    @Test
    void fullFlow_fillCapacityAndFailLast() throws Exception {
        Refectory ref = refectoryRepository.save(new Refectory("Campus C", "Zone C", 2));
        Menu menu = menuRepository.save(new Menu(LocalDate.now(), "Sopa", MealType.LUNCH, ref));
    
        mvc.perform(post("/api/reservations?menuId=" + menu.getId()))
                .andExpect(status().isOk());
    
        mvc.perform(post("/api/reservations?menuId=" + menu.getId()))
                .andExpect(status().isOk());
    
        mvc.perform(post("/api/reservations?menuId=" + menu.getId()))
                .andExpect(status().isConflict())
                .andExpect(content().string("There are no more seats available for this menu."));
    }
    
}
