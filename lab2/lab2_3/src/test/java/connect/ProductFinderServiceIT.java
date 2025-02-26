package connect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class ProductFinderServiceIT {
    @Test
    void testFindProductDetails() {
        ProductFinderService productFinderService=new ProductFinderService(new TqsBasicHttpClient());
        productFinderService.API_PRODUCTS="https://fakestoreapi.com/products/";
        Optional<Product> product = productFinderService.findProductDetails(2);

        assertTrue(product.isPresent());
        assertEquals(2, product.get().getId());
        assertEquals("Mens Casual Premium Slim Fit T-Shirts", product.get().getTitle().strip());
        assertEquals(22.3, product.get().getPrice());
    }
}
