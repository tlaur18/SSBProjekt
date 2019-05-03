package ssb.domain_layer;

import java.util.ArrayList;
import ssb.data_layer.DatabaseManager;

public class NotificationManager {

    private static NotificationManager INSTANCE = null;
    private final DatabaseManager db = DatabaseManager.getInstance();
    private ArrayList<Notification> notifications = new ArrayList<>();

    private NotificationManager() {
    }

    public static NotificationManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationManager();
        }
        return INSTANCE;
    }

    public void saveNewNotification(String message, String authorName, String creationDate, int homeID) {
        notifications.add(new Notification(notifications.get(notifications.size() - 1).getId(), message, authorName, creationDate));        //maybe change this way of getting the largest ID.
        db.insertNotification(message, authorName, creationDate);
        long notificationID = db.getMaxNotificationId();
        db.insertHomeNotificationLink(Long.toString(notificationID), Integer.toString(homeID));
    }
    
    public void checkForNewNotifications(long homeID) {
        int listNotificationCount = notifications.size();
        int dbNotificationCount = db.getNotificationCount(Long.toString(homeID));

        if (listNotificationCount < dbNotificationCount) {
            ArrayList<ArrayList<String>> notificationData = loadNotifications(homeID, listNotificationCount);

            for (ArrayList<String> arrayList : notificationData) {
                int id = Integer.parseInt(arrayList.get(0));
                String message = arrayList.get(1);
                String author = arrayList.get(2);
                String date = arrayList.get(3);
                notifications.add(new Notification(id, message, author, date));
            }
        }
    }

    private ArrayList<ArrayList<String>> loadNotifications(long homeID, int startIndex) {
        return db.loadNotifications(Long.toString(homeID), Integer.toString(startIndex));
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    void clearNotifications() {
        this.notifications = new ArrayList<>();
    }
}
