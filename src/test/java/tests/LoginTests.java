package tests;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class LoginTests {
    /*
    1. Make request (POST) to https://reqres.in/api/login
    with body { "email": "eve.holt@reqres.in", "password": "cityslicka" }
    2. Get response { "token": "QpwL5tke4Pnpja7X4" }
    3. Check "token" is "QpwL5tke4Pnpja7X4" and status code 200
     */

    @Test
    void successfulLoginTest() {
        // Правильно формируем заголовок
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v1");

        // Данные аутентификации
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

        given()
                .header(apiKeyHeader)   // Передаем правильный объект заголовка
                .body(authData)
                .contentType(ContentType.JSON)
                .log().uri()

        .when()
              .post("https://reqres.in/api/login")

        .then()
              .log().status()
              .log().body()
              .statusCode(200)
              .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void userNotFoundTest() {
        // Правильно формируем заголовок
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v1");

        // Данные аутентификации
        String authData = "{\"email\": \"eve1.holt@reqres.in\", \"password\": \"cityslicka\"}";

        given()
                .header(apiKeyHeader)   // Передаем правильный объект заголовка
                .body(authData)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("user not found"));
    }
    @Test
    void missingPasswordTest() {
        // Правильно формируем заголовок
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v1");

        // Данные аутентификации
        String authData = "{\"email\": \"eve1.holt@reqres.in\"}";

        given()
                .header(apiKeyHeader)   // Передаем правильный объект заголовка
                .body(authData)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void missingLoginTest() {
        // Правильно формируем заголовок
        Header apiKeyHeader = new Header("x-api-key", "reqres-free-v1");

        // Данные аутентификации
        String authData = "{\"password\": \"cityslicka\"}";

        given()
                .header(apiKeyHeader)   // Передаем правильный объект заголовка
                .body(authData)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }


    @Test
    void successful1LoginTest() {
        // Данные аутентификации
        String authData = "";

        given()
                .body(authData)
                .log().uri()

                .when()
                .post("https://reqres.in/api/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(415)
                .body("error", is("unsupported_charset"));
    }

    @Test
    void unSuccessfulLoginTest() {
        given()
                .log().uri()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }

}
