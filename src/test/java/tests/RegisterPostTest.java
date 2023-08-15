package tests;

import methods.UserMethods;
import org.junit.After;
import org.junit.Test;
import serialization.LoginSerialization;
import serialization.RegisterSerialization;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class RegisterPostTest extends BaseTest {

    private boolean isCleaningNeeded = false;

    @Test
    public void createUser() {
        isCleaningNeeded = true;
        RegisterSerialization user = new RegisterSerialization("test@user1.ru", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(user).then().assertThat().body("success", is(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void createUserTwice() {
        isCleaningNeeded = true;
        RegisterSerialization user = new RegisterSerialization("test@user1.ru", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(user);
        obj.createUser(user).then().assertThat().body("success", is(false))
                .and()
                .body("message", containsString("User already exists"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

    @Test
    public void createUserWithoutRequiredField() {
        RegisterSerialization user = new RegisterSerialization("", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(user).then().assertThat().body("success", is(false))
                .and()
                .body("message", containsString("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
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
