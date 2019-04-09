package ssb.domain_layer;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Resident extends Person {

    private final ObservableList<Process> processes = FXCollections.observableArrayList();
    

    public Resident(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
    }
    
    public void addProcess(Process process) {
        processes.add(process);
    }

    public void removeProcess(Process process) {
        processes.remove(process);
    }

    public ObservableList<Document> getDocuments() {
        ObservableList allDocumentsForAllResidents = FXCollections.observableArrayList();
        for (Process process : processes) {
            allDocumentsForAllResidents.addAll(process.getDocuments());
        }
        return allDocumentsForAllResidents;
      
    }
    public List<Process> getProcess(){
        return this.processes;
    }
    
    public String toString(){
        String string = "";
        string += this.getFirstName() + " " + this.getLastName();
        return string;
    }
}
