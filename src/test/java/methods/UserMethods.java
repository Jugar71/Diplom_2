package methods;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serialization.*;

import static io.restassured.RestAssured.given;

public class UserMethods {
    private String token;
    static final String REGISTER = "/api/auth/register";
    static final String LOGIN = "/api/auth/login";
    static final String USER = "/api/auth/user";

    @Step("Отправляет запрос POST о создании пользователя на /api/auth/register")
    public Response createUser (RegisterSerialization user) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(user)
                        .when()
                        .post(REGISTER);
        System.out.println(response.body().asString());
        return response;
    }

    @Step("Отправляет запрос POST на авторизацию на /api/auth/login")
    public Response loginUser (LoginSerialization login) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post(LOGIN);
        System.out.println(response.body().asString());
        return response;
    }

    @Step("Получает токен")
    public String getToken (LoginSerialization login) {
        String[] split = loginUser(login).body().asString().split("\"");
        return token = split[5];
    }

    @Step("Отправляет запрос DELETE на удаление пользователя на /api/auth/user")
    public void deleteUser (LoginSerialization login) {
        Response response2 =
                given()
                        .header("Authorization", getToken(login))
                        .and()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .delete(USER);
        System.out.println(response2.body().asString() + " !!!УДАЛЕНО!!!");
    }
}
