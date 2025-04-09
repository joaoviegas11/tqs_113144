package pt.uni.tqs.HW1.ThymeleafController;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ReservationPageControllerIT {

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
    void whenReserve_thenShowConfirmationPage() throws Exception {
        Menu menu = createMenu();

        mvc.perform(post("/site/reservations").param("menuId", String.valueOf(menu.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation/confirmation"))
                .andExpect(model().attributeExists("token"));
    }

    @Test
    void whenSearchReservation_valid_thenShowResult() throws Exception {
        Menu menu = createMenu();
        Reservation reservation = reservationRepository.save(new Reservation(menu));

        mvc.perform(post("/site/reservations/search").param("token", reservation.getToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation/search-result"))
                .andExpect(model().attribute("valid", true));
    }

    @Test
    void whenSearchReservation_invalid_thenShowInvalid() throws Exception {
        mvc.perform(post("/site/reservations/search").param("token", "invalid"))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation/search-result"))
                .andExpect(model().attribute("valid", false));
    }

    @Test
    void whenCancelReservation_thenShowResult() throws Exception {
        Menu menu = createMenu();
        Reservation reservation = reservationRepository.save(new Reservation(menu));

        mvc.perform(post("/site/reservations/cancel").param("token", reservation.getToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation/search-result"))
                .andExpect(model().attribute("cancelled", true));
    }

    @Test
    void whenCheckIn_thenShowSuccess() throws Exception {
        Menu menu = createMenu();
        Reservation reservation = reservationRepository.save(new Reservation(menu));

        mvc.perform(post("/site/reservations/checkin").param("token", reservation.getToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation/checkin-result"))
                .andExpect(model().attribute("success", true));
    }

    @Test
    void whenCheckIn_invalid_thenShowFail() throws Exception {
        mvc.perform(post("/site/reservations/checkin").param("token", "invalid"))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation/checkin-result"))
                .andExpect(model().attribute("success", false));
    }

    private Menu createMenu() {
        Refectory refectory = new Refectory("Moliceiro", "Campus", 5);
        refectory = refectoryRepository.save(refectory);
        return menuRepository.save(new Menu(LocalDate.now(), "Bitoque", MealType.LUNCH, refectory));
    }
}
