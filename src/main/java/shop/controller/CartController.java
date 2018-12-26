package shop.controller;

import shop.app.dto.ProductDto;
import shop.app.model.Product;

public class CartController extends Controller {

    public boolean modifyProductsInCart(ProductDto product, String sessionId) {
        return cartDB.modifyProductsInCart(product.getId(), product.getQuantity(), sessionId);
    }

    public String getAllProducts(String sessionId) {
        return cartDB.getAllProducts(sessionId);
    }

    public boolean checkout(String sessionId){
        return cartDB.checkout(sessionId);
    }

    public boolean removeProduct(Product product, String sessionId){
        return cartDB.removeProduct(product.getId(), sessionId);
    }
}
