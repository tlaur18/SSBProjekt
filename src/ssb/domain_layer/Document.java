package ssb.domain_layer;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Document {

    public enum type {
        SAGSÅBNING, UDREDNING, FAGLIGVURDERING, INDSTILLING, HANDLEPLAN, AFGØRELSE, BESTILLING, STATUSNOTAT, OPFØLGNING
    }

    private String documentName;
    private String residentName;
    private final type type;
    private Date editDate;
    private Date creationDate;
    private HashMap<CheckBox, Boolean> selectedCheckBoxes;
    private HashMap<TextField, String> textFieldInput;

    public Document(type type) {
        this.type = type;
        setEditDate();
        setCreationDate();
    }
    
    public Document(type type, HashMap selectedCheckBox) {
        this(type);
        this.selectedCheckBoxes = selectedCheckBox;
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
    
    public HashMap<CheckBox, Boolean> getSelectedCheckboxes() {
        return this.selectedCheckBoxes;
    }
    
    public void setSelectedCheckboxes(HashMap<CheckBox, Boolean> selectedCheckBoxes) {
        this.selectedCheckBoxes = selectedCheckBoxes;
    }
    
    public HashMap<TextField, String> getTextFieldInput() {
        return textFieldInput;
    }
    
    public void setTextFieldInput(HashMap<TextField, String> textFieldInput) {
        this.textFieldInput = textFieldInput;
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
