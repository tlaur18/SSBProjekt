package ssb.domain_layer;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Document implements Serializable {

    public enum type {
        SAGSÅBNING, UDREDNING, FAGLIGVURDERING, INDSTILLING, HANDLEPLAN, AFGØRELSE, BESTILLING, STATUSNOTAT, OPFØLGNING
    }

    private String documentName;
    private String residentName;
    private final type type;
    private Date editDate;
    private Date creationDate;
    private HashMap<String, Boolean> selectedCheckBoxes;
    private HashMap<String, String> textFieldInput;
    private HashMap<String, String> textAreas;

    public Document(type type) {
        this.type = type;
        setEditDate();
        setCreationDate();
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

    public HashMap<String, Boolean> getSelectedCheckboxes() {
        return this.selectedCheckBoxes;
    }

    public void setSelectedCheckboxes(HashMap<CheckBox, Boolean> selectedCheckBoxes) {
        this.selectedCheckBoxes = new HashMap<>();
        for (CheckBox cb : selectedCheckBoxes.keySet()) {
            this.selectedCheckBoxes.put(cb.getId(), cb.isSelected());
        }
    }

    public HashMap<String, String> getTextFieldInput() {
        return textFieldInput;
    }

    public void setTextFieldInput(HashMap<TextField, String> textFieldInput) {
        this.textFieldInput = new HashMap<>();
        for (TextField tf : textFieldInput.keySet()) {
            this.textFieldInput.put(tf.getId(), tf.getText());
        }
    }

    public HashMap<String, String> getTextAreas() {
        return this.textAreas;
    }

    public void setTextAreas(HashMap<TextArea, String> textAreaInput) {
        this.textAreas = new HashMap<>();
        for (TextArea ta : textAreaInput.keySet()) {
            this.textAreas.put(ta.getId(), ta.getText());
        }
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    @Override
    public String toString() {
        return "DocumentName: " + documentName
                + "\n - AssociatedResident: " + residentName
                + "\n - Type: " + type
                + "\n - EditDate: " + editDate
                + "\n - CreationDate: " + creationDate
                + "\n";
    }
}
