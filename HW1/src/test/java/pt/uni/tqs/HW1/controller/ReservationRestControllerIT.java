package pt.uni.tqs.HW1.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.model.Reservation;
import pt.uni.tqs.HW1.repository.MenuRepository;
import pt.uni.tqs.HW1.repository.RefectoryRepository;
import pt.uni.tqs.HW1.repository.ReservationRepository;
import pt.uni.tqs.HW1.utils.MealType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ReservationRestControllerIT {

        @Autowired
        private MockMvc mvc;

        @Autowired
        private MenuRepository menuRepository;

        @Autowired
        private ReservationRepository reservationRepository;

        @Autowired
        private RefectoryRepository refectoryRepository;

        @AfterEach
        void cleanUp() {
                reservationRepository.deleteAll();
                menuRepository.deleteAll();
                refectoryRepository.deleteAll();
        }

        @Test
        void whenCreateReservation_thenReturnToken() throws Exception {
                Menu menu = createMenu();

                mvc.perform(post("/api/reservations?menuId=" + menu.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.token").exists());
        }

        @Test
        void whenCreateWithInvalidMenu_thenBadRequest() throws Exception {
                mvc.perform(post("/api/reservations?menuId=999"))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().string("Menu not found."));
        }

        @Test
        void whenCheckReservation_thenReturnValidMessage() throws Exception {
                Menu menu = createMenu();

                Reservation reservation = reservationRepository.save(new Reservation(menu));

                mvc.perform(get("/api/reservations/" + reservation.getToken()))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Reservation is valid."));
        }

        @Test
        void whenCheckInvalidToken_thenReturn404() throws Exception {
                mvc.perform(get("/api/reservations/invalid-token"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Reservation not found or already used."));
        }

        @Test
        void whenCancelReservation_thenSuccess() throws Exception {
                Menu menu = createMenu();

                Reservation reservation = reservationRepository.save(new Reservation(menu));

                mvc.perform(delete("/api/reservations/" + reservation.getToken()))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Reservation has been cancelled."));
        }

        @Test
        void whenCancelInvalidToken_thenReturn404() throws Exception {
                mvc.perform(delete("/api/reservations/invalid-token"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Reservation not found."));
        }

        @Test
        void whenCheckIn_thenReturnSuccess() throws Exception {
                Menu menu = createMenu();

                Reservation reservation = reservationRepository.save(new Reservation(menu));

                mvc.perform(post("/api/reservations/" + reservation.getToken() + "/checkin"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Reservation successfully checked in."));
        }

        @Test
        void whenCheckInInvalid_thenReturnError() throws Exception {
                mvc.perform(post("/api/reservations/invalid/checkin"))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().string(containsString("Reservation is invalid or already used.")));
        }

        private Menu createMenu() {
                Refectory refectory = new Refectory("Moliceiro", "Campus", 25);
                refectory = refectoryRepository.save(refectory);

                Menu menu = menuRepository.save(new Menu(LocalDate.now(), "Bitoque", MealType.LUNCH, refectory));
                return menu;
        }
}
