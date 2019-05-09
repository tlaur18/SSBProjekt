package ssb.presentation_layer.controllers.vum_document_controllers;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import ssb.domain_layer.Document;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.Resident;

public abstract class VumDocumentController {

    protected HashMap<CheckBox, Boolean> checkBoxes = new HashMap<>();
    protected HashMap<TextInputControl, String> textAreas = new HashMap<>();
    protected final DocumentManager documentManager = DocumentManager.getInstance();
    protected Resident chosenResident = InformationBridge.getInstance().getChosenResident();
    protected Document chosenDocument = InformationBridge.getInstance().getChosenDocument();

    // Creating a new Document object, saves the checkboxes and textareas to it, and adds it to the residents list of Documents
    protected void saveNewDocument(Document.type type) {
        Document doc = new Document(type);
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

    protected void loadTabPaneChildren(TabPane tabPane) {
        // TODO - make recursive and also fix bug in sagsåbning tab værge and repræsentation (there is an anchorpane for no reason)
        for (Tab tabChild : tabPane.getTabs()) {
            GridPane tabContents = (GridPane) tabChild.getContent();
            for (Node gridChild : tabContents.getChildren()) {
                if (gridChild instanceof Pane) {
                    loadFieldsFromPane((Pane) gridChild);
                } else {
                    saveRelevantChildren(gridChild);
                }
            }
        }
    }

    private void loadFieldsFromPane(Pane pane) {
        for (Node gridChild : pane.getChildrenUnmodifiable()) {
            if (gridChild instanceof Accordion) {
                for (TitledPane titledPane : ((Accordion) gridChild).getPanes()) {
                    GridPane titledPaneContents = (GridPane) titledPane.getContent();
                    for (Node node : titledPaneContents.getChildren()) {
                        saveRelevantChildren(node);
                    }
                }
            } else {
                saveRelevantChildren(gridChild);
            }
        }
    }

    private void saveRelevantChildren(Node node) {
        if (node instanceof CheckBox) {
            checkBoxes.put((CheckBox) node, Boolean.FALSE);
        } else if (node instanceof TextInputControl) {
            textAreas.put((TextInputControl) node, "");
        }
    }
}
