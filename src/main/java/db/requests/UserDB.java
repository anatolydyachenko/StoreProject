package db.requests;

import db.DBClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDB extends DBClient {
    public static boolean userExists(String email) {
        boolean result = false;
        try (PreparedStatement st = conn.prepareStatement("SELECT EMAIL from USER where EMAIL like (?)");) {
            st.setString(1, email);
            try (ResultSet rs = st.executeQuery();) {
                result = rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
