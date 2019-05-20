package ssb.domain_layer;

import ssb.domain_layer.person.Resident;
import ssb.domain_layer.person.Person;
import ssb.domain_layer.document.DocumentManager;
import ssb.domain_layer.document.SystemDocument;
import ssb.domain_layer.notification.NotificationManager;
import ssb.domain_layer.person.Employee;

public class InformationBridge {

    private static final InformationBridge INSTANCE = new InformationBridge();
    private Resident chosenResident;
    private SystemDocument chosenDocument;
    private Employee loggedInEmployee = null;
    private Person chosenEmployee;
    private Home currentHome;

    public Home getCurrentHome() {
        return currentHome;
    }

    public void setCurrentHome(Home currentHome) {
        this.currentHome = currentHome;
    }
    
    private InformationBridge() {
    }

    public void putChosenResident(Resident resident) {
        this.chosenResident = resident;
    }
    public void putChosenEmployee(Person person) {
        this.chosenEmployee = person;
    }
    public Person getChosenEmployee(){
        return this.chosenEmployee;
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

    public SystemDocument getChosenDocument() {
        return this.chosenDocument;
    }

    public void setChosenDocument(SystemDocument document) {
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
        NotificationManager.getInstance().clearNotifications();
    }

}
