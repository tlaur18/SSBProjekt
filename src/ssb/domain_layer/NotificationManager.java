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
        db.insertNotification(message, authorName, creationDate);
        long notificationID = db.getNotificationIdCount();
        db.insertHomeNotificationLink(Long.toString(notificationID), Integer.toString(homeID));
    }

    public void loadNotifications(int homeID) {
//        ArrayList<Integer> notificationIds = db.getNotificationIds(Integer.toString(homeID));
        ArrayList<ArrayList<String>> notificationData = db.getNotifications(Integer.toString(homeID));
        
        for (ArrayList<String> arrayList : notificationData) {
            String message = arrayList.get(1);
            String author = arrayList.get(2);
            String date = arrayList.get(3);
            notifications.add(new Notification(message, author, date));
        }
        
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }
    
    

}
