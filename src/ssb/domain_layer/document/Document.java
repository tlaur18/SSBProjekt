package ssb.domain_layer.document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextInputControl;

public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum type {
        SAGSÅBNING, HANDLEPLAN
    }

    private long id = 0;
    private String documentName;
    private String residentName;
    private final type type;
    private Date editDate;
    private Date creationDate;
    private HashMap<String, Boolean> selectedCheckBoxes;
    private HashMap<String, String> textAreas;

    public Document(type type) {
        this.type = type;
        setEditDate();
        setCreationDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private void setCreationDate() {
        this.creationDate = new Date();
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
        if (residentName != null) {
            return residentName;
        } else {
            return "No associated resident";
        }
    }

    public String encodeDocument() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
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

    public HashMap<String, String> getTextAreas() {
        return this.textAreas;
    }

    public void setTextAreas(HashMap<TextInputControl, String> textAreaInput) {
        this.textAreas = new HashMap<>();
        for (TextInputControl ta : textAreaInput.keySet()) {
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
