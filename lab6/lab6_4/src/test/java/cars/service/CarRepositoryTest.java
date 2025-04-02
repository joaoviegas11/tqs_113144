package cars.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * DataJpaTest limits the test scope to the data access context
 * tries to autoconfigure a test database
 */
@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindByCarId_thenReturnCar() {
        Car car = new Car("Tesla", "Model S");
        entityManager.persistAndFlush(car);

        Car found = carRepository.findByCarId(car.getCarId());

        assertThat(found).isNotNull();
        assertThat(found.getCarId()).isEqualTo(car.getCarId());
        assertThat(found.getMaker()).isEqualTo("Tesla");
        assertThat(found.getModel()).isEqualTo("Model S");
    }

    @Test
    void whenFindByInvalidCarId_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-1L);
        assertThat(fromDb).isNull();
    }

    @Test
    void whenSaveCar_thenReturnCar() {
        Car car = new Car("Ferrari", "F8");
        
        Car savedCar = entityManager.persistFlushFind(car);

        assertThat(savedCar).isNotNull();
        assertThat(savedCar.getMaker()).isEqualTo("Ferrari");
        assertThat(savedCar.getModel()).isEqualTo("F8");
    }

    @Test
    void whenFindCarByExistingId_thenReturnCar() {
        Car car = new Car("BMW", "M3");
        entityManager.persistAndFlush(car);

        Car found = carRepository.findById(car.getCarId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getMaker()).isEqualTo("BMW");
        assertThat(found.getModel()).isEqualTo("M3");
    }

    @Test
    void whenFindCarByInvalidId_thenReturnNull() {
        Car fromDb = carRepository.findById(-999L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car car1 = new Car("Audi", "A4");
        Car car2 = new Car("Porsche", "911");
        Car car3 = new Car("Lamborghini", "Aventador");

        entityManager.persist(car1);
        entityManager.persist(car2);
        entityManager.persist(car3);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3);
        assertThat(allCars)
                .extracting(Car::getMaker)
                .containsExactlyInAnyOrder("Audi", "Porsche", "Lamborghini");
    }

}