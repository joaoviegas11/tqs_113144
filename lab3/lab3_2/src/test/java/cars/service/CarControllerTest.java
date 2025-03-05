package cars.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mvcForTests;

    @SuppressWarnings("removal")
    @MockBean
    private CarManagerService service;

    @Test
    void testCreateCar()  throws Exception {
        Car car=new Car("Seat", "Ibiza");
        when(service.save(Mockito.any())).thenReturn(car);
        
        mvcForTests.perform(post("/api/car").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.maker", is(car.getMaker())))
        .andExpect(jsonPath("$.model", is(car.getModel())));
        verify(service,times(1)).save(Mockito.any());
    }

    @Test
    void testGetAllCars() throws Exception {
        Car car1 = new Car("Peugeot", "E-208");
        Car car2 = new Car("Ferrari", "F80");
        Car car3 = new Car("Mercedes", "Benz");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        when( service.getAllCars()).thenReturn(allCars);

        mvcForTests.perform(
                get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(car1.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(car2.getMaker())))
                .andExpect(jsonPath("$[2].maker", is(car3.getMaker())));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void testGetCarById() throws  Exception {
        Car car=new Car("Seat", "Ibiza");
    
            when(service.getCarDetails(Mockito.anyLong())).thenReturn(java.util.Optional.of(car));
    
            mvcForTests.perform(get("/api/car/1").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is(car.getMaker())))
                .andExpect(jsonPath("$.model", is(car.getModel())));
    
            verify(service, times(1)).getCarDetails(Mockito.anyLong());
        
    }
}
