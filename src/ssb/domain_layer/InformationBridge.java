package ssb.domain_layer;

import ssb.domain_layer.Employee.Employee;

public class InformationBridge {

    private static final InformationBridge INSTANCE = new InformationBridge();
    private Resident chosenResident;
    private Document chosenDocument;
    private Employee loggedInEmployee = null;
    private Department currentDepartment;

    public Department getCurrentDepartment() {
        return currentDepartment;
    }

    public void setCurrentDepartment(Department currentDepartment) {
        this.currentDepartment = currentDepartment;
    }
    
    private InformationBridge() {
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

    public static InformationBridge getInstance() {
        return INSTANCE;
    }

    public void resetSystem() {
        chosenDocument = null;
        chosenResident = null;
        loggedInEmployee = null;
        DocumentManager.getInstance().clearDocuments();
    }

}
