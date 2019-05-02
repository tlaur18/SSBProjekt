package ssb.domain_layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ssb.data_layer.DatabaseManager;
import ssb.data_layer.contracts.EmployeeContract;
import ssb.data_layer.contracts.PersonsContract;
import ssb.domain_layer.Employee.Administrator;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Employee.Sagsbehandler;
import ssb.domain_layer.Employee.SocialPædagog;
import ssb.domain_layer.Employee.Socialrådgiver;
import ssb.domain_layer.Employee.Vikar;

public class EmployeeManager {

    private final DatabaseManager db = DatabaseManager.getInstance();
    private final InformationBridge informationBridge = InformationBridge.getInstance();
    private static EmployeeManager INSTANCE = null;
    private ObservableList<Employee> allEmployees = FXCollections.observableArrayList();

    public boolean checkUserLogIn(String userNameInput, String passwordInput) {
        String employeeCPRString = db.checkUserLogin(userNameInput, passwordInput);
        if (employeeCPRString != null) {
            HashMap<String, String> employeeData = db.getEmployeeDetails(employeeCPRString);
            informationBridge.setLoggedInEmployee(setEmployeeDetails(employeeData));
            setEmployeeResidents(employeeCPRString);
            setResidentDocuments();
            return true;
        }
        return false;
    }
    public static EmployeeManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmployeeManager();
        }
        return INSTANCE;
    }

    private Employee setEmployeeDetails(HashMap<String, String> employeeHashMap) {
        Employee employee = null;
        String role = employeeHashMap.get(EmployeeContract.COLUMN_ROLE);
        String firstName = employeeHashMap.get(PersonsContract.COLUMN_FIRST_NAME);
        String lastName = employeeHashMap.get(PersonsContract.COLUMN_LAST_NAME);
        String phoneNr = employeeHashMap.get(PersonsContract.COLUMN_PHONE);
        String cprNr = employeeHashMap.get(PersonsContract.COLUMN_CPR);
        switch (role) {
            case "socialrådgiver":
                employee = new Socialrådgiver(firstName, lastName, phoneNr, cprNr);
                allEmployees.add(employee);
                break;
            case "sagsbehandler":
                employee = new Sagsbehandler(firstName, lastName, phoneNr, cprNr);
                allEmployees.add(employee);
                break;
            case "socialpædagog":
                employee = new SocialPædagog(firstName, lastName, phoneNr, cprNr);
                allEmployees.add(employee);
                break;
            case "vikar":
                employee = new Vikar(firstName, lastName, phoneNr, cprNr);
                allEmployees.add(employee);
                break;
            case "admin":
                employee = new Administrator(firstName, lastName, phoneNr, cprNr);
                allEmployees.add(employee);
                break;
        }
        System.out.println("added employeee" + employee);
        return employee;
    }

    private void setEmployeeResidents(String employeeCpr) {
        ArrayList<HashMap<String, String>> residents = db.getEmployeeAssoResidents(employeeCpr);
        for (HashMap<String, String> residentData : residents) {
            informationBridge.getLoggedInEmployee().addResident(makeResidentObject(residentData));
        }
    }

    private Resident makeResidentObject(HashMap<String, String> residentData) {
        String firstName = residentData.get(PersonsContract.COLUMN_FIRST_NAME);
        String lastName = residentData.get(PersonsContract.COLUMN_LAST_NAME);
        String phoneNr = residentData.get(PersonsContract.COLUMN_PHONE);
        String cprNr = residentData.get(PersonsContract.COLUMN_CPR);
        return new Resident(firstName, lastName, phoneNr, cprNr);
    }

    private void setResidentDocuments() {
        for (Resident resident : informationBridge.getLoggedInEmployee().getResidents()) {
            for (String serializableString : db.getResidentDocuments(resident.getCprNr())) {
                resident.addDocument(DocumentManager.getInstance().decodeDocument(serializableString));
            }
        }
    }

    public void addResidentToEmployee(String employeeCpr, Resident resident) {
        DatabaseManager.getInstance().insertResident(resident.getCprNr(), resident.getFirstName(),
                resident.getLastName(), resident.getPhoneNr(), employeeCpr);
    }

    public void addEmployeeToDB(Employee employee, String username, String password) {
        DatabaseManager.getInstance().insertEmployee(employee.getCprNr(), employee.getFirstName(), employee.getLastName(), employee.getPhoneNr(), employee.getEmployeeRole(), username, password);
        
    }
    public void loadAllEmployess() {
        
        for (HashMap<String, String> map : db.GetAllEmployees()) {
            setEmployeeDetails(map);
        }
    }
    public ObservableList<Employee> getAllEmployees() {
        return allEmployees;
    }
}
