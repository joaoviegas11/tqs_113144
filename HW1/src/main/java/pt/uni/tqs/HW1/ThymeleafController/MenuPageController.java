package pt.uni.tqs.HW1.ThymeleafController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.service.MenuService;
import pt.uni.tqs.HW1.service.RefectoryService;
import pt.uni.tqs.HW1.service.WeatherService;
import pt.uni.tqs.HW1.utils.Weather;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/site/menus")
public class MenuPageController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RefectoryService refectoryService;

    @Autowired
    private WeatherService weatherService;
    
@GetMapping
public String showMenusAndWeather(@RequestParam("refectoryId") Long refectoryId, Model model) {
    Refectory refectory = refectoryService.getRefectoryById(refectoryId);
    List<Menu> menus = menuService.getMenusForNextSevenDays(refectoryId);
    List<Weather> forecast = weatherService.getForecast().stream().limit(5).toList();

    // Map de data -> Weather (para acesso rápido)
    Map<LocalDate, Weather> forecastMap = forecast.stream()
        .collect(Collectors.toMap(Weather::getDate, w -> w));

    // Map<Menu, Weather>: só menus com previsão
    Map<Menu, Weather> menusWithWeather = new LinkedHashMap<>();
    List<Menu> menusWithoutWeather = new ArrayList<>();

    for (Menu menu : menus) {
        Weather weather = forecastMap.get(menu.getDate());
        if (weather != null) {
            menusWithWeather.put(menu, weather);
        } else {
            menusWithoutWeather.add(menu);
        }
    }

    model.addAttribute("refectory", refectory);
    model.addAttribute("menusWithWeather", menusWithWeather);
    model.addAttribute("menusWithoutWeather", menusWithoutWeather);
    return "menu/view";
}


  
}
