package app.model;

import java.util.*;

import static app.Helper.mapToJson;

public class Store {
    private Map<Product, Integer> availableProducts = new HashMap<>();

    public Map<Product, Integer> getAvailableProducts(){
        return  availableProducts;
    }

    public String getAllProducts() {
        Map<String, Object> availableProductsMapForJson = new HashMap<>();
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

    public void addProduct(Product product, Integer quantity) {
        int existingProducts = 0;
        if (availableProducts.containsKey(product)) {
            existingProducts = availableProducts.get(product);
        }
        availableProducts.put(product, existingProducts + quantity);
    }

    //true if there is enough amount of product
    public boolean takeProduct(Product product, Integer quantity) {
        int existingProducts = 0;
        if (availableProducts.containsKey(product)) {
            existingProducts = availableProducts.get(product);
        }

        if (existingProducts >= quantity) {
            if (existingProducts == quantity) {
                availableProducts.remove(product);
            }
            availableProducts.put(product, existingProducts - quantity);
            return true;
        } else
            return false;
    }


}
