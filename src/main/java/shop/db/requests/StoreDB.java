package shop.db.requests;

import shop.app.Helper;
import shop.app.model.Product;
import shop.app.model.Store;
import shop.db.DBClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StoreDB extends DBClient {

    public String getAllProducts() {
        Store store = new Store();

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT PRODUCT_ID, TITLE, PRICE, QUANTITY FROM PRODUCT")) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("PRODUCT_ID"),
                        rs.getString("TITLE"),
                        rs.getDouble("PRICE"),
                        rs.getInt("QUANTITY")
                        );
                store.addProduct(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Helper.objectToJson(store);
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
