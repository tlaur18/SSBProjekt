package ssb.data_layer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class DatabaseManager {

    private static final String DATABASE_NAME = "ssbprojektdata.db";

    private static DatabaseManager instance = null;

    private DatabaseManager() {
    }

    Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Could not connect to the database");
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public String checkUserLogin(String username, String password) {
        final LogInData logInData = new LogInData();
        return logInData.getUserLoginWith(username, password);
    }
    
    public HashMap<String, String> getEmployeeDetails(String employeeCpr) {
        final EmployeeWorkData employeeWorkData = new EmployeeWorkData();
        return employeeWorkData.getEmployeeData(employeeCpr);
    }
}
