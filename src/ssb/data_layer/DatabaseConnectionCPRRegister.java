package ssb.data_layer;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionCPRRegister {

    private static DatabaseConnectionCPRRegister instance = null;
    private String url = "jdbc:postgresql://manny.db.elephantsql.com:5432/arjlvlxw";
    private String username = "arjlvlxw";
    private String password = "lO6sQnMdT_WqCvsyPFrVSnCBT8-Vuxej";

    private DatabaseConnectionCPRRegister() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseConnectionCPRRegister getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionCPRRegister();
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
