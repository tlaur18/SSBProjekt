package ssb.domain_layer;

import java.util.ArrayList;
import ssb.data_layer.DatabaseManager;

public class NotificationManager {

    private final DatabaseManager db = DatabaseManager.getInstance();

    public void saveNewNotification(String message, String authorName, String creationDate, int homeID) {
        db.insertNotification(message, authorName, creationDate);
        long notificationID = db.getNotificationIdCount();
        db.insertHomeNotificationLink(Long.toString(notificationID), Integer.toString(homeID));
    }

    public void loadNotifications(int homeID) {
        ArrayList<Integer> notificationIds = db.getNotificationIds(Integer.toString(homeID));
        
//        db.getNotifications(notificationIds);
    }
}
