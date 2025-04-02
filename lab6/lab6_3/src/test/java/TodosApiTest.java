import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TodosApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testTodosEndpointIsAvailable() {
        when()
                .get("/todos")
                .then()
                .statusCode(200);
    }

    @Test
    public void testTodo4HasCorrectTitle() {
        when()
                .get("/todos/4")
                .then()
                .statusCode(200)
                .body("title", equalTo("et porro tempora"));
    }

    @Test
    public void testTodosContainId198And199() {
        when()
                .get("/todos")
                .then()
                .statusCode(200)
                .body("id", hasItems(198, 199));
    }

    @Test
    public void testTodosResponseTimeUnder2Seconds() {
        when()
                .get("/todos")
                .then()
                .time(lessThan(2L), TimeUnit.SECONDS);
    }
}
