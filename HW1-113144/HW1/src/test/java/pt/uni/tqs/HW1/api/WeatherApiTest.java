package pt.uni.tqs.HW1.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.uni.tqs.HW1.utils.Weather;

import java.lang.reflect.Field;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WeatherApiTest {

    private WeatherApi weatherApi;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockResponse;

    private String mockJsonResponse = """
        {
          "list": [
            {
              "dt": 1712606400,
              "dt_txt": "2024-04-08 18:00:00",
              "main": {
                "temp_min": 10.0,
                "temp_max": 20.0
              },
              "wind": {
                "speed": 5.5
              },
              "weather": [
                { "description": "clear sky" }
              ]
            },
            {
              "dt": 1712610000,
              "dt_txt": "2024-04-08 15:00:00"
            }
          ]
        }
        """;

    @BeforeEach
    void setUp() throws Exception {
        weatherApi = new WeatherApi();

        mockHttpClient = mock(HttpClient.class);
        mockResponse = mock(HttpResponse.class);

        // Inject mock HttpClient
        Field clientField = WeatherApi.class.getDeclaredField("httpClient");
        clientField.setAccessible(true);
        clientField.set(weatherApi, mockHttpClient);

        // Inject ObjectMapper (optional)
        Field mapperField = WeatherApi.class.getDeclaredField("objectMapper");
        mapperField.setAccessible(true);
        mapperField.set(weatherApi, new ObjectMapper());
    }

    @Test
    void whenApiReturnsValidJson_thenParseForecasts() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        when(mockResponse.body()).thenReturn(mockJsonResponse);

        List<Weather> result = weatherApi.fetchWeatherData();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescription()).isEqualTo("clear sky");
        assertThat(result.get(0).getMinTemp()).isEqualTo(10.0);
        assertThat(result.get(0).getMaxTemp()).isEqualTo(20.0);
        assertThat(result.get(0).getWindSpeed()).isEqualTo(5.5);
    }

    @Test
    void whenApiThrowsException_thenReturnNull() throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new RuntimeException("Simulated error"));

        List<Weather> result = weatherApi.fetchWeatherData();

        assertThat(result).isNull();
    }
}
