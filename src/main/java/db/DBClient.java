package db;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBClient {

    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:h2:./src/main/java/db/ShopDB;" +
                                "INIT=" +
                                "RUNSCRIPT FROM './src/main/java/db/schema.sql'\\;" +
                                "RUNSCRIPT FROM './src/main/java/db/data.sql'",
                        "sa", "");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void main(String[] args) {
        try {
            conn = getConnection();
            Statement st = null;
            st = conn.createStatement();
            st.execute("CREATE TABLE TEST (id INTEGER, name VARCHAR NOT NULL)");
            st.execute("INSERT INTO TEST VALUES(default,'HELLO')");
            st.execute("INSERT INTO TEST(NAME) VALUES('JOHN')");
            String name1 = "Jack";
            String q = "insert into TEST(name) values(?)";
            PreparedStatement st1 = null;

            st1 = conn.prepareStatement(q);
            st1.setString(1, name1);
            st1.execute();

            ResultSet result;
            result = st.executeQuery("SELECT * FROM TEST");
            while (result.next()) {
                String name = result.getString("NAME");
                System.out.println(result.getString("ID") + " " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}