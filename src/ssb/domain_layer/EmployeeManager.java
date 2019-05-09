package ssb.domain_layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import ssb.data_layer.DatabaseManager;
import ssb.data_layer.contracts.EmployeeContract;
import ssb.data_layer.contracts.HomesContract;
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
    private List<Home> employeeHomes;
    private static EmployeeManager INSTANCE = null;
    private final ObservableList<Person> allEmployees = FXCollections.observableArrayList();
    
    private EmployeeManager() {}

    public boolean checkUserLogIn(String userNameInput, String passwordInput, LoginCallBack loginCallBack) {
        String employeeCPRString = db.checkUserLogin(userNameInput, passwordInput);
        if (employeeCPRString != null) {
            HashMap<String, String> employeeData = db.getEmployeeDetails(employeeCPRString);
            informationBridge.setLoggedInEmployee(setEmployeeDetails(employeeData));
            if (informationBridge.getLoggedInEmployee() instanceof Administrator) {
                loginCallBack.adminastratorLogin();
                return false;
            }
            employeeHomes = assembleHomes(db.getHomes(employeeCPRString));
            System.out.println(db.getHomes(employeeCPRString));
            if (employeeHomes.size() > 1) {
                List<String> homeNames = new ArrayList<>();
                for (Home home : employeeHomes) {
                    homeNames.add(home.getHomeName());
                }
                loginCallBack.handleMultipleHomes(homeNames);
            } else {
                fillHomeData(employeeHomes.get(0).getHomeName());
            }
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

    public void fillHomeData(String homeName) {
        for (Home home : employeeHomes) {
            if (home.getHomeName().equalsIgnoreCase(homeName)) {
                informationBridge.setCurrentHome(home);
                setHomeResidents(home);
                setResidentDocuments(home);
            }
        }
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
        return employee;
    }

    private Resident makeResidentObject(HashMap<String, String> residentData) {
        String firstName = residentData.get(PersonsContract.COLUMN_FIRST_NAME);
        String lastName = residentData.get(PersonsContract.COLUMN_LAST_NAME);
        String phoneNr = residentData.get(PersonsContract.COLUMN_PHONE);
        String cprNr = residentData.get(PersonsContract.COLUMN_CPR);
        return new Resident(firstName, lastName, phoneNr, cprNr);
    }

    private void setResidentDocuments(Home home) {
        for (Resident resident : home.getResidents()) {
            for (String serializableString : db.getResidentDocuments(resident.getCprNr())) {
                resident.addDocument(DocumentManager.getInstance().decodeDocument(serializableString));
            }
        }
    }

    public void addResidentToHome(int homeId, Resident resident) {
        db.insertResident(resident.getCprNr(), resident.getFirstName(), resident.getLastName(), resident.getPhoneNr(), homeId);
    }
    public void addEmployeeToHome(int homeId, Employee employee) {
        
    }

    private List<Home> assembleHomes(ArrayList<HashMap<String, String>> homes) {
        List<Home> employeeHomes = new ArrayList<>();

        for (HashMap<String, String> homeData : homes) {
            String homeName = homeData.get(HomesContract.COLUMN_NAME);
            int homeId = Integer.parseInt(homeData.get(HomesContract.COLUMN_ID));
            employeeHomes.add(new Home(homeName, homeId));
        }

        return employeeHomes;
    }

    private void setHomeResidents(Home home) {
        List<HashMap<String, String>> residentsData = db.getHomeResidents(home.getId());
        for (HashMap<String, String> hashMap : residentsData) {
            home.addResident(makeResidentObject(hashMap));
        }
    }

    public void addEmployeeToDB(Employee employee, String username, String password, int homeID) {
        db.insertEmployee(employee.getCprNr(), employee.getFirstName(), employee.getLastName(), employee.getPhoneNr(), employee.getEmployeeRole(), homeID);
        db.insertEmployeeLogin(employee.getCprNr(), username, password);
        addEmployeeToObservable(employee);
    }

    public void loadAllEmployess() {

        for (HashMap<String, String> map : db.GetAllEmployees()) {
            setEmployeeDetails(map);
        }
    }

    public ObservableList<Person> getAllEmployees() {
        return allEmployees;
    }

    public void addEmployeeToObservable(Person person) {
        allEmployees.add(person);
    }

    public void deleteEmployeeFromObservable(Person person) {
        allEmployees.remove(person);
    }
    public void clearObservableList() {
        allEmployees.clear();
    }

    public void updateEmployeeDetails(Person person, String userName, String passWord, int homeID) {
        db.updateEmployeeData(person.getCprNr(), person.getFirstName(), person.getLastName(), person.getPhoneNr(), homeID);
        db.updateEmployeeLogin(userName, passWord, person.getCprNr());
    }

    public void deleteEmployee(String employeeCPR) {
        db.deleteEmployee(employeeCPR);
    }

    public Pair<String, String> getEmployeeLogin(Person person) {
        return db.getEmployeeLogin(person);
    }
}
