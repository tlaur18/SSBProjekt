package ssb.domain_layer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import ssb.data_layer.EmployeeWorkData;
import ssb.data_layer.LogInData;

public class DatabaseManager {
    private Connection connection = null;
    private EmployeeWorkData employeeWorkData = new EmployeeWorkData();
    private LogInData logInData = new LogInData();

    public DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:ssbprojektdata.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Could not connect to the database");
        }
    }

    public String checkUserLogin(String username, String password) {
        logInData.loadUserLogIns(connection);
    }
}
