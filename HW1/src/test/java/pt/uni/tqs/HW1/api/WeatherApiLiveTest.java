package pt.uni.tqs.HW1.api;

import org.junit.jupiter.api.Test;
import pt.uni.tqs.HW1.utils.Weather;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherApiLiveTest {

    @Test
    void whenCallingRealApi_thenReceiveAtLeastFiveValidForecasts() {
        WeatherApi weatherApi = new WeatherApi();

        List<Weather> forecasts = weatherApi.fetchWeatherData();

        assertThat(forecasts).isNotNull();
        assertThat(forecasts).hasSize(5);

        for (int i = 0; i < 5; i++) {
            Weather w = forecasts.get(i);
            assertThat(w.getDate()).isNotNull();
            assertThat(w.getDescription()).isNotBlank();
            assertThat(w.getMinTemp()).isGreaterThan(-50); 
            assertThat(w.getMaxTemp()).isLessThan(60);
            assertThat(w.getWindSpeed()).isGreaterThanOrEqualTo(0);
        }

        Set<String> uniqueDates = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            uniqueDates.add(forecasts.get(i).getDate().toString());
        }
        assertThat(uniqueDates).hasSize(5);
    }
}
