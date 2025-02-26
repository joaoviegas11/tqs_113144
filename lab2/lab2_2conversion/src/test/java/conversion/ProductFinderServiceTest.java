package conversion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductFinderServiceTest {

    @Mock
    private ISimpleHttpClient httpClientMock;

    @InjectMocks
    private ProductFinderService productFinderService;

    @Test
    void testFindProductDetails() {

        when(httpClientMock.doHttpGet("https://fakestoreapi.com/products/2")).thenReturn(
                """
                        {
                        "id": 2,
                        "title": "Mens Casual Premium Slim Fit T-Shirts ",
                        "price": 22.3,
                        "description": "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
                        "category": "men's clothing",
                        "image": "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
                        "rating": {
                          "rate": 4.1,
                          "count": 259
                        }}
                          """);
        productFinderService.API_PRODUCTS="https://fakestoreapi.com/products/";
        Optional<Product> product = productFinderService.findProductDetails(2);

        assertTrue(product.isPresent());
        assertEquals(2, product.get().getId());
        assertEquals("Mens Casual Premium Slim Fit T-Shirts", product.get().getTitle().strip());
        assertEquals(22.3, product.get().getPrice());

    }

    @Test
    void testFindProductDetailsC() {

        when(httpClientMock.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn(
                """
                        {
                          "id": 3,
                          "title": "Mens Cotton Jacket",
                          "price": 55.99,
                          "description": "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
                          "category": "men's clothing",
                          "image": "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
                          "rating": {
                            "rate": 4.7,
                            "count": 500
                          }
                        }
                            """);
        productFinderService.API_PRODUCTS="https://fakestoreapi.com/products/";

        Optional<Product> product = productFinderService.findProductDetails(3);

        assertEquals(3, product.get().getId());
        assertEquals("Mens Cotton Jacket", product.get().getTitle().strip());
        assertEquals(55.99, product.get().getPrice());
        Optional<Product> product300 = productFinderService.findProductDetails(300);
        assertTrue(!product300.isPresent());


    }
}
