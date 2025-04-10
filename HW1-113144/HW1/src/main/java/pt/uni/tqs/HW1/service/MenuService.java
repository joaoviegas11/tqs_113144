package pt.uni.tqs.HW1.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getMenusByRefectory(Long refectoryId) {
        logger.info("Fetching menus for refectory with ID: {}", refectoryId);
        List<Menu> menus = menuRepository.findByRefectoryId(refectoryId);
        logger.debug("Retrieved menus: {}", menus);
        return menus;
    }

    public List<Menu> getMenusForNextSevenDays(Long refectoryId) {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromNow = today.plusDays(6);
        logger.info("Fetching menus from {} to {} for refectory with ID: {}", today, sevenDaysFromNow, refectoryId);
        List<Menu> menus = menuRepository.findByRefectoryIdAndDateBetween(refectoryId, today, sevenDaysFromNow);
        menus.sort(Comparator.comparing(Menu::getDate).thenComparing(Menu::getMealType));

        logger.debug("Menus retrieved for the next 7 days: {}", menus);
        return menus;
    }
}