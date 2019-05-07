package ssb.domain_layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ssb.data_layer.DatabaseManager;
import ssb.data_layer.contracts.EmployeeContract;
import ssb.data_layer.contracts.HomesContract;
import ssb.data_layer.contracts.PersonsContract;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Employee.Sagsbehandler;
import ssb.domain_layer.Employee.SocialPædagog;
import ssb.domain_layer.Employee.Socialrådgiver;
import ssb.domain_layer.Employee.Vikar;

public class EmployeeManager {

    private final DatabaseManager db = DatabaseManager.getInstance();
    private final InformationBridge informationBridge = InformationBridge.getInstance();
    private List<Home> employeeHomes;

    public boolean checkUserLogIn(String userNameInput, String passwordInput, LoginCallBack loginCallBack) {
        String employeeCPRString = db.checkUserLogin(userNameInput, passwordInput);
        if (employeeCPRString != null) {
            HashMap<String, String> employeeData = db.getEmployeeDetails(employeeCPRString);
            informationBridge.setLoggedInEmployee(setEmployeeDetails(employeeData));
            employeeHomes = assembleHomes(db.getHomes(employeeCPRString));
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
                break;
            case "sagsbehandler":
                employee = new Sagsbehandler(firstName, lastName, phoneNr, cprNr);
                break;
            case "socialpædagog":
                employee = new SocialPædagog(firstName, lastName, phoneNr, cprNr);
                break;
            case "vikar":
                employee = new Vikar(firstName, lastName, phoneNr, cprNr);
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
        db.insertResident(resident.getCprNr(), resident.getFirstName(), resident.getLastName(), resident.getPhoneNr(), Integer.toString(homeId));
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
        List<HashMap<String, String>> residentsData = db.getHomeResidents(Integer.toString(home.getId()));
        for (HashMap<String, String> hashMap : residentsData) {
            home.addResident(makeResidentObject(hashMap));
        }
    }
}
