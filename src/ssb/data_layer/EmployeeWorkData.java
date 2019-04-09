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

public class EmployeeWorkData {

    private final File RESIDENTS_DATA_FILE = new File("workdata/residents.txt");
    private final File DOCUMENTS_DATA_FILE = new File("workdata/documents.txt");

    public HashMap<String, List<String>> loadResidents() {
        /*
        Manually adding document to the file doesn't work so in order to make it work this needs to be run if it ever needs it
         */
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESIDENTS_DATA_FILE))) {
//            String test = "petkam1234,peter,kampp,12345678,123456-1234,tholau1609" + System.lineSeparator()
//                + "pouves1234,poul,vesterlykke,12345678,123456-1234,olivan0211" + System.lineSeparator();
//            writer.write(test);
//        } catch (Exception e) {
//            System.out.println("Something happend");
//        }
        
        final HashMap<String, List<String>> residentData = new HashMap<>();

        try (Scanner scanner2 = new Scanner(RESIDENTS_DATA_FILE).useDelimiter(",|\n")) {
            while (scanner2.hasNext()) {
                String residentID = scanner2.next();
                String residentFirstname = scanner2.next();
                String residentLastName = scanner2.next();
                String residentPhoneNr = scanner2.next();
                String residentCPR = scanner2.next();
                String associatedEmloyee = scanner2.next();
                List<String> residentInfo = new ArrayList<>();
                residentInfo.add(residentFirstname);
                residentInfo.add(residentLastName);
                residentInfo.add(residentPhoneNr);
                residentInfo.add(residentCPR);
                residentInfo.add(associatedEmloyee);
                residentData.put(residentID, residentInfo);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
        System.out.println(residentData.toString());
        return residentData;
    }
    
    public HashMap<String, List<String>> loadDocuments() {
        final HashMap<String, List<String>> documentData = new HashMap<>();

        try (Scanner scanner = new Scanner(DOCUMENTS_DATA_FILE).useDelimiter(",|\n")) {
            while (scanner.hasNext()) {
                String documentType = scanner.next();
                String documentCreationDate = scanner.next();
                String documentEditDate = scanner.next();
                String associatedResident = scanner.next();
                documentData.put(associatedResident, new ArrayList<>(Arrays.asList(documentType, documentCreationDate, documentEditDate)));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
        return documentData;
    }
}
