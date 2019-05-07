package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import ssb.data_layer.contracts.HomesContract;
import ssb.data_layer.contracts.PersonsHomesLinkContract;

public class HomeData {

    private final DatabaseConnection db = DatabaseConnection.getInstance();

    ArrayList<HashMap<String, String>> getEmployeeHomes(String employeeCPRString) {
        String sql = "SELECT * FROM " + HomesContract.TABLE_NAME + " INNER JOIN " + PersonsHomesLinkContract.TABLE_NAME
                + " ON " + HomesContract.TABLE_NAME + "." + HomesContract.COLUMN_ID + " = "
                + PersonsHomesLinkContract.TABLE_NAME + "." + PersonsHomesLinkContract.COLUMN_HOMES_ID
                + " WHERE " + PersonsHomesLinkContract.COLUMN_PERSON_CPR + " = ?";

        ArrayList<HashMap<String, String>> columnData = new ArrayList<>();

        try (Connection connection = db.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCPRString);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                HashMap<String, String> homeData = new HashMap<>();
                int homeId = result.getInt(HomesContract.COLUMN_ID);
                homeData.put(HomesContract.COLUMN_ID, Integer.toString(homeId));
                String homeName = result.getString(HomesContract.COLUMN_NAME);
                homeData.put(HomesContract.COLUMN_NAME, homeName);
                columnData.add(homeData);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return columnData;
    }

}
