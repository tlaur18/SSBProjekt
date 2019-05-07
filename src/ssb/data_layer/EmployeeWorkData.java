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

//    void deleteEmployee(String employeeCPR) {
//        String sqlUpdate = "DELETE FROM " + EmployeeContract.TABLE_NAME
//                + " WHERE " + EmployeeContract.COLUMN_CPR + " = ?";
//        try (Connection connection = db.connect();
//                PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate)) {
//            updateStatement.setString(1, employeeCPR);
//            updateStatement.execute();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//    
//        void deleteEmployee(String employeeCPR) {
//        String sqlDelete = "Delete From " + LoginsContract.TABLE_NAME
//                + " WHERE " + LoginsContract.COLUMN_EMPLOYEECPR + " = ?";
//        try (Connection connection = db.connect();
//                PreparedStatement updateStatement = connection.prepareStatement(sqlDelete)) {
//            updateStatement.setString(1, employeeCPR);
//            updateStatement.execute();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
}
