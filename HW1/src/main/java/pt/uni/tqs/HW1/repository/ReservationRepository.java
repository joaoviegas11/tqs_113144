package pt.uni.tqs.HW1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.uni.tqs.HW1.model.Reservation;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByToken(String token);
}