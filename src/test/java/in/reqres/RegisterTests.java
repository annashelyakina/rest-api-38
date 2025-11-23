package in.reqres;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class RegisterTests extends TestBase{

    @Test
    @DisplayName("Проверка успешной регистрации пользователя с валидными данными")
    void successfulRegisterTest() {
        given()
                .header(Constants.validApiKey)
                .body(Constants.validRegisterData)
                .contentType(ContentType.JSON)
                .log().uri()
             .when()
                .post( "/register")
             .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", Matchers.is(Matchers.notNullValue()))  // Токен должен существовать
                .body("token.length()", Matchers.is(Matchers.greaterThanOrEqualTo(17))) // Длина токена должна быть > = 17 символов
                .body("token", Matchers.is(Matchers.matchesRegex("\\S[a-zA-Z0-9]*\\S"))) // Не должно быть пробелов в начале и конце, минимум одна буква/цифра
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .body("id", is(4));
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае невалидного 'api key' для регистрации ")
    void invalidApiKeyTest() {
        given()
                .header(Constants.invalidApiKey)
                .body(Constants.validRegisterData)
                .contentType(ContentType.JSON)
                .log().uri()
            .when()
                .post("/register")
            .then()
                .log().status()
                .log().body()
                .statusCode(403)
                .body("error", is("Invalid or inactive API key"));
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае невалидного 'email' для регистрации")
    void invalidEmailTest() {
        given()
                .header(Constants.validApiKey)
                .body(Constants.invalidEmailInRegisterData)
                .contentType(ContentType.JSON)
                .log().uri()
            .when()
                .post( "/register")
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
                .post("/register")
           .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае отсутствия 'email' для регистрации")
    void missingEmailForRegisterTest() {
        given()
                .header(Constants.validApiKey)
                .body(Constants.onlyPasswordInRegisterData)
                .contentType(ContentType.JSON)
                .log().uri()
            .when()
                .post("/register")
            .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае отсутствия 'password' для регистрации")
    void missingPasswordForRegisterTest() {
        given()
                .header(Constants.validApiKey)
                .body(Constants.onlyEmailInRegisterData)
                .contentType(ContentType.JSON)
                .log().uri()
            .when()
                .post( "/register")
            .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
