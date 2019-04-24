package ssb.domain_layer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ssb.data_layer.DatabaseManager;

public final class DocumentManager {

    private ObservableList<Document> allDocuments = FXCollections.observableArrayList();

    private static DocumentManager INSTANCE = null;

    private DocumentManager() {
    }

    public void setDocumentsForEmployee() {
        for (Document doc : InformationBridge.getInstance().getLoggedInEmployee().getResidentDocuments()) {
            allDocuments.add(doc);
        }
    }

    public static DocumentManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DocumentManager();
        }
        return INSTANCE;
    }

    public ObservableList<Document> getAllDocuments() {
        return this.allDocuments;
    }

    /*
    When a new document is to be added to a resident this methods is to be called.
     */
    public void addDocument(Document document, Resident resident) {
        resident.addDocument(document);
        allDocuments.add(document);
        DatabaseManager.getInstance().insertDocument(document, resident.getCprNr());
    }

    public void updateDocument(Document document) {
        DatabaseManager.getInstance().updateDocument(document.encodeDocument(), String.valueOf(document.getId()));
    }

    public void clearDocuments() {
        allDocuments = FXCollections.observableArrayList();
    }

    public Document decodeDocument(String encodedString) {
        try {
            byte[] data = Base64.getDecoder().decode(encodedString);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return (Document) o;
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("Decoding: something went wrong: " + ex.getMessage());
        }
        return null;
    }
}
