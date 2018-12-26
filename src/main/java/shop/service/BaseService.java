package shop.service;

import com.google.gson.Gson;
import shop.controller.CartController;
import shop.controller.StoreController;
import shop.controller.UserController;

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
