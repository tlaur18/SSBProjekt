package ssb.data_layer;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseManager {

    private static DatabaseManager instance = null;
    private LogInData logInData = new LogInData();
    private PersonData personData = new PersonData();
    private EmployeeWorkData employeeWorkData = new EmployeeWorkData();
    private ResidentData residentData = new ResidentData();
    private NotificationData notificationData = new NotificationData();
    private HomeData homeData = new HomeData();

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public String checkUserLogin(String username, String password) {
        return logInData.getUserLoginWith(username, password);
    }

    public HashMap<String, String> getEmployeeDetails(String employeeCpr) {
        return employeeWorkData.getEmployeeData(employeeCpr);
    }

    public ArrayList<HashMap<String, String>> getHomeResidents(int employeeCpr) {
        return residentData.getHomeResidents(employeeCpr);
    }

    public ArrayList<String> getResidentDocuments(String residentCpr) {
        return residentData.getResidentDocuments(residentCpr);
    }

    public long getDocumentIdCount() {
        return residentData.getDocumentIdCount();
    }

    public void insertDocument(String encodedDocumentString, String residentCpr) {
        residentData.insertDocument(encodedDocumentString, residentCpr);
    }

    public void updateDocument(String encodedDocument, int documentID) {
        residentData.updateDocument(encodedDocument, documentID);
    }

    public void insertResident(String residentCpr, String firstName, String lastName, String phoneNumber, int homeId) {
        personData.insertPerson(residentCpr, firstName, lastName, phoneNumber, homeId);
        residentData.insertResident(residentCpr);
    }

    public int insertNotification(String message, String author, String creationDate, int homeId) {
        int notificationID = notificationData.insertNotification(message, author, creationDate);
        notificationData.insertHomeNotificationLink(notificationID, homeId);
        return notificationID;
    }

    public ArrayList<ArrayList<String>> loadNotifications(int homeid, int startIndex) {
        return notificationData.loadNotifications(homeid, startIndex);
    }

    public int getNotificationCount(int homeID) {
        return notificationData.getNotificationCount(homeID);
    }

    public ArrayList<HashMap<String, String>> getHomes(String employeeCPRString) {
        return homeData.getEmployeeHomes(employeeCPRString);
    }

}
