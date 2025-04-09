package pt.uni.tqs.HW1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.utils.MealType;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MenuRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void testFindByRefectoryId() {
        Refectory refectory = new Refectory("Moliceiro", "Campus", 10);
        entityManager.persist(refectory);

        Menu menu = new Menu(LocalDate.now(), "Arroz de pato", MealType.LUNCH, refectory);
        entityManager.persist(menu);
        entityManager.flush();

        List<Menu> result = menuRepository.findByRefectoryId(refectory.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMeal()).isEqualTo("Arroz de pato");
    }

    @Test
    void testFindByRefectoryIdAndDateBetween() {
        Refectory refectory = new Refectory("Moliceiro", "Campus", 10);
        entityManager.persist(refectory);

        Menu todayMenu = new Menu(LocalDate.now(), "Sopa", MealType.LUNCH, refectory);
        Menu futureMenu = new Menu(LocalDate.now().plusDays(3), "Bacalhau", MealType.DINNER, refectory);
        entityManager.persist(todayMenu);
        entityManager.persist(futureMenu);
        entityManager.flush();

        List<Menu> result = menuRepository.findByRefectoryIdAndDateBetween(refectory.getId(), LocalDate.now(), LocalDate.now().plusDays(6));

        assertThat(result).hasSize(2);
    }
}
