package service;

import app.model.Product;
import app.model.Store;
import app.model.User;
import app.model.UserCart;
import db.DBClient;

import javax.ws.rs.Path;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

abstract public class BaseService {
    static Connection conn = DBClient.getConnection();

//    static List<User> usersRegistered = new ArrayList<>();
    static Store store = new Store();
    static List<UserCart> userCarts = new ArrayList<>();


    static {
        Product product1 = new Product(1, "Product Name1", 10.1);
        Product product2 = new Product(2, "Product Name2", 20.0);
//        User admin = new User("admin", "admin");

        store.addProduct(product1, 1);
        store.addProduct(product2, 2);
        store.addProduct(product1, 5);

//        usersRegistered.add(admin);
    }
}
