package ssb.data_layer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import ssb.domain_layer.Document;
import ssb.domain_layer.Resident;

public class DatabaseManager {

    private static final String DATABASE_NAME = "ssbprojektdata.db";

    private static DatabaseManager instance = null;

    private DatabaseManager() {
    }

    Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Could not connect to the database");
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public String checkUserLogin(String username, String password) {
        LogInData logInData = new LogInData();
        return logInData.getUserLoginWith(username, password);
    }

    public HashMap<String, String> getEmployeeDetails(String employeeCpr) {
        EmployeeWorkData employeeWorkData = new EmployeeWorkData();
        return employeeWorkData.getEmployeeData(employeeCpr);
    }

    public ArrayList<HashMap<String, String>> getEmployeeAssoResidents(String employeeCpr) {
        ResidentData residentData = new ResidentData();
        return residentData.getEmployeeResidents(employeeCpr);
    }

    public ArrayList<String> getResidentDocuments(String residentCpr) {
        ResidentData residentData = new ResidentData();
        return residentData.getResidentDocuments(residentCpr);
    }
    
    public long getDocumentIdCount() {
        ResidentData residentData = new ResidentData();
        return residentData.getDocumentIdCount();
    }

    public void insertDocument(String encodedDocumentString, String residentCpr) {
        ResidentData residentData = new ResidentData();
        residentData.insertDocument(encodedDocumentString, residentCpr);
    }

    public void updateDocument(String encodedDocument, String documentID) {
        ResidentData residentData = new ResidentData();
        residentData.updateDocument(encodedDocument, documentID);
    }
    
    public void insertResident(String residentCpr, String firstName, String lastName, String phoneNumber, String employeeCpr) {
        ResidentData residentData = new ResidentData();
        residentData.insertResident(residentCpr, firstName, lastName, phoneNumber, employeeCpr);
    }
    
    public void insertNotification(String message, String author, String creationDate) {
        NotificationData notificationData = new NotificationData();
        notificationData.insertNotification(message, author, creationDate);
    }
    
    public void insertHomeNotificationLink(String notificationID, String homeID) {
        NotificationData notificationData = new NotificationData();
        notificationData.insertHomeNotificationLink(notificationID, homeID);
    }

    public long getNotificationIdCount() {
        NotificationData notificationData = new NotificationData();
        return notificationData.getNotificationIdCount();
    }
    
    public ArrayList<Integer> getNotificationIds(String homeID) {
        NotificationData notificationData = new NotificationData();
        return notificationData.getNotificationIds(homeID);
    }
    
    public ArrayList<ArrayList<String>> getNotifications(String homeid){
        NotificationData notificationData = new NotificationData();
        return notificationData.getNotifications(homeid);
    }
    
    
}
