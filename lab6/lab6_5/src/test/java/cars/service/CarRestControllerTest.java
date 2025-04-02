package cars.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.flyway.enabled=false"
})
public class CarRestControllerTest {

    @Autowired
    private CarRepository carRepository;

    @LocalServerPort
    private int port;

    @SuppressWarnings({ "deprecation", "resource" })
    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>()
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.datasource.hikari.max-lifetime", () -> "30000");
        registry.add("spring.datasource.hikari.connection-timeout", () -> "10000");
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @AfterEach
    void clearBd() {
        carRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car toyota = new Car("Toyota", "Corolla", "Sedan", "Gasolina");

        given()
                .contentType(ContentType.JSON)
                .body(toyota)
                .when()
                .post("/api/car")
                .then()
                .statusCode(201)
                .body("maker", equalTo("Toyota"))
                .body("model", equalTo("Corolla"));

        assertThat(carRepository.findAll())
                .hasSize(1)
                .first()
                .extracting(Car::getMaker)
                .isEqualTo("Toyota");
    }

    @Test
    void givenCars_whenGetAllCars_thenStatus200() {
        createTestCar("Toyota", "Corolla", "Sedan", "Gasolina");
        createTestCar("Honda", "Civic", "Sedan", "Gasolina");

        given()
                .when()
                .get("/api/cars")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2))
                .body("maker", contains("Toyota", "Honda"));

    }

    @Test
    void givenCars_whenFindReplacementCar_thenStatus200() {
        Car originalCar = createTestCar("Toyota", "Corolla", "Sedan", "Gasolina");
        createTestCar("Honda", "Civic", "Sedan", "Gasolina");
        createTestCar("Ford", "Focus", "Hatchback", "Diesel");

        given()
                .when()
                .get("/api/car/" + originalCar.getCarId() + "/replacement")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(1))
                .body("[0].maker", equalTo("Honda"))
                .body("[0].model", equalTo("Civic"));
    }

    private Car createTestCar(String maker, String model, String segment, String engineType) {
        return given()
                .contentType(ContentType.JSON)
                .body(new Car(maker, model, segment, engineType))
                .when()
                .post("/api/car")
                .then()
                .statusCode(201)
                .extract().as(Car.class);
    }
}