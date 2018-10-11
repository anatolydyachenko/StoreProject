package app;

import app.model.UserCart;
import app.model.Product;
import app.model.Store;

import java.util.Map;

public class UserCartUtils {
    private Store store;

    UserCartUtils(Store store) {
        this.store = store;
    }

    public boolean putInCart(UserCart cart, Integer id, Integer quantity) {
        Product product = checkProductExistInStore(id, quantity);
        if (product != null) {
            if (store.getAvailableProducts().get(product) == quantity){

            }
            return true;
        } else return false;
    }

    private Product checkProductExistInStore(Integer id, Integer quantity) {
        Product productsExist = null;
        for (Map.Entry<Product, Integer> entry : store.getAvailableProducts().entrySet()) {
            if (entry.getKey().getId() == id && entry.getValue() >= quantity) {
                productsExist = entry.getKey();
            }
        }
        return productsExist;
    }


}
