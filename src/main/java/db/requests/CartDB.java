package db.requests;

import app.Helper;
import db.DBClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartDB extends DBClient {

    public boolean modifyProductsInCart(int productId, int quantity, String sessionId) {
        boolean modified = false;

        if (quantity == 0) {
            modified = removeProduct(productId, sessionId);
        } else {
            try (PreparedStatement getQuantity = conn.prepareStatement("SELECT QUANTITY FROM PRODUCT WHERE PRODUCT_ID = ?")) {

                getQuantity.setInt(1, productId);
                try (ResultSet getQuantityRs = getQuantity.executeQuery()) {
                    if (getQuantityRs.next()) {
                        int productsInStore = getQuantityRs.getInt("QUANTITY");
                        Integer productsInCart = numberOfProductsInCart(productId, sessionId);

                        if (productsInCart != null && productsInStore >= quantity) {
                            PreparedStatement updateProduct =
                                    conn.prepareStatement("UPDATE CART SET QUANTITY = ? WHERE PRODUCT_ID = ? AND SESSION_ID LIKE ?");
                            updateProduct.setInt(1, quantity);
                            updateProduct.setInt(2, productId);
                            updateProduct.setString(3, sessionId);
                            updateProduct.execute();

                            modified = true;
                            updateProduct.close();
                        } else if (productsInCart == null && productsInStore >= quantity) {
                            PreparedStatement insertProduct = conn.prepareStatement("INSERT INTO CART (PRODUCT_ID, QUANTITY, SESSION_ID) VALUES (?,?,?)");
                            insertProduct.setInt(1, productId);
                            insertProduct.setInt(2, quantity);
                            insertProduct.setString(3, sessionId);
                            insertProduct.execute();

                            modified = true;
                            insertProduct.close();
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return modified;
    }

    private Integer numberOfProductsInCart(int productId, String sessionId) {
        Integer quantity = null;
        try (PreparedStatement st = conn.prepareStatement("SELECT QUANTITY FROM CART WHERE PRODUCT_ID = ? AND SESSION_ID LIKE ?")) {
            st.setInt(1, productId);
            st.setString(2, sessionId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    quantity = rs.getInt("QUANTITY");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantity;
    }

    public String getAllProducts(String sessionId) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> products = new ArrayList<>();
        float total = 0;

        try (PreparedStatement st = conn.prepareStatement(
                "SELECT CART.SESSION_ID, CART.PRODUCT_ID, PRODUCT.TITLE, PRODUCT.PRICE, CART.QUANTITY " +
                        "FROM CART JOIN PRODUCT ON CART.PRODUCT_ID=PRODUCT.PRODUCT_ID WHERE CART.SESSION_ID LIKE ?")) {
            st.setString(1, sessionId);

            try (ResultSet rs = st.executeQuery()) {
                int ordinal = 0;
                while (rs.next()) {
                    float subtotal = (float) (rs.getDouble("PRICE") * rs.getInt("QUANTITY"));
                    Map<String, Object> productMap = new HashMap<>();
                    productMap.put("ordinal", ++ordinal);
                    productMap.put("title", rs.getString("TITLE"));
                    productMap.put("quantity", rs.getInt("QUANTITY"));
                    productMap.put("subtotal", subtotal);
                    products.add(productMap);

                    total += subtotal;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        result.put("products", products);
        result.put("total", total);

        return Helper.objectToJson(result);
    }

    public boolean removeProduct(int productId, String sessioId) {
        boolean result = false;

        try (PreparedStatement st = conn.prepareStatement("DELETE FROM CART WHERE PRODUCT_ID = ? AND SESSION_ID LIKE ?")) {
            st.setInt(1, productId);
            st.setString(2, sessioId);
            st.execute();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean allowCheckout(String sessionId) {
        boolean allowCheckout = true;
        try (PreparedStatement st = conn.prepareStatement(
                "SELECT C.PRODUCT_ID AS cart_product, C.QUANTITY AS cart_quantity, P.PRODUCT_ID AS store_product, P.QUANTITY AS store_quantity\n" +
                        "FROM CART C JOIN PRODUCT P ON C.PRODUCT_ID=P.PRODUCT_ID WHERE SESSION_ID LIKE ?")) {
            st.setString(1, sessionId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int cartQuantity = rs.getInt("CART_QUANTITY");
                    int storeQuantity = rs.getInt("STORE_QUANTITY");
                    if (cartQuantity > storeQuantity) {
                        allowCheckout = false;
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allowCheckout;
    }

    public void clearCart(String sessionId) {
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM CART WHERE SESSION_ID LIKE ?")) {
            st.setString(1, sessionId);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkout(String sessionId) {
        boolean result = false;
        if (allowCheckout(sessionId)) {
            try (PreparedStatement st = conn.prepareStatement("SELECT PRODUCT_ID AS cart_product, QUANTITY AS cart_quantity " +
                    "FROM CART WHERE SESSION_ID LIKE ?")) {
                st.setString(1, sessionId);
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        try (PreparedStatement checkOutFromStore =
                                     conn.prepareStatement("UPDATE PRODUCT SET QUANTITY = QUANTITY- ? WHERE PRODUCT_ID = ?")) {
                            checkOutFromStore.setInt(1, rs.getInt("cart_quantity"));
                            checkOutFromStore.setInt(2, rs.getInt("cart_product"));
                            checkOutFromStore.execute();
                            clearCart(sessionId);
                        }
                    }
                }
                result = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
}
