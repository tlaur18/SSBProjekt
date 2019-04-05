package ssb.domain_layer;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javafx.util.Pair;
import ssb.data_layer.LogInData;
import ssb.domain_layer.Employee.Employee;

public class LoginManager {

    private final LogInData logInData = new LogInData();

    public String checkUserInformation(String userName, String password) {
        return checkUserLogIn(userName, password);
    }

    private String checkUserLogIn(String userName, String password) {
        HashMap<String, Pair<String, String>> recordedUserLogins = logInData.loadUserLogIns();
        for (Pair userNameAndPasswordPair : recordedUserLogins.values()) {
            String recordedUserName = (String) userNameAndPasswordPair.getKey();
            String recordedPassword = (String) userNameAndPasswordPair.getValue();
            if (recordedUserName.trim().equals(userName) && recordedPassword.trim().equals(password)) {
                for (Entry<String, Pair<String, String>> entry : recordedUserLogins.entrySet()) {
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
                switch employeeData.get(0) {
                    
                }
                return new Employee(employeeData.get(0), employeeData.get(1), employeeData.get(2), employeeData.get(3));
            }
        }
        return null;
    }
}
