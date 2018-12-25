package service;

import db.requests.CartDB;
import db.requests.StoreDB;
import db.requests.UserDB;

abstract public class BaseService {
    StoreDB storeDB;
    CartDB cartDB;
    UserDB userDB;

    BaseService() {
        storeDB = new StoreDB();
        cartDB = new CartDB();
        userDB = new UserDB();
    }
}
