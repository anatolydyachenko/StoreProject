package service;

import com.google.gson.Gson;
import controller.CartController;
import controller.StoreController;
import controller.UserController;
import db.requests.CartDB;
import db.requests.StoreDB;
import db.requests.UserDB;

abstract public class BaseService {
    public final static Gson gson = new Gson();

    StoreController storeController;
    CartController cartController;
    UserController userController;

    BaseService() {
        storeController = new StoreController();
        cartController = new CartController();
        userController = new UserController();
    }
}
