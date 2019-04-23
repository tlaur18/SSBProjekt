package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ssb.data_layer.contracts.LoginsContract;

class LogInData implements Runnable {

    private final DatabaseManager db = DatabaseManager.getInstance();

    @Override
    public void run() {
        // TODO - some sort of thread with callback on finihsed so it doesn't lock up the main thread.
    }

    String getUserLoginWith(String enteredUserName, String enteredPassword) {
        String sql = "SELECT " + LoginsContract.COLUMN_EMPLOYEECPR
            + " FROM " + LoginsContract.TABLE_NAME
            + " WHERE " + LoginsContract.COLUMN_USERNAME + " = ?"
            + " AND " + LoginsContract.COLUMN_PASSWORD + " = ?";

        String employeeCprNr = null;
        try (Connection connection = db.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, enteredUserName);
            statement.setString(2, enteredPassword);
            ResultSet result = statement.executeQuery();
            if (result.isBeforeFirst()) {
                employeeCprNr = result.getString(LoginsContract.COLUMN_EMPLOYEECPR);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return employeeCprNr;
    }
}
