package ssb.domain_layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Process {
    private final Date creationDate;
    private final List<Document> documents = new ArrayList<>();
    private final Resident associatedResident;

    public Process(Resident associatedResident) {
        creationDate = new Date();
        this.associatedResident = associatedResident;
    }
    
    public void endCase() {
        
    }

    public List<Document> getDocuments() {
        return documents;
    }
    
    public void addDocument(Document document) {
        document.setResidentName(associatedResident.getFirstName() + " " + associatedResident.getLastName());
        documents.add(document);
    }
}