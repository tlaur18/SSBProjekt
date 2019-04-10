package ssb.domain_layer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Document {

    public enum type {
        SAGSÅBNING, UDREDNING, FAGLIGVURDERING, INDSTILLING, HANDLEPLAN, AFGØRELSE, BESTILLING, STATUSNOTAT, OPFØLGNING
    }

    private String documentName;
    private String residentName;
    private final type type;
    private Date editDate;
    private Date creationDate;
    private HashMap selectedCheckBox;
    private HashMap textAreas;

    public Document(type type) {
        this.type = type;
        setEditDate();
        setCreationDate();
    }
    public Document(type type, HashMap selectedCheckBox) {
        this(type);
        this.selectedCheckBox = selectedCheckBox;
    }
    public Document(type type, HashMap selectedCheckBoxes, HashMap textAreas) {
        this(type);
        this.selectedCheckBox = selectedCheckBoxes;
        this.textAreas = textAreas;
    }

    private void setCreationDate() {
        this.creationDate = new Date(new Random().nextInt(100000000) + 1000000);
    }

    private void setEditDate() {
        this.editDate = new Date();
    }

    public String getDocumentName() {
        return generateDocumentName();
    }

    private String generateDocumentName() {
        return documentName = residentName.substring(0, 3) + type.toString().substring(0, 3) + " Test";
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

    public String getResidentName() {
        return residentName;
    }
    public HashMap getSelectedCheckbox() {
        return this.selectedCheckBox;
    }
    public HashMap getTextAreas(){
        return this.textAreas;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    @Override
    public String toString() {
        return "documentName=" + documentName
            + "\nassociatedResident=" + residentName
            + "\ntype=" + type
            + "\neditDate=" + editDate
            + "\ncreationDate=" + creationDate;
    }

}
