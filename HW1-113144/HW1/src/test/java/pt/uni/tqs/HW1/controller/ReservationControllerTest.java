package pt.uni.tqs.HW1.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.model.Reservation;
import pt.uni.tqs.HW1.service.ReservationService;
import pt.uni.tqs.HW1.utils.MealType;
import pt.uni.tqs.HW1.utils.TypeResevetion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void testCreateReservation_success() throws Exception {
        Refectory refectory = new Refectory("Moliceiro", "Avenida", 100);
        Menu menu = new Menu(LocalDate.now(), "Feijoada", MealType.LUNCH, refectory);
        menu.setId(1L);
        Reservation reservation = new Reservation(menu);
        reservation.setUsed(TypeResevetion.ACTIVE);

        when(reservationService.createReservation(1L)).thenReturn(reservation);

        mvc.perform(post("/api/reservations?menuId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(reservation.getToken())));

        verify(reservationService, times(1)).createReservation(1L);
    }

    @Test
    void testCreateReservation_menuNotFound() throws Exception {
        when(reservationService.createReservation(999L)).thenThrow(new IllegalArgumentException());

        mvc.perform(post("/api/reservations?menuId=999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Menu not found."));
    }

    @Test
    void testGetReservation_valid() throws Exception {
        when(reservationService.isReservationValid("abc")).thenReturn(true);

        mvc.perform(get("/api/reservations/abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation is valid."));
    }

    @Test
    void testGetReservation_invalid() throws Exception {
        when(reservationService.isReservationValid("invalid")).thenReturn(false);

        mvc.perform(get("/api/reservations/invalid"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reservation not found or already used."));
    }

    @Test
    void testCancelReservation_success() throws Exception {
        when(reservationService.cancelReservation("abc")).thenReturn(true);

        mvc.perform(delete("/api/reservations/abc"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation has been cancelled."));
    }

    @Test
    void testCancelReservation_notFound() throws Exception {
        when(reservationService.cancelReservation("invalid")).thenReturn(false);

        mvc.perform(delete("/api/reservations/invalid"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Reservation not found."));
    }

    @Test
    void testCheckIn_success() throws Exception {
        when(reservationService.checkIn("abc")).thenReturn(true);

        mvc.perform(post("/api/reservations/abc/checkin"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation successfully checked in."));
    }

    @Test
    void testCheckIn_fail() throws Exception {
        when(reservationService.checkIn("used")).thenReturn(false);

        mvc.perform(post("/api/reservations/used/checkin"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Reservation is invalid or already used."));
    }
}
