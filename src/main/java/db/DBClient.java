package db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBClient {

    protected static Connection conn = getConnection();

    public static Connection getConnection() {
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

    private static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void prepareDB(Connection conn){
        try (Statement st = conn.createStatement()) {
            if (!st.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES where table_type = 'TABLE' and table_name = 'PRODUCT'").next()) {
                st.execute(getSql("C:/Users/adyachenko/IdeaProjects/StoreProject/src/main/java/db/init/schema.sql"));
                st.execute(getSql("C:/Users/adyachenko/IdeaProjects/StoreProject/src/main/java/db/init/data.sql"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getSql(String sqlName) {
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get(sqlName)));
        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    protected static String objectToJson(Object object){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
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