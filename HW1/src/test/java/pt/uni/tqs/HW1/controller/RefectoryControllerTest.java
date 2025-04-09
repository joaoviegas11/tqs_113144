package pt.uni.tqs.HW1.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.service.MenuService;
import pt.uni.tqs.HW1.service.RefectoryService;
import pt.uni.tqs.HW1.utils.MealType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RefectoryController.class)
public class RefectoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RefectoryService refectoryService;

    @MockBean
    private MenuService menuService;

    @Test
    void testGetAllRefectories() throws Exception {
        Refectory r1 = new Refectory("Moliceiro", "Avenida A", 100);
        r1.setId(1L);
        Refectory r2 = new Refectory("Barlavento", "Campus B", 80);
        r2.setId(2L);

        List<Refectory> list = Arrays.asList(r1, r2);

        when(refectoryService.getAllRefectorys()).thenReturn(list);

        mvc.perform(get("/api/refectories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Moliceiro")))
                .andExpect(jsonPath("$[1].name", is("Barlavento")));

        verify(refectoryService, times(1)).getAllRefectorys();
    }

    @Test
    void testGetRefectoryById_valid() throws Exception {
        Refectory r = new Refectory("Moliceiro", "Avenida A", 100);
        r.setId(1L);

        when(refectoryService.getRefectoryById(1L)).thenReturn(r);

        mvc.perform(get("/api/refectories/refectories/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Moliceiro")))
                .andExpect(jsonPath("$.location", is("Avenida A")));

        verify(refectoryService, times(1)).getRefectoryById(1L);
    }

    @Test
    void testGetRefectoryById_notFound() throws Exception {
        when(refectoryService.getRefectoryById(999L)).thenThrow(new IllegalArgumentException());

        mvc.perform(get("/api/refectories/refectories/999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(refectoryService, times(1)).getRefectoryById(999L);
    }

    @Test
    void testGetMenusByRefectory() throws Exception {
        Refectory r = new Refectory("Moliceiro", "Avenida A", 100);
        r.setId(1L);

        Menu m1 = new Menu(LocalDate.now(), "Feijoada", MealType.LUNCH, r);
        Menu m2 = new Menu(LocalDate.now().plusDays(1), "Sopa + Omelete", MealType.DINNER, r);
        List<Menu> menus = Arrays.asList(m1, m2);

        when(menuService.getMenusByRefectory(1L)).thenReturn(menus);

        mvc.perform(get("/api/refectories/1/menus").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].meal", is("Feijoada")))
                .andExpect(jsonPath("$[1].meal", is("Sopa + Omelete")));

        verify(menuService, times(1)).getMenusByRefectory(1L);
    }

 @Test
void testGetMenusForNextSevenDays_limitedToSeven() throws Exception {
    Refectory r = new Refectory("Moliceiro", "Avenida A", 100);
    r.setId(1L);

    // Cria 10 menus com datas consecutivas a partir de hoje
    LocalDate today = LocalDate.now();
    List<Menu> allMenus = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        Menu m = new Menu(today.plusDays(i), "Menu " + i, MealType.LUNCH, r);
        m.setId((long) i + 1);
        allMenus.add(m);
    }

    List<Menu> sevenMenus = allMenus.subList(0, 7);
    when(menuService.getMenusForNextSevenDays(1L)).thenReturn(sevenMenus);

    mvc.perform(get("/api/refectories/1/menus/next7days")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(7)))
            .andExpect(jsonPath("$[0].meal", is("Menu 0")))
            .andExpect(jsonPath("$[6].meal", is("Menu 6")));

    verify(menuService, times(1)).getMenusForNextSevenDays(1L);
}

}