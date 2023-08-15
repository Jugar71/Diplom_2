package methods;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import serialization.LoginSerialization;
import serialization.OrderCreateSerialization;

import static io.restassured.RestAssured.given;

public class OrderMethods {

    UserMethods user = new UserMethods();
    static final String ORDERS = "/api/orders";

    @Step("Отправляет запрос POST на создание заказа на /api/orders")
    public Response createOrderWithAuth (OrderCreateSerialization order, LoginSerialization login) {
        Response response =
                given()
                        .header("Authorization", user.getToken(login))
                        .and()
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .post(ORDERS);
        System.out.println(response.body().asString() + "Отправили заказ");
        return response;
    }

    @Step("Отправляет запрос POST на создание заказа на /api/orders БЕЗ АВТОРИЗАЦИИ")
    public Response createOrderWithoutAuth (OrderCreateSerialization order) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .post(ORDERS);
        System.out.println(response.body().asString() + "Отправили заказ");
        return response;
    }

    @Step("Отправляет запрос GET на получение списка заказов на /api/orders")
    public Response getOrdersWithAuth (LoginSerialization login) {
        Response response =
                given()
                        .header("Authorization", user.getToken(login))
                        .and()
                        .header("Content-type", "application/json")
                        .when()
                        .get(ORDERS);
        System.out.println(response.body().asString() + "Получаем список заказов");
        return response;
    }

    @Step("Отправляет запрос GET на получение списка заказов на /api/orders БЕЗ АВТОРИЗАЦИИ")
    public Response getOrdersWithoutAuth () {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .get(ORDERS);
        System.out.println(response.body().asString() + "Получаем список заказов");
        return response;
    }
}
