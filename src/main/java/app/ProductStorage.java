package app;

import java.util.*;

import static app.Helper.mapToJson;

public abstract class ProductStorage {
    /*Each record of a List is a row with 2 columns, which are implemented
     * as an object array with 2 elements: {product, productCount}*/
    private List<Object[]> availableProducts = new ArrayList<>();


    public String getAllProducts() {
        Map<String, List> availableProductsMapForJson = new HashMap<>();
        List availableProductsListForJson = new ArrayList();
        availableProductsMapForJson.put("products", availableProductsListForJson);

        for (Object[] row : availableProducts) {
            Map<String, Object> storeProduct = new HashMap();
            Product product = (Product) row[0];
            int productCount = (int) row[1];

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
        Object[] productFound = availableProducts.stream().filter(row -> row[0] == product).findFirst().orElse(null);
        if (productFound != null) {
            existingProducts = (int) productFound[1];
            productFound[1] = existingProducts + count;
        } else {
            Object[] row = {product, existingProducts + count};
            availableProducts.add(row);
        }
    }


}
