package ssb.domain_layer.document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ssb.data_layer.DatabaseManager;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.person.Resident;

public final class DocumentManager {

    private ObservableList<SystemDocument> allDocuments = FXCollections.observableArrayList();

    private static DocumentManager INSTANCE = null;

    private DocumentManager() {
    }
    
    public void setDocumentsForHome() {
        for (Resident resident : InformationBridge.getInstance().getCurrentHome().getResidents()) {
            for (SystemDocument document : resident.getDocuments()) {
                allDocuments.add(document);
            }
        }
    }

    public static DocumentManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DocumentManager();
        }
        return INSTANCE;
    }

    public ObservableList<SystemDocument> getAllDocuments() {
        return this.allDocuments;
    }

    /*
    When a new document is to be added to a resident this methods is to be called.
     */
    public void addDocument(SystemDocument document, Resident resident) {
        resident.addDocument(document);
        allDocuments.add(document);
        document.setId(DatabaseManager.getInstance().getDocumentIdCount() + 1);
        DatabaseManager.getInstance().insertDocument(document.encodeDocument(), resident.getCprNr());
    }

    public void updateDocument(SystemDocument document) {
        DatabaseManager.getInstance().updateDocument(document.encodeDocument(), (int) document.getId());
    }

    public void clearDocuments() {
        allDocuments = FXCollections.observableArrayList();
    }

    public SystemDocument decodeDocument(String encodedString) {
        try {
            byte[] data = Base64.getDecoder().decode(encodedString);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return (SystemDocument) o;
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("Decoding: something went wrong: " + ex.getMessage());
        }
        return null;
    }
}
