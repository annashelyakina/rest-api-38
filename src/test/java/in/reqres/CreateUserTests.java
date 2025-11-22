package in.reqres;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CreateUserTests extends TestBase{

    @Test
    @DisplayName("Проверка успешного создания пользователя")
    void successfulCreateUserTest() {
         given()
                .header(Constants.validApiKey)
                .body(Constants.validCreateData)
                .contentType(ContentType.JSON)
                .log().uri()
           .when()
                .post("/users")
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
        given()
                .header(Constants.invalidApiKey)
                .body(Constants.validCreateData)
                .contentType(ContentType.JSON)
                .log().uri()
            .when()
                .post( "/users")
           .then()
                .log().status()
                .log().body()
                .statusCode(403)
                .body("error", is("Invalid or inactive API key"));
    }
  }
