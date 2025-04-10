package pt.uni.tqs.HW1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.uni.tqs.HW1.model.Reservation;
import pt.uni.tqs.HW1.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Create a new reservation (returns token)
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestParam Long menuId) {
        try {
            Reservation reservation = reservationService.createReservation(menuId);
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Menu not found.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body("There are no more seats available for this menu.");
        }
    }
    

    // Get reservation by token
    @GetMapping("/{token}")
    public ResponseEntity<?> getReservation(@PathVariable String token) {
        return reservationService.isReservationValid(token)
                ? ResponseEntity.ok("Reservation is valid.")
                : ResponseEntity.status(404).body("Reservation not found or already used.");
    }

    // Cancel reservation by token
    @DeleteMapping("/{token}")
    public ResponseEntity<?> cancelReservation(@PathVariable String token) {
        boolean canceled = reservationService.cancelReservation(token);
        return canceled
                ? ResponseEntity.ok("Reservation has been cancelled.")
                : ResponseEntity.status(404).body("Reservation not found.");
    }

    // Check-in (mark reservation as used)
    @PostMapping("/{token}/checkin")
    public ResponseEntity<?> checkIn(@PathVariable String token) {
        boolean checkedIn = reservationService.checkIn(token);
        return checkedIn
                ? ResponseEntity.ok("Reservation successfully checked in.")
                : ResponseEntity.badRequest().body("Reservation is invalid or already used.");
    }
    
}

