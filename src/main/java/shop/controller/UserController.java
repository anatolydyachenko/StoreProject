package shop.controller;

import shop.app.model.User;
import com.lambdaworks.crypto.SCryptUtil;

public class UserController extends Controller {
    public boolean userExists(User user) {
        return userDB.userExists(user.getEmail());
    }

    public void addUser(User user) {
        userDB.addUser(user.getEmail(), user.getPassword());
    }

    public boolean login(User userToLogin) {
        User validCredentials = userDB.getUser(userToLogin.getEmail());
        return SCryptUtil.check(userToLogin.getPassword(), validCredentials.getPassword());
    }
}
