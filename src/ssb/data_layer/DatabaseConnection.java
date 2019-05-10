package ssb.data_layer;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static DatabaseConnection instance = null;
    private String url = "jdbc:postgresql://manny.db.elephantsql.com:5432/erpvuwlh";
    private String username = "erpvuwlh";
    private String password = "P93u5USiQppfjlNkvsndF7Yg_Ml8NK_u";

    private DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    Connection connect() {
        try {
            Connection db = DriverManager.getConnection(url, username, password);
            return db;
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
