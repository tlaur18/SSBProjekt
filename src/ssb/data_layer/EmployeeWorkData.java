package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import ssb.data_layer.contracts.EmployeeContract;
import ssb.data_layer.contracts.PersonsContract;

class EmployeeWorkData {

    private final DatabaseConnection db = DatabaseConnection.getInstance();

    HashMap<String, String> getEmployeeData(String employeeCpr) {
        String sql = "SELECT * FROM " + EmployeeContract.TABLE_NAME + " NATURAL JOIN " + PersonsContract.TABLE_NAME
                + " WHERE " + EmployeeContract.TABLE_NAME + "." + EmployeeContract.COLUMN_CPR + " = ?";

        HashMap<String, String> columnData = new HashMap<>();
        try (Connection connection = db.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCpr);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                columnData.put(EmployeeContract.COLUMN_CPR, result.getString(EmployeeContract.COLUMN_CPR));
                columnData.put(EmployeeContract.COLUMN_ROLE, result.getString(EmployeeContract.COLUMN_ROLE));
                columnData.put(PersonsContract.COLUMN_FIRST_NAME, result.getString(PersonsContract.COLUMN_FIRST_NAME));
                columnData.put(PersonsContract.COLUMN_LAST_NAME, result.getString(PersonsContract.COLUMN_LAST_NAME));
                columnData.put(PersonsContract.COLUMN_PHONE, result.getString(PersonsContract.COLUMN_PHONE));
            }
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

        try (Connection connection = db.connect();
                PreparedStatement insertStatement = connection.prepareStatement(sqlEmployeeInsertion)) {
            insertStatement.setString(1, employeeCPR);
            insertStatement.setString(2, employeeRole);
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    ArrayList<HashMap<String, String>> loadAllEmployees() {
        String sql = "SELECT * FROM " + EmployeeContract.TABLE_NAME + " NATURAL JOIN " + PersonsContract.TABLE_NAME;

        ArrayList<HashMap<String, String>> allEmployeeHashmaps = new ArrayList();

        try (Connection connection = db.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                allEmployeeHashmaps.add(getEmployeeData(rs.getString(EmployeeContract.COLUMN_CPR)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

        }
        return allEmployeeHashmaps;
    }

    void updateEmployee(String employeeCpr, String firstName, String lastName, String phoneNumber) {
        String sqlUpdate = "UPDATE " + PersonsContract.TABLE_NAME
                + " SET "
                + PersonsContract.COLUMN_FIRST_NAME + " = ?, "
                + PersonsContract.COLUMN_LAST_NAME + " = ?, "
                + PersonsContract.COLUMN_PHONE + " = ?"
                + " WHERE " + PersonsContract.COLUMN_CPR + " = ?";
        try (Connection connection = db.connect();
                PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate)) {
            updateStatement.setString(1, firstName);
            updateStatement.setString(2, lastName);
            updateStatement.setString(3, phoneNumber);
            updateStatement.setString(4, employeeCpr);
            System.out.println(updateStatement);
            updateStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void deleteEmployee(String employeeCPR) {
        String sqlUpdate = "DELETE FROM " + EmployeeContract.TABLE_NAME
                + " WHERE " + EmployeeContract.COLUMN_CPR + " = ?";
        try (Connection connection = db.connect();
                PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate)) {
            updateStatement.setString(1, employeeCPR);
            updateStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    DEN HER COMPUTER ER HURTIG
    void deletePerson(String personCPR) {
        String sqlUpdate = "DELETE FROM " + PersonsContract.TABLE_NAME +
                " WHERE " + PersonsContract.COLUMN_CPR + " = ?";
        try(Connection connection = db.connect();
                PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate)) {
            updateStatement.setString(1, personCPR);
            updateStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
