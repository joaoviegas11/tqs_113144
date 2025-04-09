package pt.uni.tqs.HW1.ThymeleafController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.uni.tqs.HW1.model.Reservation;
import pt.uni.tqs.HW1.service.ReservationService;
import pt.uni.tqs.HW1.utils.TypeResevetion;

@Controller
@RequestMapping("/site/reservations")
public class ReservationPageController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public String createReservation(@RequestParam Long menuId, Model model) {
        try {
            Reservation reservation = reservationService.createReservation(menuId);
            model.addAttribute("token", reservation.getToken());
            return "reservation/confirmation";
        } catch (IllegalStateException e) {
            model.addAttribute("error", "Este menu já não tem vagas.");
            return "reservation/error";
        }
    }

    // Página para formulário de consulta de reserva
    @GetMapping("/search")
    public String showSearchForm() {
        return "reservation/search";
    }

    // Resultado da pesquisa
    @PostMapping("/search")
    public String searchReservation(@RequestParam String token, Model model) {
        Optional<Reservation> reservation = reservationService.getReservationByToken(token);
    
        if (reservation.isPresent() && reservation.get().getUsed().equals(TypeResevetion.ACTIVE)) {
            model.addAttribute("valid", true);
            model.addAttribute("reservation", reservation.get());
        } else {
            model.addAttribute("valid", false);
        }
    
        return "reservation/search-result";
    }
    

    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam String token, Model model) {
        boolean cancelled = reservationService.cancelReservation(token);
        model.addAttribute("token", token);
        model.addAttribute("valid", false); // já não é válida
        model.addAttribute("cancelled", cancelled);
        return "reservation/search-result";
    }

    // Página de check-in
    @GetMapping("/checkin")
    public String showCheckInForm() {
        return "reservation/checkin";
    }

    // Submeter token para check-in
    @PostMapping("/checkin")
    public String processCheckIn(@RequestParam String token, Model model) {
        boolean checkedIn = reservationService.checkIn(token);
        model.addAttribute("token", token);
        model.addAttribute("success", checkedIn);
        return "reservation/checkin-result";
    }
}
