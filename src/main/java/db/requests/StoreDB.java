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
