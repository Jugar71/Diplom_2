package tests;

import methods.OrderMethods;
import methods.UserMethods;
import org.junit.After;
import org.junit.Test;
import serialization.LoginSerialization;
import serialization.OrderCreateSerialization;
import serialization.RegisterSerialization;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class OrdersPostTest extends BaseTest {

    private boolean isUserCreated = false;

    @Test
    public void createOrderWithAuthorization(){
        RegisterSerialization user = new RegisterSerialization("test@user1.ru", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(user);
        isUserCreated = true;

        LoginSerialization login = new LoginSerialization("test@user1.ru", "password1");
        OrderCreateSerialization order = new OrderCreateSerialization(List.of("61c0c5a71d1f82001bdaaa6d"));

        OrderMethods ord = new OrderMethods();
        ord.createOrderWithAuth(order, login).then().assertThat().body("success", is(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void createOrderWithoutAuthorization(){
        OrderCreateSerialization order = new OrderCreateSerialization(List.of("61c0c5a71d1f82001bdaaa6d"));
        OrderMethods ord = new OrderMethods();
        ord.createOrderWithoutAuth(order).then().assertThat().body("success", is(true)) //сервис позволяет сделать заказ без авторизации
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void createOrderWithWrongHash(){
        RegisterSerialization user = new RegisterSerialization("test@user1.ru", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(user);
        isUserCreated = true;

        LoginSerialization login = new LoginSerialization("test@user1.ru", "password1");
        OrderCreateSerialization order = new OrderCreateSerialization(List.of("61"));

        OrderMethods ord = new OrderMethods();
        ord.createOrderWithAuth(order, login).then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void createOrderWithoutIngredients(){
        RegisterSerialization user = new RegisterSerialization("test@user1.ru", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(user);
        isUserCreated = true;

        LoginSerialization login = new LoginSerialization("test@user1.ru", "password1");
        OrderCreateSerialization order = new OrderCreateSerialization(null);

        OrderMethods ord = new OrderMethods();
        ord.createOrderWithAuth(order, login).then().assertThat().body("success", is(false))
                .and()
                .body("message", containsString("Ingredient ids must be provided"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @After
    public void cleaning() {
        if (isUserCreated) {
            LoginSerialization login = new LoginSerialization("test@user1.ru", "password1");
            UserMethods obj = new UserMethods();
            obj.deleteUser(login);
            isUserCreated = false;
        }
    }
}
