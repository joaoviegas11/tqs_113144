package cars.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CarRestControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() throws Exception {
        Car toyota = new Car("Toyota", "Corolla", "Sedan", "Gasolina");
        mvc.perform(post("/api/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(toyota)));

        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getMaker).containsOnly("Toyota");
    }

    @Test
    void givenCars_whenFindReplacementCar_thenStatus200() throws Exception {
        Car originalCar = createTestCar("Toyota", "Corolla", "Sedan", "Gasolina");
        createTestCar("Honda", "Civic", "Sedan", "Gasolina"); 
        createTestCar("Ford", "Focus", "Hatchback", "Diesel"); 

        mvc.perform(get("/api/car/" + originalCar.getCarId() + "/replacement")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].maker", is("Honda")))
                .andExpect(jsonPath("$[0].model", is("Civic")));
    }

    private Car createTestCar(String maker, String model, String segment, String engineType) {
        Car car = new Car(maker, model, segment, engineType);
        return repository.saveAndFlush(car);
    }
}
