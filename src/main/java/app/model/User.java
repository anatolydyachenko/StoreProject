package app.model;

public class User {
    private String email;
    private String passwordHash;

    public User(String email, String passwordHash){
        this.email=email;
        this.passwordHash=passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }
}
