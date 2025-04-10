package pt.uni.tqs.HW1.service;

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
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.repository.MenuRepository;
import pt.uni.tqs.HW1.utils.MealType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    private Refectory refectory;

    private Menu menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8;

    @BeforeEach
    public void setUp() {
        refectory = new Refectory("Moliceiro Refectory", "avenida A", 120);
        refectory.setId(999L);

        LocalDate today = LocalDate.now();

        menu1 = new Menu(today, "Arroz de frango", MealType.LUNCH, refectory);
        menu1.setId(1L);
        menu2 = new Menu(today.plusDays(1), "Sopa + omelete", MealType.DINNER, refectory);
        menu2.setId(2L);
        menu3 = new Menu(today.plusDays(2), "Arroz de frango", MealType.LUNCH, refectory);
        menu3.setId(3L);
        menu4 = new Menu(today.plusDays(3), "Sopa + omelete", MealType.DINNER, refectory);
        menu4.setId(4L);
        menu5 = new Menu(today.plusDays(4), "Arroz de frango", MealType.LUNCH, refectory);
        menu5.setId(5L);
        menu6 = new Menu(today.plusDays(5), "Sopa + omelete", MealType.DINNER, refectory);
        menu6.setId(6L);
        menu7 = new Menu(today.plusDays(6), "Arroz de frango", MealType.LUNCH, refectory);
        menu7.setId(7L);
        menu8 = new Menu(today.plusDays(7), "Sopa + omelete", MealType.DINNER, refectory);
        menu8.setId(8L); // fora do intervalo de 7 dias

        List<Menu> allMenus = Arrays.asList(menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8);
        List<Menu> sevenDayMenus = Arrays.asList(menu1, menu2, menu3, menu4, menu5, menu6, menu7);

        when(menuRepository.findByRefectoryId(999L)).thenReturn(allMenus);
        when(menuRepository.findByRefectoryIdAndDateBetween(
                Mockito.eq(999L),
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class)
        )).thenReturn(sevenDayMenus);
    }

    @Test
    void whenValidRefectoryId_thenReturnAllMenus() {
        List<Menu> result = menuService.getMenusByRefectory(999L);

        assertThat(result).hasSize(8).extracting(Menu::getMeal)
                .containsExactly(
                        "Arroz de frango", "Sopa + omelete",
                        "Arroz de frango", "Sopa + omelete",
                        "Arroz de frango", "Sopa + omelete",
                        "Arroz de frango", "Sopa + omelete"
                );

        verifyFindByRefectoryIdCalledOnce(999L);
    }

    @Test
    void whenGetMenusForNextSevenDays_thenReturnOnlySevenMenus() {
        List<Menu> result = menuService.getMenusForNextSevenDays(999L);

        assertThat(result).hasSize(7).extracting(Menu::getMeal)
                .containsExactly(
                        "Arroz de frango", "Sopa + omelete",
                        "Arroz de frango", "Sopa + omelete",
                        "Arroz de frango", "Sopa + omelete",
                        "Arroz de frango"
                );

        verifyFindByRefectoryIdAndDateBetweenCalledOnce();
    }

    private void verifyFindByRefectoryIdCalledOnce(Long refectoryId) {
        Mockito.verify(menuRepository, VerificationModeFactory.times(1)).findByRefectoryId(refectoryId);
    }

    private void verifyFindByRefectoryIdAndDateBetweenCalledOnce() {
        Mockito.verify(menuRepository, VerificationModeFactory.times(1))
                .findByRefectoryIdAndDateBetween(
                        Mockito.eq(999L),
                        Mockito.any(LocalDate.class),
                        Mockito.any(LocalDate.class));
    }
}
