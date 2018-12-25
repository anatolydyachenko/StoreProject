package controller;

import db.requests.CartDB;
import db.requests.StoreDB;
import db.requests.UserDB;

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
