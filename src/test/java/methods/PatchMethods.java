package methods;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serialization.EmailPatchSerialization;
import serialization.LoginSerialization;
import serialization.NamePatchSerialization;
import serialization.PasswordPatchSerialization;

import static io.restassured.RestAssured.given;

public class PatchMethods {

    UserMethods user = new UserMethods();

    @Step("Отправляет запрос PATCH на изменение email пользователя на /api/auth/user")
    public Response patchEmail (EmailPatchSerialization email, LoginSerialization login) {
        Response response =
                given()
                        .header("Authorization", user.getToken(login))
                        .and()
                        .header("Content-type", "application/json")
                        .and()
                        .body(email)
                        .when()
                        .patch(user.USER);
        System.out.println(response.body().asString() + "Пропатчили email");
        return response;
    }

    @Step("Отправляет запрос PATCH на изменение password пользователя на /api/auth/user")
    public Response patchPassword (PasswordPatchSerialization password, LoginSerialization login) {
        Response response =
                given()
                        .header("Authorization", user.getToken(login))
                        .and()
                        .header("Content-type", "application/json")
                        .and()
                        .body(password)
                        .when()
                        .patch(user.USER);
        System.out.println(response.body().asString() + "Пропатчили password");
        return response;
    }

    @Step("Отправляет запрос PATCH на изменение name пользователя на /api/auth/user")
    public Response patchName (NamePatchSerialization name, LoginSerialization login) {

        Response response =
                given()
                        .header("Authorization", user.getToken(login))
                        .and()
                        .header("Content-type", "application/json")
                        .and()
                        .body(name)
                        .when()
                        .patch(user.USER);
        System.out.println(response.body().asString() + "Пропатчили name");
        return response;
    }

    @Step("Отправляет запрос PATCH на изменение email пользователя на /api/auth/user БЕЗ АВТОРИЗАЦИИ")
    public Response patchEmailWithoutAuth (EmailPatchSerialization email) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(email)
                        .when()
                        .patch(user.USER);
        System.out.println(response.body().asString() + "Пропатчили email");
        return response;
    }

    @Step("Отправляет запрос PATCH на изменение password пользователя на /api/auth/user БЕЗ АВТОРИЗАЦИИ")
    public Response patchPasswordWithoutAuth (PasswordPatchSerialization password) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(password)
                        .when()
                        .patch(user.USER);
        System.out.println(response.body().asString() + "Пропатчили password");
        return response;
    }

    @Step("Отправляет запрос PATCH на изменение name пользователя на /api/auth/user БЕЗ АВТОРИЗАЦИИ")
    public Response patchNameWithoutAuth (NamePatchSerialization name) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(name)
                        .when()
                        .patch(user.USER);
        System.out.println(response.body().asString() + "Пропатчили name");
        return response;
    }
}
