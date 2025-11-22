package in.reqres;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class RegisterTests {

    @Test
    @DisplayName("Проверка успешной регистрации пользователя с валидными данными")
    void successfulRegisterTest() {
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v1");
        String registerData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .header(apiKeyHeader)
                .body(registerData)
                .contentType(ContentType.JSON)
                .log().uri()

             .when()
                .post("https://reqres.in/api/register")

             .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .body("id", is(4));
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае невалидного 'api key' для регистрации ")
    void invalidApiKeyTest() {
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v11");
        String registerData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .header(apiKeyHeader)
                .body(registerData)
                .contentType(ContentType.JSON)
                .log().uri()

            .when()
                .post("https://reqres.in/api/register")

            .then()
                .log().status()
                .log().body()
                .statusCode(403)
                .body("error", is("Invalid or inactive API key"));
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае невалидного 'email' для регистрации")
    void invalidEmailTest() {
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v1");
        String registerData = "{\"email\": \"eve111.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .header(apiKeyHeader)
                .body(registerData)
                .contentType(ContentType.JSON)
                .log().uri()

            .when()
                .post("https://reqres.in/api/register")

            .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    @DisplayName("Проверка статус кода в случае отсутствия данных для регистрации и api key.")
    void unSuccessfulRegisterTest() {
        given()
                .log().uri()
                .post("https://reqres.in/api/register")
           .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае отсутствия 'email' для регистрации")
    void missingEmailForRegisterTest() {
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v1");
        String registerData = "{\"password\": \"pistol\"}";

        given()
                .header(apiKeyHeader)
                .body(registerData)
                .contentType(ContentType.JSON)
                .log().uri()

            .when()
                .post("https://reqres.in/api/register")

            .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае отсутствия 'password' для регистрации")
    void missingPasswordForRegisterTest() {
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v1");
        String registerData = "{\"email\": \"eve.holt@reqres.in\"}";

        given()
                .header(apiKeyHeader)
                .body(registerData)
                .contentType(ContentType.JSON)
                .log().uri()

            .when()
                .post("https://reqres.in/api/register")

            .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
