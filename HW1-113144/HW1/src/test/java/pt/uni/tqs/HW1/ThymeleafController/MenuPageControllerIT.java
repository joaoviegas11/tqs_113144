package pt.uni.tqs.HW1.ThymeleafController;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.repository.MenuRepository;
import pt.uni.tqs.HW1.repository.RefectoryRepository;
import pt.uni.tqs.HW1.repository.ReservationRepository;
import pt.uni.tqs.HW1.utils.MealType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class MenuPageControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RefectoryRepository refectoryRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        menuRepository.deleteAll();
        refectoryRepository.deleteAll();
    }

    @Test
    void whenShowMenusAndWeather_thenRenderMenusWithWeatherForecast() throws Exception {
        Refectory ref = new Refectory("Moliceiro", "Campus Central", 20);
        ref = refectoryRepository.save(ref);

        List<Menu> menus = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            menus.add(new Menu(LocalDate.now().plusDays(i), "Menu " + i, MealType.LUNCH, ref));
        }
        menuRepository.saveAll(menus);

        mvc.perform(get("/site/menus")
                        .param("refectoryId", ref.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("menu/view"))
                .andExpect(model().attributeExists("refectory"))
                .andExpect(model().attributeExists("menusWithWeather"))
                .andExpect(model().attribute("menusWithWeather", aMapWithSize(5)))
                .andExpect(model().attributeExists("menusWithoutWeather"))
                .andExpect(model().attribute("menusWithoutWeather", hasSize(2)));
    }
}
