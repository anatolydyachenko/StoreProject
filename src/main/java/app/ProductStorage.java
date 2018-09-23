package app;

import java.util.*;
import static app.Helper.listToJson;

public abstract class ProductStorage {
    private List<Object[]> availableProducts = new ArrayList<>();
    private Map<String, List> availableProductsMap = new HashMap<>();
    private List availableProductsList = new ArrayList();

    ProductStorage() {
        availableProductsMap.put("products", availableProductsList);
    }


    public String getAllProducts() {
        List result = new ArrayList();
        for (Object[] row : availableProducts) {
            Map<String, Object> storeProduct = new HashMap();
            Product product = (Product) row[0];
            int productCount = (int) row[1];

            storeProduct.put("id", product.getId());
            storeProduct.put("title", product.getName());
            storeProduct.put("available", productCount);
            storeProduct.put("price", product.getPrice());

            result.add(storeProduct);
        }

        return listToJson(result);
    }

    public void addProduct(final Product product, Integer count) {
        int existingProducts = 0;
        Object[] productFound = availableProducts.stream().filter(row -> row[0] == product).findFirst().orElse(null);
        if (productFound != null) {
            existingProducts = (int) productFound[1];
        }
        Object[] row = {product, existingProducts + count};
        availableProducts.add(row);
    }


}
