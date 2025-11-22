package in.reqres;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class GetUserTests {
    @Test
    @DisplayName("Проверка получения данных для существующего пользователя")
    void successfulGetUserTest() {
        given()
                .header(Constants.validApiKey)
                 .log().uri()
                .when()
                .get(Constants.baseUrl + "/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    @DisplayName("Проверка статус кода в случае, если пользователь не найден")
    void notFoundUserTest() {
        given()
                .header(Constants.validApiKey)
                .log().uri()
                .when()
                .get(Constants.baseUrl + "/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
