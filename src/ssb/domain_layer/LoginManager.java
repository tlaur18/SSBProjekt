package ssb.domain_layer;

import java.util.HashMap;
import java.util.Map;
import ssb.data_layer.DatabaseManager;
import ssb.data_layer.contracts.EmployeeContract;
import ssb.data_layer.contracts.PersonsContract;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Employee.Sagsbehandler;
import ssb.domain_layer.Employee.SocialPædagog;
import ssb.domain_layer.Employee.Socialrådgiver;
import ssb.domain_layer.Employee.Vikar;

public class LoginManager {

    private final DatabaseManager db = DatabaseManager.getInstance();
    private final InformationBridge informationBridge = InformationBridge.getINSTANCE();

    public String checkUserLogIn(String userNameInput, String passwordInput) {
        String employeeCPRString = db.checkUserLogin(userNameInput, passwordInput);
        if (employeeCPRString != null) {
            // TODO - Set loggedInUser in information brdige with all the right information based on the two methods down below
            // Also get all information from database again.
            HashMap<String, String> employeeData = db.getEmployeeDetails(employeeCPRString);
            informationBridge.setLoggedInEmployee(setEmployeeDetails(employeeData));
//            setEmployeeResidents();
//            setResidentDocuments();
        }
        return null;
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

    private void setEmployeeResidents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setResidentDocuments() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    public Employee getEmployeeDetails(String employeeCpr) {
//        for (Entry<String, List<String>> userData : logInData.loadEmployeeData().entrySet()) {
//            if (userData.getKey().trim().equals(employeeID)) {
//                List<String> employeeData = userData.getValue();
//                switch (employeeData.get(employeeData.size() - 1).trim()) {
//                    case "socialrådgiver":
//                        return new Socialrådgiver(employeeData.get(0), employeeData.get(1), employeeData.get(2), employeeData.get(3));
//                    case "sagsbehandler":
//                        return new Sagsbehandler(employeeData.get(0), employeeData.get(1), employeeData.get(2), employeeData.get(3));
//                    case "socialpædagog":
//                        return new SocialPædagog(employeeData.get(0), employeeData.get(1), employeeData.get(2), employeeData.get(3));
//                    case "vikar":
//                        return new Vikar(employeeData.get(0), employeeData.get(1), employeeData.get(2), employeeData.get(3));
//                }
//            }
//        }
//        return null;
//    }
//    public void setEmployeeWorkData(Employee employee) throws ParseException {
//        List<Resident> associatedResidents = new ArrayList<>();
//
//        for (Entry<String, List<String>> residentData : workData.loadResidents().entrySet()) {
//            if (residentData.getValue().get(residentData.getValue().size() - 1).trim().equals(employee.getID())) {
//                Resident newResident = new Resident(residentData.getValue().get(0), residentData.getValue().get(1), residentData.getValue().get(2), residentData.getValue().get(3));
//                associatedResidents.add(newResident);
//            }
//        }
//
//        List<Pair<String, List<String>>> documentData = workData.loadDocuments();
//        for (Resident resident : associatedResidents) {
//            for (Pair<String, List<String>> document : documentData) {
//                if (resident.getID().equals(document.getKey())) {
//                    for (Document.type type : Document.type.values()) {
//                        if (type.toString().equalsIgnoreCase(document.getValue().get(0))) {
//                            Date creationDate = new SimpleDateFormat("dd/MM/yyyy").parse(document.getValue().get(1));
//                            Date editDate = new SimpleDateFormat("dd/MM/yyyy").parse(document.getValue().get(2));
//                            Document loadedDocument = new Document(type, creationDate, editDate);
//                            resident.addDocument(loadedDocument);
//                            break;
//                        }
//                    }
//                }
//            }
//            employee.addResident(resident);
//        }
//    }
}
