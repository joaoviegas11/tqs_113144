package pt.uni.tqs.HW1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.uni.tqs.HW1.service.WeatherService;
import pt.uni.tqs.HW1.utils.Weather;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<List<Weather>> getForecast() {
        List<Weather> forecasts = weatherService.getForecast();
        return ResponseEntity.ok(forecasts);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getWeatherStats() {
        return ResponseEntity.ok(
                Map.of("totalRequests", weatherService.getTotalRequests(),"cacheHits", weatherService.getCacheHits(),"cacheMisses", weatherService.getCacheMisses()));
    }

}
