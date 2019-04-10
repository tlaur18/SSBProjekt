package ssb.data_layer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
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

        try (Scanner scanner = new Scanner(RESIDENTS_DATA_FILE).useDelimiter(",|\n")) {
            while (scanner.hasNext()) {
                String residentID = scanner.next();
                String residentFirstname = scanner.next();
                String residentLastName = scanner.next();
                String residentPhoneNr = scanner.next();
                String residentCPR = scanner.next();
                String associatedEmloyee = scanner.next();
                List<String> residentInfo = new ArrayList<>(Arrays.asList(residentFirstname, residentLastName, residentPhoneNr, residentCPR, associatedEmloyee));
                residentData.put(residentID, residentInfo);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
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

        try (Scanner scanner = new Scanner(DOCUMENTS_DATA_FILE).useDelimiter(",|\n")) {
            while (scanner.hasNext()) {
                String documentType = scanner.next();
                String documentCreationDate = scanner.next();
                String documentEditDate = scanner.next();
                String associatedResident = scanner.next();
                documentData.add(new Pair(associatedResident, new ArrayList<>(Arrays.asList(documentType, documentCreationDate, documentEditDate))));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
        return documentData;
    }
}
