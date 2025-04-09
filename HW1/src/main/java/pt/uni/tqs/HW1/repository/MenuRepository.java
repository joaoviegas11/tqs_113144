// src/main/java/pt/uni/tqs/HW1/repository/MenuRepository.java
package pt.uni.tqs.HW1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.uni.tqs.HW1.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRefectoryId(Long refectoryId);
     List<Menu> findByRefectoryIdAndDateBetween(Long refectoryId, LocalDate startDate, LocalDate endDate);
}
