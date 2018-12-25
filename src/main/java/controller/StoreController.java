package controller;

import app.model.Product;

public class StoreController extends Controller {
    public String getAllProducts() {
        return storeDB.getAllProducts();
    }

    public void addProduct(Product product) {
        storeDB.addProduct(product.getTitle(), product.getPrice(), product.getQuantity());
    }
}
