package pt.uni.tqs.HW1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.model.Reservation;
import pt.uni.tqs.HW1.utils.MealType;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void testFindByToken() {
        Refectory refectory = new Refectory("Moliceiro", "Campus", 30);
        entityManager.persist(refectory);

        Menu menu = new Menu(LocalDate.now(), "Feijoada", MealType.LUNCH, refectory);
        entityManager.persist(menu);

        Reservation reservation = new Reservation(menu);
        entityManager.persistAndFlush(reservation);

        Optional<Reservation> result = reservationRepository.findByToken(reservation.getToken());

        assertThat(result).isPresent();
        assertThat(result.get().getMenu().getMeal()).isEqualTo("Feijoada");
    }

    @Test
    void testFindByInvalidToken() {
        Optional<Reservation> result = reservationRepository.findByToken("invalid-token");
        assertThat(result).isNotPresent();
    }
}
