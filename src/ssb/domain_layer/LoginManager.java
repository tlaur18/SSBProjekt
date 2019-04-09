package ssb.domain_layer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javafx.util.Pair;
import ssb.data_layer.EmployeeWorkData;
import ssb.data_layer.LogInData;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Employee.Sagsbehandler;
import ssb.domain_layer.Employee.SocialPædagog;
import ssb.domain_layer.Employee.Socialrådgiver;
import ssb.domain_layer.Employee.Vikar;

public class LoginManager {

    private final LogInData logInData = new LogInData();
    private final EmployeeWorkData workData = new EmployeeWorkData();
    private final InformationBridge informationBridge = InformationBridge.getINSTANCE();

    public String checkUserLogIn(String userNameInput, String passwordInput) {
        HashMap<String, Pair<String, String>> recordedUserLogins = logInData.loadUserLogIns();
        for (Pair userNameAndPasswordPair : recordedUserLogins.values()) { // Checks if any of the password and username matches the inputtet username and password
            String recordedUserName = (String) userNameAndPasswordPair.getKey();
            String recordedPassword = (String) userNameAndPasswordPair.getValue();
            if (recordedUserName.trim().equals(userNameInput) && recordedPassword.trim().equals(passwordInput)) {
                for (Entry<String, Pair<String, String>> entry : recordedUserLogins.entrySet()) { // Gets the ID based on username and password
                    if (entry.getValue().equals(userNameAndPasswordPair)) {
                        return entry.getKey();
                    }
                }
            }
        }
        return null;
    }

    public Employee setLoggedInUser(String employeeID) {
        for (Entry<String, List<String>> userData : logInData.loadEmployeeData().entrySet()) {
            if (userData.getKey().trim().equals(employeeID)) {
                List<String> employeeData = userData.getValue();
                switch (employeeData.get(employeeData.size() - 1).trim()) {
                    case "socialrådgiver":
                        return new Socialrådgiver(employeeData.get(0), employeeData.get(1), employeeData.get(2), employeeData.get(3));
                    case "sagsbehandler":
                        return new Sagsbehandler(employeeData.get(0), employeeData.get(1), employeeData.get(2), employeeData.get(3));
                    case "socialpædagog":
                        return new SocialPædagog(employeeData.get(0), employeeData.get(1), employeeData.get(2), employeeData.get(3));
                    case "vikar":
                        return new Vikar(employeeData.get(0), employeeData.get(1), employeeData.get(2), employeeData.get(3));
                }
            }
        }
        return null;
    }

    public void setEmployeeWorkData(Employee employee) throws ParseException {
        List<Resident> associatedResidents = new ArrayList<>();

        for (Entry<String, List<String>> residentData : workData.loadResidents().entrySet()) {
            System.out.println(residentData.toString());
            if (residentData.getValue().get(residentData.getValue().size() - 1).trim().equals(employee.getID().trim())) {
                Resident newResident = new Resident(residentData.getValue().get(0), residentData.getValue().get(1), residentData.getValue().get(2), residentData.getValue().get(3));
                associatedResidents.add(newResident);
            }
        }

        HashMap<String, List<String>> documentData = workData.loadDocuments();
        for (Resident resident : associatedResidents) {
            for (Entry<String, List<String>> document : documentData.entrySet()) {
                if (resident.getID().equals(document.getKey())) {
                    for (Document.type type : Document.type.values()) {
                        if (type.toString().equals(document.getValue().get(0))) {
                            Date creationDate = new SimpleDateFormat("dd/MM/yyyy").parse(document.getValue().get(1));
                            Date editDate = new SimpleDateFormat("dd/MM/yyyy").parse(document.getValue().get(2));
                            Document loadedDocument = new Document(type, creationDate, editDate);
                            resident.addDocument(loadedDocument);
                            break;
                        }
                    }
                }
            }
            employee.addResident(resident);
        }
        System.out.println(employee.getResidentDocuments().toString());
    }
}
