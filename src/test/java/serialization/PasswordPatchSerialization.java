package serialization;

public class PasswordPatchSerialization {
    private String password;

    public PasswordPatchSerialization() {}

    public PasswordPatchSerialization(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
