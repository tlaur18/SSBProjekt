package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    
}
