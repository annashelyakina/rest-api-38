package in.reqres;

import io.restassured.http.Header;

public class Constants {
    private Constants() {}
    public static final Header validApiKey = new Header("x-api-key", "reqres-free-v1");
    public static final Header invalidApiKey = new Header("x-api-key", "reqres-free-v111");
    public static final String validCreateData = "{\"name\": \"Izabel\", \"job\": \"engineer\"}";
    public static final String validRegisterData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
    public static final String invalidEmailInRegisterData = "{\"email\": \"eve111.holt@reqres.in\", \"password\": \"pistol\"}";
    public static final String onlyPasswordInRegisterData = "{\"password\": \"pistol\"}";
    public static final String onlyEmailInRegisterData = "{\"email\": \"eve.holt@reqres.in\"}";

}
