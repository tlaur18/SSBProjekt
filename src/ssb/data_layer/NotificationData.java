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
import ssb.data_layer.contracts.HomesContract;
import ssb.data_layer.contracts.HomesNotificationsLinkContract;
import ssb.data_layer.contracts.NotificationsContract;

public class NotificationData {

    private final DatabaseManager db = DatabaseManager.getInstance();

    public void insertNotification(String message, String author, String creationDate) {
        String sql = "INSERT INTO " + NotificationsContract.TABLE_NAME + "(" + NotificationsContract.COLUMN_MESSAGE
                + ", " + NotificationsContract.COLUMN_AUTHOR + ", " + NotificationsContract.COLUMN_CREATIONDATE + ") VALUES (?, ?, ?)";

        try (Connection connection = db.connect();
                PreparedStatement insertStatement = connection.prepareStatement(sql)) {

            insertStatement.setString(1, message);
            insertStatement.setString(2, author);
            insertStatement.setString(3, creationDate);
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    long getMaxNotificationId() {
        String sqlCountId = "SELECT MAX(" + NotificationsContract.COLUMN_ID + ") AS " + NotificationsContract.COLUMN_ID
                + " FROM " + NotificationsContract.TABLE_NAME;
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

    void insertHomeNotificationLink(String notificationID, String homeID) {
        String sql = "INSERT INTO " + HomesNotificationsLinkContract.TABLE_NAME + "(" + HomesNotificationsLinkContract.COLUMN_HOMES_ID
                + ", " + HomesNotificationsLinkContract.COLUMN_NOTIFICATIONS_ID + ") VALUES (?, ?)";

        try (Connection connection = db.connect();
                PreparedStatement insertStatement = connection.prepareStatement(sql)) {

            insertStatement.setString(1, homeID);
            insertStatement.setString(2, notificationID);
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    ArrayList<ArrayList<String>> getNotifications(String homeid) {
        String sql = "SELECT * FROM " + HomesNotificationsLinkContract.TABLE_NAME + " INNER JOIN " + NotificationsContract.TABLE_NAME
                + " ON " + HomesNotificationsLinkContract.COLUMN_NOTIFICATIONS_ID + " = " + NotificationsContract.COLUMN_ID
                + " WHERE " + HomesNotificationsLinkContract.TABLE_NAME + "." + HomesNotificationsLinkContract.COLUMN_HOMES_ID + " = ?";

        ArrayList<ArrayList<String>> columnData = new ArrayList<>();
        try (Connection connection = db.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, homeid);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ArrayList<String> notificationData = new ArrayList<>();
                String notificationID = result.getString(NotificationsContract.COLUMN_ID);
                String notificationMessage = result.getString(NotificationsContract.COLUMN_MESSAGE);
                String notificationAuthor = result.getString(NotificationsContract.COLUMN_AUTHOR);
                String notificationDate = result.getString(NotificationsContract.COLUMN_CREATIONDATE);
                notificationData.add(notificationID);
                notificationData.add(notificationMessage);
                notificationData.add(notificationAuthor);
                notificationData.add(notificationDate);
                columnData.add(notificationData);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return columnData;
    }

    int getNotificationCount(String homeID) {
        String sql = "SELECT COUNT() FROM " + HomesNotificationsLinkContract.TABLE_NAME
                + " WHERE " + HomesNotificationsLinkContract.COLUMN_HOMES_ID + " = ?";
        
        int count = -1;
        
        try (Connection connection = db.connect();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, homeID);
            ResultSet result = statement.executeQuery();
            if (result.isBeforeFirst()) {
                count = result.getInt("COUNT()");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return count;
    }
}
