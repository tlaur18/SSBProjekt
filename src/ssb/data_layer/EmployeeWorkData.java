package ssb.data_layer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

public class EmployeeWorkData {

    private final File RESIDENTS_DATA_FILE = new File("workdata/residents.txt");
    private final File DOCUMENTS_DATA_FILE = new File("workdata/documents.txt");

    public HashMap<String, List<String>> loadResidents() {
        /*
        Manually adding document to the file doesn't work so in order to make it work this needs to be run if it ever needs it
         */
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESIDENTS_DATA_FILE))) {
//            String test = "petkam1234,peter,kampp,12345678,123456-1234,tholau1609\n"
//                    + "pouves1234,poul,vesterlykke,12345678,123456-1234,olivan0211\n"
//                    + "aslasl9876,aslak,aslak,12345678,987654-3210,tholau1609\n"
//                    + "erisør1234,erik,søre,12345678,987654-3210,micped1302";
//            writer.write(test);
//        } catch (Exception e) {
//            System.out.println("Something happend");
//        }

        final HashMap<String, List<String>> residentData = new HashMap<>();

        try (BufferedReader scanner = new BufferedReader(new FileReader(RESIDENTS_DATA_FILE))) {
            String residentDataString = scanner.readLine();
            while (residentDataString != null) {
                String[] residentDataValues = residentDataString.split(",");
                for (String string : residentDataValues) {
                    System.out.println(string);
                }
                String residentID = residentDataValues[0];
                String residentFirstname = residentDataValues[1];
                String residentLastName = residentDataValues[2];
                String residentPhoneNr = residentDataValues[3];
                String residentCPR = residentDataValues[4];
                String associatedEmloyee = residentDataValues[5];
                List<String> residentInfo = new ArrayList<>(Arrays.asList(residentFirstname, residentLastName, residentPhoneNr, residentCPR, associatedEmloyee));
                residentData.put(residentID, residentInfo);
                residentDataString = scanner.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        } catch (IOException ex) {
            Logger.getLogger(EmployeeWorkData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return residentData;
    }

    public List<Pair<String, List<String>>> loadDocuments() {
        /*
        Manually adding document to the file doesn't work so in order to make it work this needs to be run if it ever needs it
         */
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOCUMENTS_DATA_FILE))) {
//            String test = "sagsåbning,09/04/2019,09/04/2019,petkam1234\n"
//                    + "udredning,12/03/2019,14/03/2019,petkam1234\n"
//                    + "indstilling,15/03/2019,25/03/2019,petkam1234\n"
//                    + "sagsåbning,09/04/2019,09/04/2019,pouves1234\n"
//                    + "udredning,20/09/2018,15/11/2018,pouves1234\n"
//                    + "handleplan,07/10/2019,10/10/2019,pouves1234\n"
//                    + "sagsåbning,10/11/2018,12/11/2018,aslasl9876\n"
//                    + "indstilling,12/11/2018,13/11/2018,aslasl9876\n"
//                    + "sagsåbning,15/03/2019,25/03/2019,erisør1234\n"
//                    + "afgørelse,15/03/2019,25/03/2019,erisør1234";
//            writer.write(test);
//        } catch (Exception e) {
//            System.out.println("Something happend");
//        }

        final List<Pair<String, List<String>>> documentData = new ArrayList<>();

        try (BufferedReader scanner = new BufferedReader(new FileReader(DOCUMENTS_DATA_FILE))) {
            String documentDataString = scanner.readLine();
            while (documentDataString != null) {
                String[] documentValues = documentDataString.split(",");
                String documentType = documentValues[0];
                String documentCreationDate = documentValues[1];
                String documentEditDate = documentValues[2];
                String associatedResident = documentValues[3];
                documentData.add(new Pair(associatedResident, new ArrayList<>(Arrays.asList(documentType, documentCreationDate, documentEditDate))));
                documentDataString = scanner.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        } catch (IOException ex) {
            Logger.getLogger(EmployeeWorkData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return documentData;
    }
}
