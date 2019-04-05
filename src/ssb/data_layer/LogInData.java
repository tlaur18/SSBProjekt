package ssb.data_layer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javafx.util.Pair;

public class LogInData {

    private final File LOGIN_DATA_FILE = new File("userdata/logins.txt");
    private final File EMPLOYEE_DATA_FILE = new File("userdata/employeeData.txt");

    public HashMap<String, Pair<String, String>> loadUserLogIns() {
        final HashMap<String, Pair<String, String>> userLogins = new HashMap<>();

        try (Scanner scanner = new Scanner(LOGIN_DATA_FILE).useDelimiter(",|\n")) {
            while (scanner.hasNext()) {
                String userID = scanner.next();
                String userName = scanner.next();
                String password = scanner.next();
                Pair<String, String> userNameAndPassword = new Pair<>(userName, password);
                userLogins.put(userID, userNameAndPassword);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
        return userLogins;
    }

    public HashMap<String, List<String>> loadEmployeeData() {
        final HashMap<String, List<String>> employeeData = new HashMap<>();
        try (Scanner scanner = new Scanner(EMPLOYEE_DATA_FILE).useDelimiter(",|\n")) {
            while (scanner.hasNext()) {
                String employeeID = scanner.next();
                String employeeRole = scanner.next();
                String employeeFirstname = scanner.next();
                String employeeLastName = scanner.next();
                String employeePhoneNr = scanner.next();
                String employeeCPR = scanner.next();
                employeeData.put(employeeID, new ArrayList<>(Arrays.asList(employeeRole,
                    employeeFirstname, employeeLastName, employeePhoneNr, employeeCPR)));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
        return employeeData;
    }

}
