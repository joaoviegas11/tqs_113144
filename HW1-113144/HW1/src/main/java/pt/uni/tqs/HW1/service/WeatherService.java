package pt.uni.tqs.HW1.service;

import java.util.ArrayList;
import java.util.List;


import pt.uni.tqs.HW1.api.WeatherApi;
import pt.uni.tqs.HW1.utils.Cache;
import pt.uni.tqs.HW1.utils.Weather;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final WeatherApi weatherApi = new WeatherApi();
    private final Cache<List<Weather>> weatherCache = new Cache<>(12 * 60 * 60 * 1000);
    private int totalRequests = 0;
    private int cacheHits = 0;
    private int cacheMisses = 0;

    public List<Weather> getForecast() {
        totalRequests++;
        List<Weather> forecasts = new ArrayList<>();

        if (weatherCache.isValid()) {
            cacheHits++;
            logger.info("Cache hit for weather forecast.");
            return weatherCache.get();
        }

        cacheMisses++;
        logger.info("Cache miss. Fetching new weather data from OpenWeatherMap API.");
        
        forecasts = weatherApi.fetchWeatherData();
        if (forecasts != null) {
            weatherCache.put(forecasts);
            logger.info("Weather forecast data successfully parsed and cached.");
        } else {
            logger.error("Failed to retrieve weather data.");
            throw new RuntimeException("Failed to retrieve weather data.");
        }

        return forecasts;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public int getCacheHits() {
        return cacheHits;
    }

    public int getCacheMisses() {
        return cacheMisses;
    }
}