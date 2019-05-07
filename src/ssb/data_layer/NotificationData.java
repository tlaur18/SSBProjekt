package ssb.data_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ssb.data_layer.contracts.DocumentsContract;
import ssb.data_layer.contracts.HomesNotificationsLinkContract;
import ssb.data_layer.contracts.NotificationsContract;

public class NotificationData {

    private final DatabaseConnection db = DatabaseConnection.getInstance();

    public int insertNotification(String message, String author, String creationDate) {
        String sql = "INSERT INTO " + NotificationsContract.TABLE_NAME + "(" + NotificationsContract.COLUMN_MESSAGE
                + ", " + NotificationsContract.COLUMN_AUTHOR + ", " + NotificationsContract.COLUMN_CREATIONDATE
                + ") VALUES (?, ?, ?) RETURNING " + NotificationsContract.COLUMN_ID;

        try (Connection connection = db.connect();
                PreparedStatement insertStatement = connection.prepareStatement(sql)) {

            insertStatement.setString(1, message);
            insertStatement.setString(2, author);
            insertStatement.setString(3, creationDate);
            ResultSet result = insertStatement.executeQuery();
            if (result.next()) {
                return result.getInt(NotificationsContract.COLUMN_ID);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    void insertHomeNotificationLink(int notificationID, int homeID) {
        String sql = "INSERT INTO " + HomesNotificationsLinkContract.TABLE_NAME + "(" + HomesNotificationsLinkContract.COLUMN_HOMES_ID
                + ", " + HomesNotificationsLinkContract.COLUMN_NOTIFICATIONS_ID + ") VALUES (?, ?)";

        try (Connection connection = db.connect();
                PreparedStatement insertStatement = connection.prepareStatement(sql)) {

            insertStatement.setInt(1, homeID);
            insertStatement.setInt(2, notificationID);
            insertStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    ArrayList<ArrayList<String>> loadNotifications(int homeid, int startIndex) {
        String sql = "SELECT * FROM " + HomesNotificationsLinkContract.TABLE_NAME + " INNER JOIN " + NotificationsContract.TABLE_NAME
                + " ON " + HomesNotificationsLinkContract.COLUMN_NOTIFICATIONS_ID + " = " + NotificationsContract.COLUMN_ID
                + " WHERE " + HomesNotificationsLinkContract.TABLE_NAME + "." + HomesNotificationsLinkContract.COLUMN_HOMES_ID + " = ? "
                + " OFFSET ?";

        ArrayList<ArrayList<String>> columnData = new ArrayList<>();
        try (Connection connection = db.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, homeid);
            statement.setInt(2, startIndex);
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

    int getNotificationCount(int homeID) {
        String sql = "SELECT COUNT(" + HomesNotificationsLinkContract.COLUMN_HOMES_ID + ") AS COUNT FROM " + HomesNotificationsLinkContract.TABLE_NAME
                + " WHERE " + HomesNotificationsLinkContract.COLUMN_HOMES_ID + " = ?";

        int count = -1;

        try (Connection connection = db.connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, homeID);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                count = result.getInt("COUNT");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return count;
    }
}
