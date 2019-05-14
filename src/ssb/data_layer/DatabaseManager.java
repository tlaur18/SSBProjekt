package ssb.data_layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;
import ssb.domain_layer.Home;
import ssb.domain_layer.person.Person;

public class DatabaseManager {

    private static DatabaseManager instance = null;
    private final LogInData logInData = new LogInData();
    private final PersonData personData = new PersonData();
    private final EmployeeWorkData employeeWorkData = new EmployeeWorkData();
    private final ResidentData residentData = new ResidentData();
    private final NotificationData notificationData = new NotificationData();
    private final HomeData homeData = new HomeData();

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

    public void insertResident(String residentCpr, String firstName, String lastName, String phoneNumber, int homeID) {
        personData.insertPerson(residentCpr, firstName, lastName, phoneNumber);
        personData.insertPersonHomeLink(residentCpr, homeID);
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

    public void insertEmployee(String employeecpr, String firstName, String lastName, String phoneNumber, String employeeRole) {
        personData.insertPerson(employeecpr, firstName, lastName, phoneNumber);
        employeeWorkData.insertEmployee(employeecpr, employeeRole);
    }

    public ArrayList<HashMap<String, String>> getAllEmployees() {
        return employeeWorkData.loadAllEmployees();
    }

    public void updateEmployeeData(String employeeCPR, String firstName, String lastName, String phoneNr) {
        personData.updatePerson(employeeCPR, firstName, lastName, phoneNr);
    }

    public void updateEmployeeLogin(String userName, String passWord, String employeeCPR) {
        logInData.updateLogin(userName, passWord, employeeCPR);
    }

    public void deleteEmployee(String employeeCPR) {
        personData.deletePerson(employeeCPR);
    }

    public Pair<String, String> getEmployeeLogin(Person person) {
        return logInData.getLoginData(person.getCprNr());
    }

    public void insertEmployeeLogin(String cprNr, String username, String password) {
        logInData.insertLogin(cprNr, username, password);
    }

    public void insertPersonHomeLink(String employeeCPR, int homeID) {
        personData.insertPersonHomeLink(employeeCPR, homeID);
    }

    public void deletePersonHomeLink(String personCprNr, int homeID) {
        homeData.deleteEmployeeHomeLink(personCprNr, homeID);
    }

    public ArrayList<HashMap<String, String>> getallEmployeeHomes(String employeeCPR) {
        return homeData.getEmployeeHomes(employeeCPR);
    }

    public ArrayList<HashMap<String, String>> getAllHomes() {
        return homeData.getAllHomes();
    }

}
