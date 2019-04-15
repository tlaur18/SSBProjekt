package ssb.presentation_layer.fxml_documents;

import java.util.ArrayList;
import java.util.HashMap;
import ssb.domain_layer.Document;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Resident;

public class InformationBridge {

    private static final InformationBridge INSTANCE = new InformationBridge();
    private final HashMap<String, Integer> integerInformation;
    private final HashMap<String, Double> doubleInformation;
    private final HashMap<String, String> stringInformation;
    private final HashMap<String, Boolean> booleanInformation;
    private final HashMap<String, ArrayList<?>> arrayListInformation;
    private Resident chosenResident;
    private Document chosenDocument;
    private Employee loggedInEmployee = null;
    
    private DocumentManager documentManager;

    private InformationBridge() {
        this.integerInformation = new HashMap<>();
        this.doubleInformation = new HashMap<>();
        this.stringInformation = new HashMap<>();
        this.booleanInformation = new HashMap<>();
        this.arrayListInformation = new HashMap<>();
        
        documentManager = new DocumentManager();
    }

    public void putInteger(String key, Integer value) {
        integerInformation.put(key, value);
    }

    public Integer getIntegerValue(String key) {
        return integerInformation.get(key);
    }

    public void putDouble(String key, Double value) {
        doubleInformation.put(key, value);
    }

    public Double getDoubleValue(String key) {
        return doubleInformation.get(key);
    }

    public void putString(String key, String value) {
        stringInformation.put(key, value);
    }

    public String getStringValue(String key) {
        return stringInformation.get(key);
    }

    public void putBoolean(String key, Boolean value) {
        booleanInformation.put(key, value);
    }

    public Boolean getBooleanValue(String key) {
        return booleanInformation.get(key);
    }

    public void putArrayList(String key, ArrayList<?> arrayList) {
        arrayListInformation.put(key, arrayList);
    }

    public ArrayList<?> getArrayListValue(String key) {
        return arrayListInformation.get(key);
    }

    public void putChosenResident(Resident resident) {
        this.chosenResident = resident;
    }

    public Resident getChosenResident() {
        return this.chosenResident;
    }

    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public void setLoggedInEmployee(Employee loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
    }

    public Document getChosenDocument() {
        return this.chosenDocument;
    }

    public void setChosenDocument(Document document) {
        this.chosenDocument = document;
    }

    public DocumentManager getDocumentManager() {
        return documentManager;
    }

    public void setDocumentManager(DocumentManager documentManager) {
        this.documentManager = documentManager;
    }

    public static InformationBridge getINSTANCE() {
        return INSTANCE;
    }

}
