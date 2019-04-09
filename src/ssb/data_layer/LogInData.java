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
    private final File EMPLOYEE_DATA_FILE = new File("userdata/employeedata.txt");

    public HashMap<String, Pair<String, String>> loadUserLogIns() {
        final HashMap<String, Pair<String, String>> userLogins = new HashMap<>();
        try (Scanner scanner = new Scanner(LOGIN_DATA_FILE).useDelimiter(",|\n")) {
            while (scanner.hasNext()) {
                String employeeID = scanner.next();
                String userName = scanner.next();
                String password = scanner.next();
                Pair<String, String> userNameAndPassword = new Pair<>(userName, password);
                userLogins.put(employeeID, userNameAndPassword);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
        return userLogins;
    }

    /*
    Gets all employee data where the key is the ID
     */
    public HashMap<String, List<String>> loadEmployeeData() {
        /*
        Manually adding document to the file doesn't work so in order to make it work this needs to be run if it ever needs it
         */
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_DATA_FILE))) {
//            String test = "nickru0412,nicolai,kruuse,60487580,123456-7890,socialrådgiver" + System.lineSeparator()
//                + "micped1302,michael,pedersen,60825359,123456-7891,sagsbehandler" + System.lineSeparator()
//                + "morjen1506,morten,jensen,22300758,123456-7892,socialrådgiver" + System.lineSeparator()
//                + "tholau1609,thomas,laursen,30953669,123456-7893,socialrådgiver" + System.lineSeparator()
//                + "olivan0211,oliver,van komen,93930585,123456-7894,socialrådgiver" + System.lineSeparator()
//                + "lonbor1234,lone,borgersen,12345678,123456-7895,socialpædagog "+ System.lineSeparator()
//                + "jepsch1234,jeppe,schmidt,12345678,123456-7896,vikar";
//            writer.write(test);
//        } catch (Exception e) {
//            System.out.println("Something happend");
//        }

        final HashMap<String, List<String>> employeeData = new HashMap<>();
        try (Scanner scanner = new Scanner(EMPLOYEE_DATA_FILE).useDelimiter(",|\n")) {
            while (scanner.hasNext()) {
                String employeeID = scanner.next();
                String employeeFirstname = scanner.next();
                String employeeLastName = scanner.next();
                String employeePhoneNr = scanner.next();
                String employeeCPR = scanner.next();
                String employeeRole = scanner.next();
                employeeData.put(employeeID, new ArrayList<>(Arrays.asList(employeeFirstname,
                        employeeLastName, employeePhoneNr, employeeCPR, employeeRole)));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
        return employeeData;
    }
}
