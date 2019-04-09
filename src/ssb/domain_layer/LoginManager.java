package ssb.domain_layer;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javafx.util.Pair;
import ssb.data_layer.LogInData;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Employee.Sagsbehandler;
import ssb.domain_layer.Employee.SocialPædagog;
import ssb.domain_layer.Employee.Socialrådgiver;
import ssb.domain_layer.Employee.Vikar;

public class LoginManager {

    private final LogInData logInData = new LogInData();

    public String checkUserInput(String userName, String password) {
        return checkUserLogIn(userName, password);
    }

    private String checkUserLogIn(String userNameInput, String passwordInput) {
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

    public Employee setLoggedInUser(String userID) {
        for (Entry<String, List<String>> entry : logInData.loadEmployeeData().entrySet()) {
            if (entry.getKey().trim().equals(userID)) {
                List<String> employeeData = entry.getValue();
                System.out.println(employeeData.get(employeeData.size() - 1).trim());
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
}
