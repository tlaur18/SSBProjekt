package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import ssb.data_layer.contracts.DocumentsContract;
import ssb.data_layer.contracts.EmployeeResidentLinkContract;
import ssb.data_layer.contracts.PersonsContract;
import ssb.data_layer.contracts.ResidentsContract;
import ssb.domain_layer.Document;

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

    ArrayList<String> getResidentDocuments(String residentCpr) {

        String sql = "SELECT " + DocumentsContract.COLUMN_SERIALIZABLE + " FROM " + DocumentsContract.TABLE_NAME
            + " WHERE " + DocumentsContract.COLUMN_RESIDENT_CPR + " = ?";

        ArrayList<String> columnData = new ArrayList<>();

        try (Connection connection = db.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, residentCpr);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                columnData.add(result.getString(DocumentsContract.COLUMN_SERIALIZABLE));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return columnData;
    }

    long insertDocument(Document document, String residentCpr) {
        
        String sqlCountId = "SELECT MAX(" + DocumentsContract.COLUMN_ID + ") AS " + DocumentsContract.COLUMN_ID
            + " FROM " + DocumentsContract.TABLE_NAME;

        String sql = "INSERT INTO " + DocumentsContract.TABLE_NAME + "(" + DocumentsContract.COLUMN_RESIDENT_CPR
            + ", " + DocumentsContract.COLUMN_SERIALIZABLE + ") VALUES (?, ?)";
        long idOfNewDocument = -1;

        try (Connection connection = db.connect();
            Statement countStatement = connection.createStatement();
            PreparedStatement insertStatement = connection.prepareStatement(sql)) {

            ResultSet countResult = countStatement.executeQuery(sqlCountId);
            if (countResult.isBeforeFirst()) {
                idOfNewDocument = countResult.getLong(DocumentsContract.COLUMN_ID) + 1;
                System.out.println(idOfNewDocument);
                document.setId(idOfNewDocument);
            }

            insertStatement.setString(1, residentCpr);
            insertStatement.setString(2, document.encodeDocument());
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return idOfNewDocument;
    }
}
