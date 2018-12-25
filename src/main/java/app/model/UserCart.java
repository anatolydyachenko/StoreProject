package app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Helper.mapToJson;

public class UserCart {
    private User user;
    private List<CartItem> cartItems = new ArrayList<>();

    public UserCart(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Map<Product, Integer> productsInCart = new HashMap<>();

    public String getProductsInCart() {
        Map<String, Object> cartMapForJson = new HashMap<>();
        List<Map<String, Object>> cartProductsListForJson = new ArrayList<>();
        cartMapForJson.put("products", cartProductsListForJson);
        double total = 0.0;

        for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()) {
            Map<String, Object> productQuantity = new HashMap<>();
            Product product = entry.getKey();
            int productCount = entry.getValue();
            double subtotal = product.getPrice() * productCount;

            productQuantity.put("id", product.getId());
            productQuantity.put("title", product.getTitle());
            productQuantity.put("quantity", productCount);
            productQuantity.put("price", product.getPrice());
            productQuantity.put("subtotal", subtotal);
            total += subtotal;
            cartProductsListForJson.add(productQuantity);
        }
        cartMapForJson.put("subtotal", total);
        return mapToJson(cartMapForJson);
    }


}
