package ssb.domain_layer;

import java.util.ArrayList;
import java.util.List;

public class Resident extends Person {

    private final List<Process> processes = new ArrayList<>();

    public Resident(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
    }
    
    public void addProcess(Process process) {
        processes.add(process);
    }

    public void removeProcess(Process process) {
        processes.remove(process);
    }

    public List<Document> getDocuments() {
        List<Document> allDocumentsForAllResidents = new ArrayList<>();
        for (Process process : processes) {
            allDocumentsForAllResidents.addAll(process.getDocuments());
        }
        return allDocumentsForAllResidents;
    }
}
