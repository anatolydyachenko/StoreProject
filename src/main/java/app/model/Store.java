package app.model;

import java.util.*;

import static app.Helper.mapToJson;

public class Store {
    private Map<Product, Integer> availableProducts = new HashMap<>();

    public Map<Product, Integer> getAvailableProducts(){
        return  availableProducts;
    }


}
