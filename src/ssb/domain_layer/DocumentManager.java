package ssb.domain_layer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
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
    
    public String encodeDocument(Serializable object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
    }
}
