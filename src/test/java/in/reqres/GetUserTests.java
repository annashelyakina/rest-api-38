package in.reqres;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class GetUserTests extends TestBase{
    public static final String apiKey = "reqres-free-v1";

    @Test
    @DisplayName("Проверка получения данных для существующего пользователя")
    void successfulGetUserTest() {
        // Предварительная проверка токена
        assertThat(apiKey).isNotBlank();   // Должен быть непустой строкой
        assertThat(apiKey.length()).isGreaterThanOrEqualTo(14);  // Минимальная длина должна быть > = 14 символов
        assertThat(apiKey).matches("[a-zA-Z0-9-]+");     // Должны быть алфавитно-цифровые символы и дефис

        given()
                .header("x-api-key", apiKey)
                .log().uri()
                .when()
                .get("/users/2")
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
        // Предварительная проверка токена
        assertThat(apiKey).isNotBlank();   // Должен быть непустой строкой
        assertThat(apiKey.length()).isGreaterThanOrEqualTo(14);  // Минимальная длина должна быть больше 8 символов
        assertThat(apiKey).matches("[a-zA-Z0-9-]+");     // Только алфавитно-цифровые символы и дефис

        given()
                .header(Constants.validApiKey)
                .log().uri()
                .when()
                .get("/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
