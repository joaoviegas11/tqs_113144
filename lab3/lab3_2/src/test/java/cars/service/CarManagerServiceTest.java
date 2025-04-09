package cars.service;

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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CarManagerServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {

        // these expectations provide an alternative to the use of the repository
        Car car1 = new Car("Peugeot", "E-208");
        car1.setCarId(999L);
        Car car2 = new Car("Ferrari", "F80");
        Car car3 = new Car("Mercedes", "Benz");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        Mockito.when(carRepository.findByCarId(999L)).thenReturn(car1);
        Mockito.when(carRepository.findByCarId(car2.getCarId())).thenReturn(car2);
        Mockito.when(carRepository.findByCarId(car3.getCarId())).thenReturn(car3);
        Mockito.when(carRepository.findByCarId(-998L)).thenReturn(null);
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
    }

    @Test
    void whenSearchValidID_thenCarShouldBeFound() {

        Optional<Car> searchResult = carManagerService.getCarDetails(999L);
        assertThat(searchResult.isPresent()).isTrue();
        assertThat(searchResult.get().getCarId()).isEqualTo(999L);
    }

    @Test
    void whenSearchInvalidName_thenCarShouldNotBeFound() {
        Optional<Car> car = carManagerService.getCarDetails(-998L);
        assertThat(car).isEmpty();

        verifyFindByCarIdIsCalledOnce(-998L);
    }

    @Test
    void given3Cars_whengetAll_thenReturn3Records() {
        Car car1 = new Car("Peugeot", "E-208");
        Car car2 = new Car("Ferrari", "F80");
        Car car3 = new Car("Mercedes", "Benz");

        List<Car> allCars = carManagerService.getAllCars();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).contains(car1.getMaker(), car2.getMaker(),
                car3.getMaker());
        verifyFindAllCarsIsCalledOnce();
    }

    @Test
    void whenSaveCar_thenCarShouldBeSaved() {
        Car car = new Car("Ford", "GEN-E");
        car.setCarId(1002L);

        when(carRepository.save(Mockito.any(Car.class))).thenReturn(car);

        Car savedCar = carManagerService.save(car);
        assertThat(savedCar).isNotNull();
        assertThat(savedCar.getMaker()).isEqualTo(car.getMaker());
        assertThat(savedCar.getModel()).isEqualTo(car.getModel());

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).save(car);
    }

    private void verifyFindByCarIdIsCalledOnce(Long carId) {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(carId);
    }

    private void verifyFindAllCarsIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
