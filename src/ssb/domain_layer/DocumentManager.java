package ssb.domain_layer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DocumentManager {

    private ObservableList<Document> allDocuments = FXCollections.observableArrayList();

    public DocumentManager() {
    }

    public ObservableList<Document> getAllDocuments() {
        return this.allDocuments;
    }
    
    public void addDocument(Document document, Resident resident) {
        resident.addDocument(document);
        allDocuments.add(document);
    }
}
