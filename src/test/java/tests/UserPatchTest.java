package tests;

import methods.PatchMethods;
import methods.UserMethods;
import org.junit.After;
import org.junit.Test;
import serialization.*;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.*;

public class UserPatchTest extends BaseTest {

    private boolean isEmailChanged = false;
    private boolean isPasswordChanged = false;
    private boolean isNameChanged = false;

    @Test
    public void patchEmailWithAuthorization(){
        RegisterSerialization user = new RegisterSerialization("test@user1.ru", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(user);

        isEmailChanged = true;
        LoginSerialization login = new LoginSerialization("test@user1.ru", "password1");
        EmailPatchSerialization email = new EmailPatchSerialization("test@user2.ru");

        PatchMethods patch = new PatchMethods();
        patch.patchEmail(email, login).then().assertThat().body("success", is(true))
                .and()
                .body("user.email", containsString("test@user2.ru"))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void patchPasswordWithAuthorization(){
        RegisterSerialization user = new RegisterSerialization("test@user1.ru", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(user);

        isPasswordChanged = true;
        LoginSerialization login = new LoginSerialization("test@user1.ru", "password1");
        PasswordPatchSerialization password = new PasswordPatchSerialization("password2");

        PatchMethods patch = new PatchMethods();
        patch.patchPassword(password, login);
        LoginSerialization login2 = new LoginSerialization("test@user1.ru", "password2");
        obj.loginUser(login2).then().assertThat().body("success", is(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void patchNameWithAuthorization(){
        RegisterSerialization user = new RegisterSerialization("test@user1.ru", "password1", "name1");
        UserMethods obj = new UserMethods();
        obj.createUser(user);

        LoginSerialization login = new LoginSerialization("test@user1.ru", "password1");
        NamePatchSerialization name = new NamePatchSerialization("name2");

        PatchMethods patch = new PatchMethods();
        patch.patchName(name, login).then().assertThat().body("success", is(true))
                .and()
                .body("user.name", containsString("name2"))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void patchEmailWithoutAuthorization(){
        EmailPatchSerialization email = new EmailPatchSerialization("test@user2.ru");

        PatchMethods patch = new PatchMethods();
        patch.patchEmailWithoutAuth(email).then().assertThat().body("success", is(false))
                .and()
                .body("message", containsString("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    public void patchPasswordWithoutAuthorization(){
        PasswordPatchSerialization password = new PasswordPatchSerialization("password2");

        PatchMethods patch = new PatchMethods();
        patch.patchPasswordWithoutAuth(password).then().assertThat().body("success", is(false))
                .and()
                .body("message", containsString("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    public void patchNameWithoutAuthorization(){
        NamePatchSerialization name = new NamePatchSerialization("name2");

        PatchMethods patch = new PatchMethods();
        patch.patchNameWithoutAuth(name).then().assertThat().body("success", is(false))
                .and()
                .body("message", containsString("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @After
    public void cleaning(){
        if (isEmailChanged) {
            LoginSerialization login = new LoginSerialization("test@user2.ru", "password1");
            UserMethods obj = new UserMethods();
            obj.deleteUser(login);
            isEmailChanged = false;
        }
        if (isPasswordChanged){
            LoginSerialization login = new LoginSerialization("test@user1.ru", "password2");
            UserMethods obj = new UserMethods();
            obj.deleteUser(login);
            isPasswordChanged = false;
        }
        if (isNameChanged) {
            LoginSerialization login = new LoginSerialization("test@user1.ru", "password1");
            UserMethods obj = new UserMethods();
            obj.deleteUser(login);
            isNameChanged = false;
        }
    }
}
