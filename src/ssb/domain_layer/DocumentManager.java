package ssb.domain_layer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Document decodeDocument(String encodedString) throws IOException{
        try {
            byte[] data = Base64.getDecoder().decode(encodedString);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return (Document) o;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DocumentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
