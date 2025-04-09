package pt.uni.tqs.HW1.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.uni.tqs.HW1.utils.Weather;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class WeatherApi {

    private String apiKey = "3bbf82cab662991f6f9aa44509d9c5a8";
    private static final float lat = 40.63099726658498f;
    private static final float lon = -8.65755022635584f;
    private HttpClient httpClient = HttpClient.newHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Weather> fetchWeatherData() {
        List<Weather> forecasts = new ArrayList<>();
        
        try {
            String url = String.format(
                    "https://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&appid=%s&units=metric",
                    lat, lon, apiKey);
            URI uri = new URI(url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JsonNode root = objectMapper.readTree(response.body());
            JsonNode list = root.get("list");

            for (JsonNode item : list) {
                String dateTimeText = item.get("dt_txt").asText();
                if (!dateTimeText.contains("18:00:00")) {
                    continue;
                }

                long timestamp = item.get("dt").asLong();
                LocalDate date = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();

                double minTemp = item.get("main").get("temp_min").asDouble();
                double maxTemp = item.get("main").get("temp_max").asDouble();
                double windSpeed = item.get("wind").get("speed").asDouble();
                String description = item.get("weather").get(0).get("description").asText();

                Weather forecast = new Weather(date, description, minTemp, maxTemp, windSpeed);
                forecasts.add(forecast);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return forecasts;
    }
}
