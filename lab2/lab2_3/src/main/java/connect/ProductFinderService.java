package connect;

import java.util.Optional;
import org.json.JSONObject;

public class ProductFinderService {
    private ISimpleHttpClient httpClient;
    public String API_PRODUCTS;

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Product> findProductDetails(Integer id) {
        String jsonString;
        try {
            jsonString = httpClient.doHttpGet(API_PRODUCTS + String.valueOf(id));
        } catch (Exception e) {
            return Optional.empty();
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            if (!jsonObject.has("id")) {
                return Optional.empty();
            }
            return Optional.ofNullable(new Product(
                    jsonObject.optInt("id"),
                    jsonObject.optString("title"),
                    jsonObject.optDouble("price"),
                    jsonObject.optString("description"),
                    jsonObject.optString("category"),
                    jsonObject.optString("image")));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
