package ssb.domain_layer.document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ssb.data_layer.DatabaseManager;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.logger.EmployeeLoggerManager;
import ssb.domain_layer.person.Resident;

public final class DocumentManager {
    
    private static final Logger EMPLOYEE_LOGGER = Logger.getLogger(EmployeeLoggerManager.class.getName());
    
    private ObservableList<Document> allDocuments = FXCollections.observableArrayList();

    private static DocumentManager INSTANCE = null;

    private DocumentManager() {
    }
    
    public void setDocumentsForHome() {
        for (Resident resident : InformationBridge.getInstance().getCurrentHome().getResidents()) {
            for (Document document : resident.getDocuments()) {
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

    public ObservableList<Document> getAllDocuments() {
        return this.allDocuments;
    }

    /*
    When a new document is to be added to a resident this methods is to be called.
     */
    public void addDocument(Document document, Resident resident) {
        resident.addDocument(document);
        allDocuments.add(document);
        document.setId(DatabaseManager.getInstance().getDocumentIdCount() + 1);
        DatabaseManager.getInstance().insertDocument(document.encodeDocument(), resident.getCprNr());
        EMPLOYEE_LOGGER.log(Level.INFO, "{0} has added a new document for resident: {1} {2}", new Object[]{InformationBridge.getInstance().getLoggedInEmployee().getFirstName(), resident.getFirstName(), resident.getLastName()});
    }

    public void updateDocument(Document document) {
        DatabaseManager.getInstance().updateDocument(document.encodeDocument(), (int) document.getId());
        EMPLOYEE_LOGGER.log(Level.INFO, "{0} has updated {1} for the resident {2}", new Object[]{InformationBridge.getInstance().getLoggedInEmployee().getFirstName(), document.getDocumentName(), document.getResidentName()});
    }

    public void clearDocuments() {
        allDocuments = FXCollections.observableArrayList();
    }

    public Document decodeDocument(String encodedString) {
        try {
            byte[] data = Base64.getDecoder().decode(encodedString);
            Object o;
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
                o = ois.readObject();
            }
            return (Document) o;
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("Decoding: something went wrong: " + ex.getMessage());
        }
        return null;
    }
}
