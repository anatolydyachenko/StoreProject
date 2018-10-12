package app.utils;

import app.model.Product;
import app.model.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Helper.mapToJson;

public class StoreUtils {
    private Store store;

    public StoreUtils(Store store) {
        this.store = store;
    }

    public String getAllProducts() {
        Map<String, Object> availableProductsMapForJson = new HashMap<>();
        List<Map<String, Object>> availableProductsListForJson = new ArrayList<>();
        availableProductsMapForJson.put("products", availableProductsListForJson);

        for (Map.Entry<Product, Integer> entry : store.getAvailableProducts().entrySet()) {
            Map<String, Object> storeProduct = new HashMap<>();
            Product product = entry.getKey();
            int productCount = entry.getValue();

            storeProduct.put("id", product.getId());
            storeProduct.put("title", product.getName());
            storeProduct.put("available", productCount);
            storeProduct.put("price", product.getPrice());

            availableProductsListForJson.add(storeProduct);
        }
        return mapToJson(availableProductsMapForJson);
    }

    public void addProduct(Product product, Integer quantity) {
        int existingProducts = 0;
        if (store.getAvailableProducts().containsKey(product)) {
            existingProducts = store.getAvailableProducts().get(product);
        }
        store.getAvailableProducts().put(product, existingProducts + quantity);
    }

    //true if there is enough amount of product
    public boolean takeProduct(Product product, Integer quantity) {
        int existingProducts = 0;
        if (store.getAvailableProducts().containsKey(product)) {
            existingProducts = store.getAvailableProducts().get(product);
        }

        if (existingProducts >= quantity) {
            if (existingProducts == quantity) {
                store.getAvailableProducts().remove(product);
            }
            store.getAvailableProducts().put(product, existingProducts - quantity);
            return true;
        } else
            return false;
    }

}
