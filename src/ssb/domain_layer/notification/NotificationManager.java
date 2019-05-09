package ssb.domain_layer.notification;

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

    public void saveNewNotification(String message, String authorName, String creationDate, int homeId) {
        int notificationId = db.insertNotification(message, authorName, creationDate, homeId);
        notifications.add(new Notification(notificationId, message, authorName, creationDate));
    }

    public void checkForNewNotifications(int homeID) {
        int listNotificationCount = notifications.size();
        int dbNotificationCount = db.getNotificationCount(homeID);

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
        return db.loadNotifications((int)homeID, startIndex);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void clearNotifications() {
        this.notifications = new ArrayList<>();
    }
}
