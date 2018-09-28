package app;

import com.lambdaworks.crypto.SCryptUtil;

public class User {
    private String email;
    private String passwordHash;

    public User(String email, String password){
        this.email=email;
        setPasswordHash(password);
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
        this.passwordHash = SCryptUtil.scrypt(password, 16, 16, 16);
    }
}
