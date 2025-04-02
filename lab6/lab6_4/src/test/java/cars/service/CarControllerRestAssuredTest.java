package cars.service;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebMvcTest(CarController.class)
public class CarControllerRestAssuredTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void testCreateCar() throws IOException {
        Car car = new Car("Seat", "Ibiza");

        Mockito.when(service.save(Mockito.any())).thenReturn(car);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(JsonUtils.toJson(car))
                .when()
                .post("/api/car")
                .then()
                .statusCode(201)
                .body("maker", Matchers.equalTo("Seat"))
                .body("model", Matchers.equalTo("Ibiza"));

        Mockito.verify(service, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testGetAllCars() {
        List<Car> allCars = List.of(
                new Car("Peugeot", "E-208"),
                new Car("Ferrari", "F80"),
                new Car("Mercedes", "Benz"));

        Mockito.when(service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc
                .when()
                .get("/api/cars")
                .then()
                .statusCode(200)
                .body("", Matchers.hasSize(3))
                .body("[0].maker", Matchers.equalTo("Peugeot"))
                .body("[1].maker", Matchers.equalTo("Ferrari"))
                .body("[2].maker", Matchers.equalTo("Mercedes"));

        Mockito.verify(service, Mockito.times(1)).getAllCars();
    }

    @Test
    void testGetCarById() {
        Car car = new Car("Seat", "Ibiza");

        Mockito.when(service.getCarDetails(Mockito.anyLong())).thenReturn(Optional.of(car));

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .when()
                .get("/api/car/1")
                .then()
                .statusCode(200)
                .body("maker", Matchers.equalTo("Seat"))
                .body("model", Matchers.equalTo("Ibiza"));

        Mockito.verify(service, Mockito.times(1)).getCarDetails(Mockito.anyLong());
    }
}
