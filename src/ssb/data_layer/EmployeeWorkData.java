package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import ssb.data_layer.contracts.EmployeeContract;
import ssb.data_layer.contracts.LoginsContract;
import ssb.data_layer.contracts.PersonsContract;

class EmployeeWorkData {

    private final DatabaseManager db = DatabaseManager.getInstance();

    HashMap<String, String> getEmployeeData(String employeeCpr) {
        String sql = "SELECT * FROM " + EmployeeContract.TABLE_NAME + " NATURAL JOIN " + PersonsContract.TABLE_NAME
            + " WHERE " + EmployeeContract.TABLE_NAME + "." + EmployeeContract.COLUMN_CPR + " = ?";

        HashMap<String, String> columnData = new HashMap<>();
        try (Connection connection = db.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCpr);
            ResultSet result = statement.executeQuery();
            columnData.put(EmployeeContract.COLUMN_CPR, result.getString(EmployeeContract.COLUMN_CPR));
            columnData.put(EmployeeContract.COLUMN_ROLE, result.getString(EmployeeContract.COLUMN_ROLE));
            columnData.put(PersonsContract.COLUMN_FIRST_NAME, result.getString(PersonsContract.COLUMN_FIRST_NAME));
            columnData.put(PersonsContract.COLUMN_LAST_NAME, result.getString(PersonsContract.COLUMN_LAST_NAME));
            columnData.put(PersonsContract.COLUMN_PHONE, result.getString(PersonsContract.COLUMN_PHONE));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return columnData;
    }
    
    void insertEmployeePerson(String employeeCpr, String firstName, String lastName, String phoneNumber) {
        String sqlPersonInsertion = "INSERT INTO " + PersonsContract.TABLE_NAME + " VALUES "
            + "(?, ?, ?, ?)";
        try (Connection connection = db.connect();
            PreparedStatement insertStatement = connection.prepareStatement(sqlPersonInsertion)) {
            insertStatement.setString(1, employeeCpr);
            insertStatement.setString(2, firstName);
            insertStatement.setString(3, lastName);
            insertStatement.setString(4, phoneNumber);
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    void insertEmployee(String employeeCPR, String employeeRole) {
        String sqlEmployeeInsertion = "INSERT INTO " + EmployeeContract.TABLE_NAME + " VALUES " + "(?, ?)";
        
        try(Connection connection = db.connect();
            PreparedStatement insertStatement = connection.prepareStatement(sqlEmployeeInsertion)) {
            insertStatement.setString(1, employeeCPR);
            insertStatement.setString(2, employeeRole);
            insertStatement.execute();
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    void insertEmployeeLogin(String employeeCPR, String username, String password) {
        String sqlEmployeeLoginInsertion = "INSERT INTO " + LoginsContract.TABLE_NAME + " VALUES " 
                + "(?, ?, ?)";
        
        try(Connection connection = db.connect();
            PreparedStatement insertStatement = connection.prepareStatement(sqlEmployeeLoginInsertion)) {
            insertStatement.setString(1, employeeCPR);
            insertStatement.setString(2, username);
            insertStatement.setString(3, password);
            insertStatement.execute();
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("alt burde være tilføjet");
    }
}
