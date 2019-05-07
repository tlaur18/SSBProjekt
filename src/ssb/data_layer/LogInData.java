package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.util.Pair;
import ssb.data_layer.contracts.LoginsContract;

class LogInData implements Runnable {

    private final DatabaseConnection db = DatabaseConnection.getInstance();

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
            if (result.next()) {
                employeeCprNr = result.getString(LoginsContract.COLUMN_EMPLOYEECPR);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return employeeCprNr;
    }

    void updateLogin(String userName, String passWord, String employeeCPR) {
        String sqlUpdate = "UPDATE " + LoginsContract.TABLE_NAME
            + " SET "
            + LoginsContract.COLUMN_USERNAME + " = ?, "
            + LoginsContract.COLUMN_PASSWORD + " = ?"
            + " WHERE " + LoginsContract.COLUMN_EMPLOYEECPR + " = ?";
        try (Connection connection = db.connect();
            PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate)) {
            updateStatement.setString(1, userName);
            updateStatement.setString(2, passWord);
            updateStatement.setString(3, employeeCPR);
            updateStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void insertLogin(String employeeCPR, String username, String password) {
        String sqlEmployeeLoginInsertion = "INSERT INTO " + LoginsContract.TABLE_NAME + " VALUES "
            + "(?, ?, ?)";

        try (Connection connection = db.connect();
            PreparedStatement insertStatement = connection.prepareStatement(sqlEmployeeLoginInsertion)) {
            insertStatement.setString(1, employeeCPR);
            insertStatement.setString(2, username);
            insertStatement.setString(3, password);
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    Pair<String, String> getLoginData(String employeeCPR) {
        String sql = "SELECT * FROM " + LoginsContract.TABLE_NAME
            + " WHERE " + LoginsContract.COLUMN_EMPLOYEECPR + " = ?";

        try (Connection connection = db.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCPR);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String username = result.getString(LoginsContract.COLUMN_USERNAME);
                String password = result.getString(LoginsContract.COLUMN_PASSWORD);
                return new Pair<>(username, password);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
