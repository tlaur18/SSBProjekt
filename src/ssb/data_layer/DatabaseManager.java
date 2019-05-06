package ssb.data_layer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import ssb.domain_layer.Document;
import ssb.domain_layer.EmployeeManager;
import ssb.domain_layer.Person;
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
    public void insertEmployee(String employeecpr, String firstName, String lastName, String phoneNumber, String employeeRole, String employeeUsername, String employeePassword){
        EmployeeWorkData employeeWorkData = new EmployeeWorkData();
        employeeWorkData.insertEmployeePerson(employeecpr, firstName, lastName, phoneNumber);
        employeeWorkData.insertEmployee(employeecpr, employeeRole);
        employeeWorkData.insertEmployeeLogin(employeecpr, employeeUsername, employeePassword);
    }
    public ArrayList<HashMap<String, String>> GetAllEmployees() {
        EmployeeWorkData employeeWorkData = new EmployeeWorkData();
        return employeeWorkData.loadAllEmployees();
    }
    public void updateEmployeeData(Person person) {
        EmployeeWorkData employeeWorkData = new EmployeeWorkData();
        employeeWorkData.updateEmployee(person.getCprNr(), person.getFirstName(), person.getLastName(), person.getPhoneNr());
    }
    public void updateEmployeeLogin(String userName, String passWord, String employeeCPR) {
        LogInData loginData = new LogInData();
        loginData.updateEmployeeLogin(userName, passWord, employeeCPR);
    }
}
