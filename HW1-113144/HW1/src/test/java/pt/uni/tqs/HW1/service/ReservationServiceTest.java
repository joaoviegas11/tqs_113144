package pt.uni.tqs.HW1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.model.Reservation;
import pt.uni.tqs.HW1.repository.MenuRepository;
import pt.uni.tqs.HW1.repository.ReservationRepository;
import pt.uni.tqs.HW1.utils.MealType;
import pt.uni.tqs.HW1.utils.TypeResevetion;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Menu menuAvailable;
    private Menu menuFull;
    private Reservation activeReservation;
    private Reservation usedReservation;

    @BeforeEach
    public void setUp() {
        Refectory refectory = new Refectory("Moliceiro", "Avenida A", 10);
        refectory.setId(1L);

        menuAvailable = new Menu(LocalDate.now(), "Arroz de frango", MealType.LUNCH, refectory);
        menuAvailable.setId(100L);
        menuAvailable.setOccupiedSeats(5);

        menuFull = new Menu(LocalDate.now(), "Feijoada", MealType.DINNER, refectory);
        menuFull.setId(200L);
        menuFull.setOccupiedSeats(10);

        activeReservation = new Reservation(menuAvailable);
        activeReservation.setUsed(TypeResevetion.ACTIVE);

        usedReservation = new Reservation(menuAvailable);
        usedReservation.setUsed(TypeResevetion.USED);

        Mockito.when(menuRepository.findById(100L)).thenReturn(Optional.of(menuAvailable));
        Mockito.when(menuRepository.findById(200L)).thenReturn(Optional.of(menuFull));

        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(reservationRepository.findByToken(activeReservation.getToken()))
                .thenReturn(Optional.of(activeReservation));

        Mockito.when(reservationRepository.findByToken("invalid_token"))
                .thenReturn(Optional.empty());

        Mockito.when(reservationRepository.findByToken(usedReservation.getToken()))
                .thenReturn(Optional.of(usedReservation));
                
        Mockito.when(menuRepository.findById(999L)).thenReturn(Optional.empty());

    }

    @Test
    void whenCreateReservationWithAvailableSeats_thenReservationIsCreated() {
        Reservation reservation = reservationService.createReservation(100L);

        assertThat(reservation).isNotNull();
        assertThat(reservation.getMenu()).isEqualTo(menuAvailable);

        Mockito.verify(menuRepository, VerificationModeFactory.times(1)).save(menuAvailable);
        Mockito.verify(reservationRepository, VerificationModeFactory.times(1)).save(reservation);
    }

    @Test
    void whenCreateReservationWithFullCapacity_thenThrowException() {
        Exception exception = assertThrows(IllegalStateException.class,
                () -> reservationService.createReservation(200L));

        assertThat(exception.getMessage()).isEqualTo("There are no more seats available for this menu.");
    }

    @Test
    void whenValidTokenAndActive_thenCheckInReturnsTrue() {
        boolean result = reservationService.checkIn(activeReservation.getToken());

        assertThat(result).isTrue();
        assertThat(activeReservation.getUsed()).isEqualTo(TypeResevetion.USED);

        Mockito.verify(reservationRepository, VerificationModeFactory.times(1)).save(activeReservation);
    }

    @Test
    void whenInvalidToken_thenCheckInReturnsFalse() {
        boolean result = reservationService.checkIn("invalid_token");

        assertThat(result).isFalse();
    }

    @Test
    void whenValidActiveToken_thenCancelReservationAndDecrementSeats() {
        menuAvailable.setOccupiedSeats(5);
        boolean result = reservationService.cancelReservation(activeReservation.getToken());

        assertThat(result).isTrue();
        assertThat(activeReservation.getUsed()).isEqualTo(TypeResevetion.DEACTIVATED);
        assertThat(menuAvailable.getOccupiedSeats()).isEqualTo(4);

        Mockito.verify(reservationRepository).save(activeReservation);
        Mockito.verify(menuRepository).save(menuAvailable);
    }

    @Test
    void whenInvalidToken_thenCancelReservationReturnsFalse() {
        boolean result = reservationService.cancelReservation("invalid_token");

        assertThat(result).isFalse();
    }

    @Test
    void whenGetReservationByToken_thenReturnOptionalReservation() {
        Optional<Reservation> result = reservationService.getReservationByToken(activeReservation.getToken());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(activeReservation);
    }

    @Test
    void whenReservationNotActive_thenIsReservationValidReturnsFalse() {
        usedReservation.setUsed(TypeResevetion.USED);

        boolean result = reservationService.isReservationValid(usedReservation.getToken());

        assertThat(result).isFalse();
    }

    @Test
    void whenUsedReservation_thenCheckInReturnsFalse() {
        usedReservation.setUsed(TypeResevetion.USED);

        boolean result = reservationService.checkIn(usedReservation.getToken());

        assertThat(result).isFalse();
    }

    @Test
    void whenUsedReservation_thenCancelReservationReturnsFalse() {
        usedReservation.setUsed(TypeResevetion.USED);

        boolean result = reservationService.cancelReservation(usedReservation.getToken());

        assertThat(result).isFalse();
    }

    @Test
    void whenMenuDoesNotExist_thenThrowIllegalArgumentException() {

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.createReservation(999L));

        assertThat(exception.getMessage()).isEqualTo("Menu not found.");
    }

}
