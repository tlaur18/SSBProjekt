package ssb.data_layer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

public class LogInData {

    private final File LOGIN_DATA_FILE = new File("userdata/logins.txt");
    private final File EMPLOYEE_DATA_FILE = new File("userdata/employeedata.txt");

    public HashMap<String, Pair<String, String>> loadUserLogIns() {
        final HashMap<String, Pair<String, String>> userLogins = new HashMap<>();
        try (BufferedReader scanner = new BufferedReader(new FileReader(LOGIN_DATA_FILE))) {
            String logInString = scanner.readLine();
            while (logInString != null) {
                String[] logInStringValue = logInString.split(",");
                String employeeID = logInStringValue[0];
                String userName = logInStringValue[1];
                String password = logInStringValue[2];
                Pair<String, String> userNameAndPassword = new Pair<>(userName, password);
                userLogins.put(employeeID, userNameAndPassword);
                logInString = scanner.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        } catch (IOException ex) {
            Logger.getLogger(LogInData.class.getName()).log(Level.SEVERE, null, ex);
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
        try (BufferedReader scanner = new BufferedReader(new FileReader(EMPLOYEE_DATA_FILE))) {
            String employeeDataString = scanner.readLine();
            while (employeeDataString != null) {
                String[] employeeDataValues = employeeDataString.split(",");
                String employeeID = employeeDataValues[0];
                String employeeFirstname = employeeDataValues[1];
                String employeeLastName = employeeDataValues[2];
                String employeePhoneNr = employeeDataValues[3];
                String employeeCPR = employeeDataValues[4];
                String employeeRole = employeeDataValues[5];
                employeeData.put(employeeID, new ArrayList<>(Arrays.asList(employeeFirstname,
                        employeeLastName, employeePhoneNr, employeeCPR, employeeRole)));
                employeeDataString = scanner.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        } catch (IOException ex) {
            Logger.getLogger(LogInData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employeeData;
    }
}
