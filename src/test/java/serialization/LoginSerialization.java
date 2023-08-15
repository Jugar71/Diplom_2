package serialization;

public class LoginSerialization {
    private String email;
    private String password;

    public LoginSerialization() {}

    public LoginSerialization(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }
}
