package pt.uni.tqs.HW1.ThymeleafController;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.repository.MenuRepository;
import pt.uni.tqs.HW1.repository.RefectoryRepository;
import pt.uni.tqs.HW1.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class RefectoryPageControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RefectoryRepository refectoryRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @AfterEach
    void cleanUp() {
        reservationRepository.deleteAll();
        menuRepository.deleteAll();
        refectoryRepository.deleteAll();
    }

    @Test
    void whenChooseRefectory_thenRenderViewWithExpectedRefectories() throws Exception {
        //eu não sei de onde vem 4 canctinas
        //Isto a testar com todos fociona normaente sozinho dá erro 
        //Por isso meti estas linhas
        reservationRepository.deleteAll();
        menuRepository.deleteAll();
        refectoryRepository.deleteAll();

        Refectory r1 = new Refectory("Moliceiro", "Campus A", 100);
        Refectory r2 = new Refectory("Barlavento", "Campus B", 80);
        refectoryRepository.saveAll(List.of(r1, r2));

        mvc.perform(get("/site/refectories"))
                .andExpect(status().isOk())
                .andExpect(view().name("refectory/choose"))
                .andExpect(model().attributeExists("refectories"))
                .andExpect(model().attribute("refectories", hasSize(2)))
                .andExpect(model().attribute("refectories", hasItem(
                        allOf(
                            hasProperty("name", is("Moliceiro")),
                            hasProperty("location", is("Campus A")),
                            hasProperty("availableSeats", is(100))
                        )
                )))
                .andExpect(model().attribute("refectories", hasItem(
                        allOf(
                            hasProperty("name", is("Barlavento")),
                            hasProperty("location", is("Campus B")),
                            hasProperty("availableSeats", is(80))
                        )
                )));
    }
}
