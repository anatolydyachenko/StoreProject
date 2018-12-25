package shop.db;

import shop.app.Helper;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBClient {

    protected Connection conn = getConnection();

    public Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.h2.Driver");
                conn = DriverManager.getConnection("jdbc:h2:C:/Users/adyachenko/IdeaProjects/StoreProject/src/main/java/db/ShopDB;"
//                                +
//                                "INIT=" +
//                                "RUNSCRIPT FROM 'C:/Users/adyachenko/IdeaProjects/StoreProject/src/main/java/db/init/schema.sql'\\;" +
//                                "RUNSCRIPT FROM 'C:/Users/adyachenko/IdeaProjects/StoreProject/src/main/java/db/init/data.sql'"
                        , "sa", "");
                prepareDB(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

//    private void closeConnection() {
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private static void prepareDB(Connection conn) {
        try (Statement st = conn.createStatement()) {
            if (!st.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE table_type = 'TABLE' AND table_name = 'PRODUCT'").next()) {
                st.execute(Helper.getSql("C:/Users/adyachenko/IdeaProjects/StoreProject/src/main/java/db/init/schema.sql"));
                st.execute(Helper.getSql("C:/Users/adyachenko/IdeaProjects/StoreProject/src/main/java/db/init/data.sql"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}