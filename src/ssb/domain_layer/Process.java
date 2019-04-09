package ssb.domain_layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class Process {

    private final Date creationDate;
    private final ObservableList<Document> documents = FXCollections.observableArrayList();
    private final Resident associatedResident;

    public Process(Resident associatedResident) {
        creationDate = new Date();
        this.associatedResident = associatedResident;
    }

    public void endCase() {

    }
    

    public ObservableList<Document> getDocuments() {
        return this.documents;
    }

    public void addDocument(Document document) {
        document.setResidentName(associatedResident.getFirstName() + " " + associatedResident.getLastName());
        this.documents.add(document);
    }

    public Resident getAssociatedResident() {
        return this.associatedResident;
    }
}
