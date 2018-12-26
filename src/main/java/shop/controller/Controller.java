package shop.controller;

import shop.db.requests.CartDB;
import shop.db.requests.StoreDB;
import shop.db.requests.UserDB;

public class Controller {
    StoreDB storeDB;
    CartDB cartDB;
    UserDB userDB;

    Controller() {
        storeDB = new StoreDB();
        cartDB = new CartDB();
        userDB = new UserDB();
    }
}
