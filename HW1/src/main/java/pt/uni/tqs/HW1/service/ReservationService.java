package pt.uni.tqs.HW1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Reservation;
import pt.uni.tqs.HW1.repository.MenuRepository;
import pt.uni.tqs.HW1.repository.ReservationRepository;
import pt.uni.tqs.HW1.utils.TypeResevetion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MenuRepository menuRepository;

    public Reservation createReservation(Long menuId) {
        logger.info("Creating reservation for menu ID: {}", menuId);
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> {
                    logger.error("Menu with ID {} not found.", menuId);
                    return new IllegalArgumentException("Menu not found.");
                });

        int availableSeats = menu.getRefectory().getAvailableSeats();
        int occupiedSeats = menu.getOccupiedSeats();

        logger.debug("Available seats: {}, Occupied seats: {}", availableSeats, occupiedSeats);

        if (occupiedSeats >= availableSeats) {
            logger.warn("No seats available for menu ID: {}", menuId);
            throw new IllegalStateException("There are no more seats available for this menu.");
        }

        Reservation reservation = new Reservation(menu);
        reservationRepository.save(reservation);

        menu.setOccupiedSeats(occupiedSeats + 1);
        menuRepository.save(menu);

        logger.info("Reservation created successfully with token: {}", reservation.getToken());
        return reservation;
    }

    public boolean isReservationValid(String token) {
        logger.info("Validating reservation with token: {}", token);
        Optional<Reservation> reservation = reservationRepository.findByToken(token);
        boolean isValid = reservation.isPresent() && reservation.get().getUsed().equals(TypeResevetion.ACTIVE);
        logger.debug("Is reservation valid? {}", isValid);
        return isValid;
    }

    public boolean checkIn(String token) {
        logger.info("Checking in reservation with token: {}", token);
        Optional<Reservation> reservationOpt = reservationRepository.findByToken(token);
        if (reservationOpt.isPresent() && reservationOpt.get().getUsed().equals(TypeResevetion.ACTIVE)) {
            Reservation reservation = reservationOpt.get();
            reservation.setUsed(TypeResevetion.USED);
            reservationRepository.save(reservation);
            logger.info("Check-in successful for token: {}", token);
            return true;
        }
        logger.warn("Check-in failed: reservation not found or not active for token: {}", token);
        return false;
    }

    public boolean cancelReservation(String token) {
        logger.info("Cancelling reservation with token: {}", token);
        Optional<Reservation> reservationOpt = reservationRepository.findByToken(token);
        if (reservationOpt.isPresent() && reservationOpt.get().getUsed().equals(TypeResevetion.ACTIVE)) {
            Reservation reservation = reservationOpt.get();
            reservation.setUsed(TypeResevetion.DEACTIVATED);
            reservationRepository.save(reservation);

            Menu menu = reservation.getMenu();
            menu.setOccupiedSeats(Math.max(0, menu.getOccupiedSeats() - 1));
            menuRepository.save(menu);

            logger.info("Reservation successfully cancelled for token: {}", token);
            return true;
        }
        logger.warn("Cancellation failed: reservation not found or not active for token: {}", token);
        return false;
    }

    public Optional<Reservation> getReservationByToken(String token) {
        logger.info("Retrieving reservation by token: {}", token);
        return reservationRepository.findByToken(token);
    }
}