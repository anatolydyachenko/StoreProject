package db.requests;

import app.Helper;
import app.model.Product;
import db.DBClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreDB extends DBClient {

    public void addProductInStore(Product product, int count) {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO PRODUCT (title, quantity, price) VALUES (?,?,?)")) {
            st.setString(1, product.getName());
            st.setInt(2, count);
            st.setDouble(3, product.getPrice());
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean productExist(int productId, int count) {
        boolean productExist = false;
        try (PreparedStatement st = conn.prepareStatement("SELECT PRODUCT_ID, QUANTITY FROM PRODUCT WHERE PRODUCT_ID = ? AND QUANTITY >= ?")) {
            st.setInt(1, productId);
            st.setInt(2, count);
            productExist = st.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productExist;
    }


    public String getAllProducts() {
        List<Map<String, Object>> allProducts = new ArrayList<>();

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT PRODUCT_ID, TITLE, PRICE, QUANTITY FROM PRODUCT")) {

            while (rs.next()) {
                Map<String, Object> productMap = new HashMap<>();
                productMap.put("product_id", rs.getInt("PRODUCT_ID"));
                productMap.put("title", rs.getString("TITLE"));
                productMap.put("price", rs.getDouble("PRICE"));
                productMap.put("quantity", rs.getInt("QUANTITY"));
                allProducts.add(productMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Helper.objectToJson(allProducts);
    }

    public void addProduct(String title, double price, int quantity) {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO PRODUCT (TITLE, PRICE, QUANTITY) VALUES (?,?,?)")) {
            st.setString(1, title);
            st.setDouble(2, price);
            st.setInt(3, quantity);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
