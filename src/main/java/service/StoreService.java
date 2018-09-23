package service;


import app.Product;
import app.Store;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lambdaworks.crypto.SCryptUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;


//@Path("/")
public class StoreService {
    public static void main(String[] args) {
        Store store = new Store();

        Product product1 = new Product(1, "Product Name1", 10.1);
        Product product2 = new Product(2, "Product Name2", 20.0);

        store.addProduct(product1,1);
        System.out.println(store.getAllProducts());

        store.addProduct(product2,2);
        System.out.println(store.getAllProducts());

        store.addProduct(product1,5);
        System.out.println(store.getAllProducts());


    }
}
