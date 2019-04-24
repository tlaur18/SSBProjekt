package ssb.presentation_layer.controllers;

import java.util.HashMap;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextInputControl;
import ssb.domain_layer.Document;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.Resident;

public class VumDocumentController {

    protected HashMap<CheckBox, Boolean> checkBoxes = new HashMap<>();
    protected HashMap<TextInputControl, String> textAreas = new HashMap<>();
    protected final DocumentManager documentManager = DocumentManager.getInstance();
    protected Resident chosenResident = InformationBridge.getInstance().getChosenResident();
    protected Document chosenDocument = InformationBridge.getInstance().getChosenDocument();

    // Creating a new Document object, saves the checkboxes and textareas to it, and adds it to the residents list of Documents
    protected void saveNewDocument() {
        Document doc = new Document(Document.type.SAGSÅBNING);
        doc.setSelectedCheckboxes(checkBoxes);
        doc.setTextAreas(textAreas);
        documentManager.addDocument(doc, chosenResident);
    }

    // adds the checkboxes and textAreas to the existing document
    protected void saveExistingDocument() {
        Document document = InformationBridge.getInstance().getChosenDocument();
        document.setSelectedCheckboxes(checkBoxes);
        document.setTextAreas(textAreas);
        documentManager.updateDocument(document);
    }

    // Saves all the Checkboxes and textAreas to their hashmaps
    protected void saveInfo() {
        for (CheckBox checkbox : checkBoxes.keySet()) {
            checkBoxes.put(checkbox, checkbox.isSelected());
        }

        for (TextInputControl textArea : textAreas.keySet()) {
            textAreas.put(textArea, textArea.getText());
        }
    }

    // Gets the Hashmaps and set the values to the correct textAreas and checkboxes
    protected void loadDocumentContent(Document doc) {
        HashMap<String, Boolean> checkBoxesFromDoc = doc.getSelectedCheckboxes();
        HashMap<String, String> textAreasFromDoc = doc.getTextAreas();

        for (CheckBox checkBox : checkBoxes.keySet()) {
            for (String IDFromDoc : checkBoxesFromDoc.keySet()) {
                if (checkBox.getId().equals(IDFromDoc)) {
                    checkBox.setSelected(checkBoxesFromDoc.get(IDFromDoc));
                }
            }
        }
        for (TextInputControl textArea : textAreas.keySet()) {
            for (String IDFromDoc : textAreasFromDoc.keySet()) {
                if (textArea.getId().equals(IDFromDoc)) {
                    textArea.setText(textAreasFromDoc.get(IDFromDoc));
                }
            }
        }
    }
}
