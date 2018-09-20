package app;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Helper.mapToJson;

public abstract class ProductStorage {
    private List<Array> list = new ArrayList<>();
    private Map<Product, Integer> availableProducts = new HashMap<>();

    public String getAllProducts() {
        return mapToJson(availableProducts);
    }

    public void addProduct(Product product, Integer count) {
        int existingProducts = 0;
        if (availableProducts.containsKey(product)) {
            existingProducts = availableProducts.get(product);
        }
        availableProducts.put(product, existingProducts + count);
    }

}
