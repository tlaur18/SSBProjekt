package ssb.domain_layer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import ssb.data_layer.DatabaseManager;
import ssb.data_layer.contracts.DocumentsContract;
import ssb.data_layer.contracts.EmployeeContract;
import ssb.data_layer.contracts.PersonsContract;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Employee.Sagsbehandler;
import ssb.domain_layer.Employee.SocialPædagog;
import ssb.domain_layer.Employee.Socialrådgiver;
import ssb.domain_layer.Employee.Vikar;

public class LoginManager {
    // Usikker på om det at lave objekterne oppe i domænet laget fremfor i data laget er korrekt. Det skal undersøges om det skal ned i data laget.
    private final DatabaseManager db = DatabaseManager.getInstance();
    private final InformationBridge informationBridge = InformationBridge.getINSTANCE();

    public boolean checkUserLogIn(String userNameInput, String passwordInput) {
        String employeeCPRString = db.checkUserLogin(userNameInput, passwordInput);
        if (employeeCPRString != null) {
            HashMap<String, String> employeeData = db.getEmployeeDetails(employeeCPRString);
            informationBridge.setLoggedInEmployee(setEmployeeDetails(employeeData));
            setEmployeeResidents(employeeCPRString);
            setResidentDocuments();
            return true;
        }
        return false;
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

    private void setEmployeeResidents(String employeeCpr) {
        ArrayList<HashMap<String, String>> residents = db.getEmployeeAssoResidents(employeeCpr);
        for (HashMap<String, String> residentData : residents) {
            informationBridge.getLoggedInEmployee().addResident(makeResidentObject(residentData));
        }
    }

    private Resident makeResidentObject(HashMap<String, String> residentData) {
        String firstName = residentData.get(PersonsContract.COLUMN_FIRST_NAME);
        String lastName = residentData.get(PersonsContract.COLUMN_LAST_NAME);
        String phoneNr = residentData.get(PersonsContract.COLUMN_PHONE);
        String cprNr = residentData.get(PersonsContract.COLUMN_CPR);
        return new Resident(firstName, lastName, phoneNr, cprNr);
    }

    private void setResidentDocuments() {
        informationBridge.getLoggedInEmployee().getResidents().forEach((resident) -> {
            db.getResidentDocuments(resident.getCprNr()).forEach((documentData) -> {
                resident.addDocument(makeDocumentObject(documentData));
            });
        });
    }

    private Document makeDocumentObject(HashMap<String, String> documentData) {
        String typeString = documentData.get(DocumentsContract.COLUMN_TYPE);
//        System.out.println("type : "+ typeString);
        Document.type documentType = null;
        for (Document.type type : Document.type.values()) {
            if (type.toString().equalsIgnoreCase(typeString)) {
                documentType = type;
            }
        }
        String createDateString = documentData.get(DocumentsContract.COLUMN_CREATE_DATE);
//        System.out.println("create date: " + createDateString);
        String editDateString = documentData.get(DocumentsContract.COLUMN_EDIT_DATE);
//        System.out.println("edit date: " + editDateString);
        Date createDate = null;
        Date editDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            createDate = dateFormat.parse(createDateString);
            editDate = dateFormat.parse(editDateString);
        } catch (ParseException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new Document(documentType, createDate, editDate);
    }
}
