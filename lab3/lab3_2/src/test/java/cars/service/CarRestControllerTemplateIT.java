package cars.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureTestDatabase
@TestPropertySource(locations = "resources/application-integrationtest.properties")
class CarRestControllerTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car toyota = new Car("Toyota", "Corolla", "Sedan", "Gasolina");
        restTemplate.postForEntity("/api/car", toyota, Car.class);

        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getMaker).containsOnly("Toyota");
    }

    @Test
    void givenCars_whenGetAllCars_thenStatus200() {
        createTestCar("Toyota", "Corolla", "Sedan", "Gasolina");
        createTestCar("Honda", "Civic", "Sedan", "Gasolina");

        ResponseEntity<List<Car>> response = restTemplate.exchange(
                "/api/cars", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("Toyota", "Honda");
    }

    @Test
    void givenCars_whenFindReplacementCar_thenStatus200() {
        Car originalCar = createTestCar("Toyota", "Corolla", "Sedan", "Gasolina");
        createTestCar("Honda", "Civic", "Sedan", "Gasolina"); // Deve ser retornado como substituto
        createTestCar("Ford", "Focus", "Hatchback", "Diesel"); // Não deve ser retornado

        ResponseEntity<List<Car>> response = restTemplate.exchange("/api/car/" + originalCar.getCarId()
                + "/replacement", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()).extracting(Car::getMaker).containsOnly("Honda");
    }

    private Car createTestCar(String maker, String model, String segment, String engineType) {
        Car car = new Car(maker, model, segment, engineType);
        return repository.saveAndFlush(car);
    }
}
