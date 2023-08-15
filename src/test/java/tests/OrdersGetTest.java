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

public class OrdersGetTest extends BaseTest {

    private boolean isUserCreated = false;

    @Test
    public void getOrdersWithAuthorization(){
        RegisterSerialization user = new RegisterSerialization("test@user1.ru", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(user);
        isUserCreated = true;

        LoginSerialization login = new LoginSerialization("test@user1.ru", "password1");
        OrderCreateSerialization order = new OrderCreateSerialization(List.of("61c0c5a71d1f82001bdaaa6d"));
        OrderMethods ord = new OrderMethods();
        ord.createOrderWithAuth(order, login);

        ord.getOrdersWithAuth(login).then().assertThat().body("success", is(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void getOrdersWithoutAuthorization(){
        OrderMethods ord = new OrderMethods();
        ord.getOrdersWithoutAuth().then().assertThat().body("success", is(false))
                .and()
                .body("message", containsString("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
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
