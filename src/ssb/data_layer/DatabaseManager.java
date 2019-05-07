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

    public ArrayList<HashMap<String, String>> getHomeResidents(String employeeCpr) {
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

    public void updateDocument(String encodedDocument, String documentID) {
        residentData.updateDocument(encodedDocument, documentID);
    }

    public void insertResident(String residentCpr, String firstName, String lastName, String phoneNumber, String homeId) {
        personData.insertPerson(residentCpr, firstName, lastName, phoneNumber, homeId);
        residentData.insertResident(residentCpr);
    }

    public void insertNotification(String message, String author, String creationDate) {
        notificationData.insertNotification(message, author, creationDate);
    }

    public void insertHomeNotificationLink(String notificationID, String homeID) {
        notificationData.insertHomeNotificationLink(notificationID, homeID);
    }

    public long getMaxNotificationId() {
        return notificationData.getMaxNotificationId();
    }

    public ArrayList<ArrayList<String>> loadNotifications(String homeid, String startIndex) {
        return notificationData.loadNotifications(homeid, startIndex);
    }

    public int getNotificationCount(String homeID) {
        return notificationData.getNotificationCount(homeID);
    }

    public ArrayList<HashMap<String, String>> getHomes(String employeeCPRString) {
        return homeData.getEmployeeHomes(employeeCPRString);
    }

}
