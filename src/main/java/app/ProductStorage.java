package app;

import java.util.*;

import static app.Helper.mapToJson;

public abstract class ProductStorage {
    private Map<Product, Integer> availableProducts = new HashMap<>();

    public String getAllProducts() {
        Map<String, List> availableProductsMapForJson = new HashMap<>();
        List<Map<String, Object>> availableProductsListForJson = new ArrayList<>();
        availableProductsMapForJson.put("products", availableProductsListForJson);

        for (Map.Entry<Product, Integer> entry : availableProducts.entrySet()) {
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

    public void addProduct(Product product, Integer count) {
        int existingProducts = 0;
        if (availableProducts.containsKey(product)) {
            existingProducts = availableProducts.get(product);
        }
        availableProducts.put(product, existingProducts + count);
    }

    //true if there is enough amount of product
    public boolean takeProduct(Product product, Integer count) {
        int existingProducts = 0;
        if (availableProducts.containsKey(product)) {
            existingProducts = availableProducts.get(product);
        }

        if (existingProducts >= count) {
            if (existingProducts == count) {
                availableProducts.remove(product);
            }
            availableProducts.put(product, existingProducts - count);
            return true;
        } else
            return false;
    }


}
