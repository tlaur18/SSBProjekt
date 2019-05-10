package ssb.domain_layer;

import ssb.domain_layer.document.DocumentManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.util.Pair;
import ssb.data_layer.DatabaseManager;
import ssb.data_layer.contracts.EmployeeContract;
import ssb.data_layer.contracts.HomesContract;
import ssb.data_layer.contracts.PersonsContract;
import ssb.domain_layer.Home;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.callbacks.LoginCallBack;
import ssb.domain_layer.person.Administrator;
import ssb.domain_layer.person.Employee;
import ssb.domain_layer.person.Person;
import ssb.domain_layer.person.Resident;
import ssb.domain_layer.person.Sagsbehandler;
import ssb.domain_layer.person.SocialPædagog;
import ssb.domain_layer.person.Socialrådgiver;
import ssb.domain_layer.person.Vikar;

public class EmployeeManager {

    private final DatabaseManager database = DatabaseManager.getInstance();
    private final InformationBridge informationBridge = InformationBridge.getInstance();
    private List<Home> employeeHomes;
    private static EmployeeManager INSTANCE = null;
    private final ObservableList<Person> allEmployees = FXCollections.observableArrayList();
    private LoginCallBack loginCallBack;
    private Thread employeeDetailsThread;
    private Thread employeeHomesThread;

    private EmployeeManager() {
    }

    public boolean checkValidInput(String userNameInput, String passwordInput, LoginCallBack loginCallBack) {
        this.loginCallBack = loginCallBack;
        String employeeCPRString = database.checkUserLogin(userNameInput, passwordInput);
        if (employeeCPRString == null) {
            loginCallBack.wrongInput();
            return false;
        } else {
            // Two threads proved to be quicker than one thread
            employeeDetailsThread = new Thread(new Task() {
                @Override
                protected HashMap<String, String> call() throws Exception {
                    return database.getEmployeeDetails(employeeCPRString);
                }

                @Override
                protected void succeeded() {
                    HashMap<String, String> employeeDetails = (HashMap<String, String>) getValue();
                    informationBridge.setLoggedInEmployee(setEmployeeDetails(employeeDetails));
                    if (informationBridge.getLoggedInEmployee() instanceof Administrator) {
                        loginCallBack.adminLogin();
                    }
                }
            });
            employeeDetailsThread.start();
            employeeHomesThread = new Thread(new Task() {
                @Override
                protected ArrayList<HashMap<String, String>> call() throws Exception {
                    return database.getHomes(employeeCPRString);
                }

                @Override
                protected void succeeded() {
                    homeDataResult((ArrayList<HashMap<String, String>>) getValue());
                }

            });
            employeeHomesThread.start();
        }
        return true;
    }

    public void homeDataResult(ArrayList<HashMap<String, String>> homes) {
        if (homes.isEmpty()) {
            return;
        }
        employeeHomes = assembleHomes(homes);
        if (employeeHomes.size() > 1) {
            List<String> homeNames = new ArrayList<>();
            for (Home home : employeeHomes) {
                homeNames.add(home.getHomeName());
            }
            loginCallBack.handleMultipleHomes(homeNames);
        } else {
            fillHomeData(employeeHomes.get(0).getHomeName());
            try {
                employeeDetailsThread.join();
                loginCallBack.login();
            } catch (InterruptedException ex) {
                Logger.getLogger(EmployeeManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
                new Thread(() -> {
                    setHomeResidents(home);
                    setResidentDocuments(home);
                }).start();
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
            for (String serializableString : database.getResidentDocuments(resident.getCprNr())) {
                resident.addDocument(DocumentManager.getInstance().decodeDocument(serializableString));
            }
        }
        DocumentManager.getInstance().setDocumentsForHome();
    }

    public void addResidentToHome(int homeId, Resident resident) {
        database.insertResident(resident.getCprNr(), resident.getFirstName(), resident.getLastName(), resident.getPhoneNr(), homeId);
    }

    private List<Home> assembleHomes(ArrayList<HashMap<String, String>> homes) {
        List<Home> allEmployeeHomes = new ArrayList<>();

        for (HashMap<String, String> homeData : homes) {
            String homeName = homeData.get(HomesContract.COLUMN_NAME);
            int homeId = Integer.parseInt(homeData.get(HomesContract.COLUMN_ID));
            allEmployeeHomes.add(new Home(homeName, homeId));
        }

        return allEmployeeHomes;
    }

    private void setHomeResidents(Home home) {
        List<HashMap<String, String>> residentsData = database.getHomeResidents(home.getId());
        for (HashMap<String, String> hashMap : residentsData) {
            home.addResident(makeResidentObject(hashMap));
        }
    }

    public void addEmployeeToDB(Employee employee, String username, String password) {
        // TODO - HER SKAL DER LAVES SÅDAN SÅ NÅR EN MEDARBEJDER BLIVER OPRETTET AT DER BLIVER VALGT ET TILKNYTTET BOSTED SOM SÅ SKAL SÆTTES MED I STEDET FOR VAMMELBY MED ID 1
        database.insertEmployee(employee.getCprNr(), employee.getFirstName(), employee.getLastName(), employee.getPhoneNr(), employee.getEmployeeRole(), 1);
        database.insertEmployeeLogin(employee.getCprNr(), username, password);
    }

    public void loadAllEmployess() {
        for (HashMap<String, String> map : database.GetAllEmployees()) {
            setEmployeeDetails(map);
        }
    }

    public ObservableList<Person> getAllEmployees() {
        return allEmployees;
    }

    public void addEmployeeToObservable(Person person) {
        allEmployees.add(person);
    }

    public void clearEmployeesList() {
        allEmployees.clear();
    }

    public void updateEmployeeDetails(Person person, String userName, String passWord) {
        database.updateEmployeeData(person.getCprNr(), person.getFirstName(), person.getLastName(), person.getPhoneNr());
        database.updateEmployeeLogin(userName, passWord, person.getCprNr());
    }

    public void deleteEmployee(Person person) {
        allEmployees.remove(person);        
    }
    
    public void deleteEmployeeFromDb(Person person) {
        database.deleteEmployee(person.getCprNr());
    }

    public Pair<String, String> getEmployeeLogin(Person person) {
        return database.getEmployeeLogin(person);
    }
}
