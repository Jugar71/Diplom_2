package tests;

import methods.UserMethods;
import org.junit.After;
import org.junit.Test;
import serialization.LoginSerialization;
import serialization.RegisterSerialization;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class LoginPostTest extends BaseTest {

    private boolean isCleaningNeeded = false;

    @Test
    public void correctLogin(){
        isCleaningNeeded = true;
        RegisterSerialization userRegister = new RegisterSerialization("test@user1.ru", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(userRegister);
        LoginSerialization userLogin = new LoginSerialization("test@user1.ru", "password1");
        obj.loginUser(userLogin).then().assertThat().body("success", is(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void incorrectLogin() {
        LoginSerialization user = new LoginSerialization("t", "password1");
        UserMethods obj = new UserMethods();
        obj.loginUser(user).then().assertThat().body("success", is(false))
                .and()
                .body("message", containsString("email or password are incorrect"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void cleaning(){
        if(isCleaningNeeded) {
            LoginSerialization login = new LoginSerialization("test@user1.ru", "password1");
            UserMethods obj = new UserMethods();
            obj.deleteUser(login);
            isCleaningNeeded = false;
        }
    }
}
