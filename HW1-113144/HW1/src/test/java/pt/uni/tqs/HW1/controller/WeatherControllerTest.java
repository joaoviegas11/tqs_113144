package pt.uni.tqs.HW1.controller;

import org.junit.jupiter.api.Test;
import pt.uni.tqs.HW1.service.WeatherService;
import pt.uni.tqs.HW1.utils.Weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void testGetForecast() throws Exception {
        Weather w1 = new Weather(LocalDate.now(), "clear sky", 12.0, 20.0, 4.5);
        Weather w2 = new Weather(LocalDate.now().plusDays(1), "light rain", 10.0, 18.0, 6.0);
        List<Weather> forecastList = Arrays.asList(w1, w2);

        when(weatherService.getForecast()).thenReturn(forecastList);

        mvc.perform(get("/api/weather").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description", is("clear sky")))
                .andExpect(jsonPath("$[0].minTemp", is(12.0)))
                .andExpect(jsonPath("$[0].maxTemp", is(20.0)))
                .andExpect(jsonPath("$[0].windSpeed", is(4.5)))
                .andExpect(jsonPath("$[1].description", is("light rain")))
                .andExpect(jsonPath("$[1].minTemp", is(10.0)))
                .andExpect(jsonPath("$[1].maxTemp", is(18.0)))
                .andExpect(jsonPath("$[1].windSpeed", is(6.0)));

        verify(weatherService, times(1)).getForecast();
    }

    @Test
    void testGetWeatherStats() throws Exception {
        when(weatherService.getTotalRequests()).thenReturn(5);
        when(weatherService.getCacheHits()).thenReturn(3);
        when(weatherService.getCacheMisses()).thenReturn(2);

        mvc.perform(get("/api/weather/stats").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRequests", is(5)))
                .andExpect(jsonPath("$.cacheHits", is(3)))
                .andExpect(jsonPath("$.cacheMisses", is(2)));

        verify(weatherService, times(1)).getTotalRequests();
        verify(weatherService, times(1)).getCacheHits();
        verify(weatherService, times(1)).getCacheMisses();
    }
}
