package ssb.domain_layer;

import java.util.Date;
import java.util.Random;

public class Document {

    public enum type {
        SAGSÅBNING, UDREDNING, FAGLIGVURDERING, INDSTILLING, HANDLEPLAN, AFGØRELSE, BESTILLING, STATUSNOTAT, OPFØLGNING
    }

    private String documentName;
    private final Resident associatedResident;
    private final type type;
    private Date editDate;
    private Date creationDate;

    public Document(Resident associatedResident, type type) {
        this.associatedResident = associatedResident;
        this.type = type;
        generateDocumentName();
        setEditDate();
        setCreationDate();
    }

    private void generateDocumentName() {
        this.documentName = associatedResident.getFirstName().substring(0, 3) + type.toString().substring(0, 3) + " Test";
    }

    private void setCreationDate() {
        this.creationDate = new Date(new Random().nextInt(100000000) + 1000000);
    }

    private void setEditDate() {
        this.editDate = new Date();
    }

    public String getDocumentName() {
        return documentName;
    }

    public String getAssociatedResident() {
        return associatedResident.getFirstName();
    }

    public type getType() {
        return type;
    }

    public Date getEditDate() {
        return editDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "documentName=" + documentName 
            + "\nassociatedResident=" + associatedResident 
            + "\ntype=" + type 
            + "\neditDate=" + editDate 
            + "\ncreationDate=" + creationDate;
    }
    
    

}
