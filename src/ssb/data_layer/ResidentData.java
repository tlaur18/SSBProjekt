package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import ssb.data_layer.contracts.DocumentsContract;
import ssb.data_layer.contracts.EmployeeResidentLinkContract;
import ssb.data_layer.contracts.PersonsContract;
import ssb.data_layer.contracts.ResidentsContract;

public class ResidentData {

    private final DatabaseManager db = DatabaseManager.getInstance();

    ArrayList<HashMap<String, String>> getEmployeeResidents(String employeeCpr) {
        String sql = "SELECT * FROM " + ResidentsContract.TABLE_NAME + " NATURAL JOIN " + PersonsContract.TABLE_NAME
            + " WHERE " + ResidentsContract.TABLE_NAME + "." + ResidentsContract.COLUMN_CPR + " IN ("
            + "SELECT " + EmployeeResidentLinkContract.COLUMN_RESIDENT_CPR + " FROM " + EmployeeResidentLinkContract.TABLE_NAME
            + " WHERE " + EmployeeResidentLinkContract.COLUMN_EMPLOYEE_CPR + " = ?)";

        ArrayList<HashMap<String, String>> columnData = new ArrayList<>();
        try (Connection connection = db.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCpr);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                HashMap<String, String> residentData = new HashMap<>();
                String residentCpr = result.getString(ResidentsContract.COLUMN_CPR);
                residentData.put(ResidentsContract.COLUMN_CPR, residentCpr);
                String residentFirstName = result.getString(PersonsContract.COLUMN_FIRST_NAME);
                residentData.put(PersonsContract.COLUMN_FIRST_NAME, residentFirstName);
                String residentLastName = result.getString(PersonsContract.COLUMN_LAST_NAME);
                residentData.put(PersonsContract.COLUMN_LAST_NAME, residentLastName);
                String residentPhone = result.getString(PersonsContract.COLUMN_PHONE);
                residentData.put(PersonsContract.COLUMN_PHONE, residentPhone);
                columnData.add(residentData);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return columnData;
    }

    ArrayList<HashMap<String, String>> getResidentDocuments(String residentCpr) {
        String sql = "SELECT * FROM " + DocumentsContract.TABLE_NAME
            + " WHERE " + DocumentsContract.COLUMN_RESIDENT_CPR + " = ?";

        ArrayList<HashMap<String, String>> columnData = new ArrayList<>();
        try (Connection connection = db.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, residentCpr);
            ResultSet result = statement.executeQuery();
            HashMap<String, String> residentData = new HashMap<>();
            while (result.next()) {
                String documentType = result.getString(DocumentsContract.COLUMN_TYPE);
                residentData.put(DocumentsContract.COLUMN_TYPE, documentType);
                String documentCreationDate = result.getString(DocumentsContract.COLUMN_CREATE_DATE);
                residentData.put(DocumentsContract.COLUMN_CREATE_DATE, documentCreationDate);
                String documentEditDate = result.getString(DocumentsContract.COLUMN_EDIT_DATE);
                residentData.put(DocumentsContract.COLUMN_EDIT_DATE, documentEditDate);
                columnData.add(residentData);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return columnData;
    }

}
