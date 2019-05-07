package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import ssb.data_layer.contracts.PersonsContract;
import ssb.data_layer.contracts.PersonsHomesLinkContract;

public class PersonData {

    private final DatabaseConnection db = DatabaseConnection.getInstance();

    void insertPerson(String personCpr, String firstName, String lastName, String phoneNumber, int homeId) {
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
        insertPersonHomeLink(personCpr, homeId);
    }

    public void insertPersonHomeLink(String personCpr, int homeId) {
        String sqlResidentInsertion = "INSERT INTO " + PersonsHomesLinkContract.TABLE_NAME + " VALUES "
            + "(?, ?)";
        try (Connection connection = db.connect();
            PreparedStatement insertStatement = connection.prepareStatement(sqlResidentInsertion)) {
            insertStatement.setString(1, personCpr);
            insertStatement.setInt(2, homeId);
            insertStatement.execute();
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
            System.out.println(updateStatement);
            updateStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
