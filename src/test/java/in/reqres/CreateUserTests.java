package in.reqres;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CreateUserTests {

    @Test
    @DisplayName("Проверка успешного создания пользователя")
    void successfulCreateUserTest() {
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v1");
        String createData = "{\"name\": \"Izabel\", \"job\": \"engineer\"}";

        given()
                .header(apiKeyHeader)
                .body(createData)
                .contentType(ContentType.JSON)
                .log().uri()

           .when()
                .post("https://reqres.in/api/users")

           .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Izabel"))
                .body("job", is("engineer"));
     }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае невалидного 'api key' для создания пользователя")
    void invalidApiKeyTest() {
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v122");
        String createData = "{\"name\": \"Izabel\", \"job\": \"engineer\"}";

        given()
                .header(apiKeyHeader)
                .body(createData)
                .contentType(ContentType.JSON)
                .log().uri()

            .when()
                .post("https://reqres.in/api/users")

            .then()
                .log().status()
                .log().body()
                .statusCode(403)
                .body("error", is("Invalid or inactive API key"));
    }
  }
