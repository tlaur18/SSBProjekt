package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import ssb.data_layer.contracts.DocumentsContract;
import ssb.data_layer.contracts.PersonsContract;
import ssb.data_layer.contracts.PersonsHomesLinkContract;
import ssb.data_layer.contracts.ResidentsContract;

public class ResidentData {

    private final DatabaseConnection db = DatabaseConnection.getInstance();

    ArrayList<HashMap<String, String>> getHomeResidents(String homeId) {
        String sql = "SELECT * FROM " + ResidentsContract.TABLE_NAME + " NATURAL JOIN " + PersonsContract.TABLE_NAME
                + " WHERE " + ResidentsContract.TABLE_NAME + "." + ResidentsContract.COLUMN_CPR + " IN ("
                + "SELECT " + PersonsHomesLinkContract.COLUMN_PERSON_CPR + " FROM " + PersonsHomesLinkContract.TABLE_NAME
                + " WHERE " + PersonsHomesLinkContract.COLUMN_HOMES_ID + " = ?)";

        ArrayList<HashMap<String, String>> columnData = new ArrayList<>();
        try (Connection connection = db.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, homeId);
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

    long getDocumentIdCount() {
        String sqlCountId = "SELECT MAX(" + DocumentsContract.COLUMN_ID + ") AS " + DocumentsContract.COLUMN_ID
                + " FROM " + DocumentsContract.TABLE_NAME;
        long highestID = -1;

        try (Connection connection = db.connect();
                Statement countStatement = connection.createStatement();
                ResultSet countResult = countStatement.executeQuery(sqlCountId)) {
            if (countResult.isBeforeFirst()) {
                highestID = countResult.getLong(DocumentsContract.COLUMN_ID);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return highestID;
    }

    void insertDocument(String encodedDocumentString, String residentCpr) {

        String sql = "INSERT INTO " + DocumentsContract.TABLE_NAME + "(" + DocumentsContract.COLUMN_RESIDENT_CPR
                + ", " + DocumentsContract.COLUMN_SERIALIZABLE + ") VALUES (?, ?)";

        try (Connection connection = db.connect();
                PreparedStatement insertStatement = connection.prepareStatement(sql)) {

            insertStatement.setString(1, residentCpr);
            insertStatement.setString(2, encodedDocumentString);
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void updateDocument(String encodedDocumentString, String documentID) {
        String sqlUpdate = "UPDATE " + DocumentsContract.TABLE_NAME
                + " SET " + DocumentsContract.COLUMN_SERIALIZABLE + " = ?"
                + " WHERE " + DocumentsContract.COLUMN_ID + " = ?";
        try (Connection connection = db.connect();
                PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate)) {
            updateStatement.setString(1, encodedDocumentString);
            updateStatement.setString(2, documentID);
            updateStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void insertResident(String cpr) {
        String sqlResidentInsertion = "INSERT INTO " + ResidentsContract.TABLE_NAME + " VALUES "
                + "(?)";

        try (Connection connection = db.connect();
                PreparedStatement insertStatement = connection.prepareStatement(sqlResidentInsertion)) {
            insertStatement.setString(1, cpr);
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
