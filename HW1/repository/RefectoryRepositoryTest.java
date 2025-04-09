package pt.uni.tqs.HW1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.uni.tqs.HW1.model.Refectory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RefectoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RefectoryRepository refectoryRepository;

    @Test
    void testSaveAndFindAll() {
        Refectory r1 = new Refectory("Moliceiro", "Campus", 20);
        Refectory r2 = new Refectory("Lagos", "Centro", 15);

        entityManager.persist(r1);
        entityManager.persist(r2);
        entityManager.flush();

        List<Refectory> all = refectoryRepository.findAll();

        assertThat(all).hasSize(2);
        assertThat(all)
                .extracting(Refectory::getName)
                .containsExactlyInAnyOrder("Moliceiro", "Lagos");
    }
}

