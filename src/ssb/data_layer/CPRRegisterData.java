package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import ssb.data_layer.contracts.CPRRegisterPersonsContract;

public class CPRRegisterData {

    private final DatabaseConnectionCPRRegister db = DatabaseConnectionCPRRegister.getInstance();

    public HashMap<String, String> getPersonInformation(String cprToSearch) {
        String sql = "SELECT * FROM " + CPRRegisterPersonsContract.TABLE_NAME
                + " WHERE " + CPRRegisterPersonsContract.COLUMN_CPR + " = ?";

        HashMap<String, String> columnData = new HashMap();
        
        try (Connection connection = db.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cprToSearch);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                columnData.put(CPRRegisterPersonsContract.COLUMN_CPR, result.getString(CPRRegisterPersonsContract.COLUMN_CPR));
                columnData.put(CPRRegisterPersonsContract.COLUMN_FIRST_NAME, result.getString(CPRRegisterPersonsContract.COLUMN_FIRST_NAME));
                columnData.put(CPRRegisterPersonsContract.COLUMN_LAST_NAME, result.getString(CPRRegisterPersonsContract.COLUMN_LAST_NAME));
                columnData.put(CPRRegisterPersonsContract.COLUMN_PHONE, result.getString(CPRRegisterPersonsContract.COLUMN_PHONE));
                columnData.put(CPRRegisterPersonsContract.COLUMN_MAIL, result.getString(CPRRegisterPersonsContract.COLUMN_MAIL));
                columnData.put(CPRRegisterPersonsContract.COLUMN_ZIP_CODE, Integer.toString(result.getInt(CPRRegisterPersonsContract.COLUMN_ZIP_CODE)));
                columnData.put(CPRRegisterPersonsContract.COLUMN_CITY, result.getString(CPRRegisterPersonsContract.COLUMN_CITY));
                columnData.put(CPRRegisterPersonsContract.COLUMN_STREET, result.getString(CPRRegisterPersonsContract.COLUMN_STREET));
                columnData.put(CPRRegisterPersonsContract.COLUMN_STREET_NR, Integer.toString(result.getInt(CPRRegisterPersonsContract.COLUMN_STREET_NR)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return columnData;
    }
}
