package app;

import java.util.Map;

import static app.Helper.parseToJsonObject;

public class UserCartUtils {
    private Store store;

    UserCartUtils(Store store) {
        this.store = store;
    }

    public boolean putInCart(UserCart userCart, Integer id, Integer quantity) {
        if (checkProductExistInStore(id, quantity)) {
            return false;
        }
        return false;
    }

    private boolean checkProductExistInStore(Integer id, Integer quantity) {
        boolean exists = false;
        for (Map.Entry<Product, Integer> entry : store.getAvailableProducts().entrySet()) {
            if (entry.getKey().getId() == id && entry.getValue() >= quantity) {
                exists = true;
            }
        }
        return exists;
    }

}
