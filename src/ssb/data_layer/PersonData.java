package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import ssb.data_layer.contracts.PersonsContract;
import ssb.data_layer.contracts.PersonsHomesLinkContract;

public class PersonData {

    private final DatabaseConnection db = DatabaseConnection.getInstance();

    void insertPerson(String personCpr, String firstName, String lastName, String phoneNumber) {
        String sqlPersonInsertion = "INSERT INTO " + PersonsContract.TABLE_NAME + " VALUES "
            + "(?, ?, ?, ?)";
        try (Connection connection = db.connect();
            PreparedStatement insertStatement = connection.prepareStatement(sqlPersonInsertion)) {
            insertStatement.setString(1, personCpr);
            insertStatement.setString(2, firstName);
            insertStatement.setString(3, lastName);
            insertStatement.setString(4, phoneNumber);
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void insertPersonHomeLink(String personCPR, int homeID) {
     String sqlUpdate = "INSERT INTO " + PersonsHomesLinkContract.TABLE_NAME
                + " SELECT ?, ?"
                + " WHERE NOT EXISTS (SELECT * FROM " + PersonsHomesLinkContract.TABLE_NAME 
                + " WHERE " + PersonsHomesLinkContract.COLUMN_PERSON_CPR + " = ? AND " 
                + PersonsHomesLinkContract.COLUMN_HOMES_ID + " = ?)";
        try (Connection connection = db.connect();
                PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate)) {
            updateStatement.setString(1, personCPR);
            updateStatement.setInt(2, homeID);
            updateStatement.setString(3, personCPR);
            updateStatement.setInt(4, homeID);
            updateStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void deletePerson(String personCPR) {
        String sqlUpdate = "DELETE FROM " + PersonsContract.TABLE_NAME
            + " WHERE " + PersonsContract.COLUMN_CPR + " = ?";
        try (Connection connection = db.connect();
            PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate)) {
            updateStatement.setString(1, personCPR);
            updateStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void updatePerson(String employeeCpr, String firstName, String lastName, String phoneNumber) {
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
            updateStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
