package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Helper.mapToJson;

public class UserCart {
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
            productQuantity.put("title", product.getName());
            productQuantity.put("quantity", productCount);
            productQuantity.put("price", product.getPrice());
            productQuantity.put("subtotal", subtotal);
            total += subtotal;
            cartProductsListForJson.add(productQuantity);
        }
        cartMapForJson.put("subtotal", total);
        return mapToJson(cartMapForJson);
    }

    private void putProductInCart(Product product, Integer quantity) {
        int existingProducts = 0;
        if (productsInCart.containsKey(product)) {
            existingProducts = productsInCart.get(product);
        }
        productsInCart.put(product, existingProducts + quantity);
    }

    public boolean changeProductsQuantity(Integer id, Integer quantity) {
        return false;
    }

}
