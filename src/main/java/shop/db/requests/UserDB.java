package shop.db.requests;

import shop.app.model.User;
import com.lambdaworks.crypto.SCryptUtil;
import shop.db.DBClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB extends DBClient {
    public boolean userExists(String email) {
        boolean result = false;
        try (PreparedStatement st = conn.prepareStatement("SELECT EMAIL FROM USER WHERE EMAIL LIKE (?)");) {
            st.setString(1, email);
            try (ResultSet rs = st.executeQuery();) {
                result = rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addUser(String email, String password) {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO USER(EMAIL, PASSWORDHASH) VALUES (?, ?)")) {
            st.setString(1, email);
            st.setString(2, SCryptUtil.scrypt(password, 16, 16, 16));
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String email) {
        User user = null;
        try (PreparedStatement st = conn.prepareStatement("SELECT EMAIL, PASSWORDHASH FROM USER WHERE EMAIL LIKE (?)")) {
            st.setString(1, email);
            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                user = new User(rs.getString("EMAIL"), rs.getString("PASSWORDHASH"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
