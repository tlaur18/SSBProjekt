package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import ssb.data_layer.contracts.PersonsContract;
import ssb.data_layer.contracts.PersonsHomesLinkContract;

public class PersonData {
    
    private final DatabaseConnection db = DatabaseConnection.getInstance();
    
    void insertPerson(String personCpr, String firstName, String lastName, String phoneNumber, String homeId) {
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
    
    public void insertPersonHomeLink(String personCpr, String homeId) {
        String sqlResidentInsertion = "INSERT INTO " + PersonsHomesLinkContract.TABLE_NAME + " VALUES "
                + "(?, ?)";
        try (Connection connection = db.connect();
                PreparedStatement insertStatement = connection.prepareStatement(sqlResidentInsertion)) {
            insertStatement.setString(1, personCpr);
            insertStatement.setString(2, homeId);
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
