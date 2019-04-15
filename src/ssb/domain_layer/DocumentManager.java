package ssb.domain_layer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ssb.domain_layer.Employee.Employee;

public class DocumentManager {

    private ObservableList<Document> allDocuments = FXCollections.observableArrayList();

    public DocumentManager() {
    }

    public ObservableList<Document> getAllDocuments() {
        return this.allDocuments;
    }

    public void loadAllDocuments(Employee employee) {
        for (Document doc : employee.getResidentDocuments()) {
            allDocuments.add(doc);
        }
    }

    public void addDocument(Document document, Resident resident) {
        resident.addDocument(document);
        allDocuments.add(document);
    }
}
