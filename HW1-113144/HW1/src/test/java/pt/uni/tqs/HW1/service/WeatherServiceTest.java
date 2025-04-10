package pt.uni.tqs.HW1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.uni.tqs.HW1.api.WeatherApi;
import pt.uni.tqs.HW1.utils.Weather;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    private WeatherService weatherService;
    private WeatherApi mockApi;

    private List<Weather> sampleForecasts;

    @BeforeEach
    void setUp() throws Exception {
        mockApi = mock(WeatherApi.class);

        Weather forecast = new Weather(java.time.LocalDate.now(), "clear sky", 10, 20, 5);
        sampleForecasts = Arrays.asList(forecast);

        when(mockApi.fetchWeatherData()).thenReturn(sampleForecasts);

        weatherService = new WeatherService();

        Field apiField = WeatherService.class.getDeclaredField("weatherApi");
        apiField.setAccessible(true);
        apiField.set(weatherService, mockApi);
    }

    @Test
    void whenFirstRequest_thenCacheMissAndDataFetched() {
        List<Weather> result = weatherService.getForecast();

        assertThat(result).isEqualTo(sampleForecasts);
        assertThat(weatherService.getCacheMisses()).isEqualTo(1);
        assertThat(weatherService.getCacheHits()).isEqualTo(0);
        assertThat(weatherService.getTotalRequests()).isEqualTo(1);

        verify(mockApi, times(1)).fetchWeatherData();
    }

    @Test
    void whenSecondRequest_thenCacheHitAndApiNotCalledAgain() {
        weatherService.getForecast();
        weatherService.getForecast();

        assertThat(weatherService.getCacheHits()).isEqualTo(1);
        assertThat(weatherService.getCacheMisses()).isEqualTo(1);
        assertThat(weatherService.getTotalRequests()).isEqualTo(2);

        verify(mockApi, times(1)).fetchWeatherData();
    }

    @Test
    void whenCacheExpires_thenApiIsCalledAgain() throws Exception {
        weatherService.getForecast();

        Field cacheField = WeatherService.class.getDeclaredField("weatherCache");
        cacheField.setAccessible(true);

        Object cache = cacheField.get(weatherService);
        Field timestampField = cache.getClass().getDeclaredField("timestamp");
        timestampField.setAccessible(true);
        timestampField.set(cache, System.currentTimeMillis() - (13 * 60 * 60 * 1000));

        weatherService.getForecast();

        assertThat(weatherService.getCacheMisses()).isEqualTo(2);
        assertThat(weatherService.getCacheHits()).isEqualTo(0);
        assertThat(weatherService.getTotalRequests()).isEqualTo(2);

        verify(mockApi, times(2)).fetchWeatherData();
    }

    @Test
    void whenApiReturnsNull_thenThrowException() {
        when(mockApi.fetchWeatherData()).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            weatherService.getForecast();
        });

        assertThat(exception.getMessage()).isEqualTo("Failed to retrieve weather data.");
        assertThat(weatherService.getTotalRequests()).isEqualTo(1);
        assertThat(weatherService.getCacheMisses()).isEqualTo(1);
        assertThat(weatherService.getCacheHits()).isEqualTo(0);

        verify(mockApi, times(1)).fetchWeatherData();
    }

}
