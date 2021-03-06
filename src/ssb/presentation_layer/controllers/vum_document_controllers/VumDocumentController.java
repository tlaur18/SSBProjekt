package ssb.presentation_layer.controllers.vum_document_controllers;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import ssb.domain_layer.document.Document;
import ssb.domain_layer.document.DocumentManager;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.person.Resident;

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
        new Thread(() -> {
            documentManager.addDocument(doc, chosenResident);
        }).start();
    }

    // adds the checkboxes and textAreas to the existing document
    protected void saveExistingDocument() {
        Document document = InformationBridge.getInstance().getChosenDocument();
        document.setSelectedCheckboxes(checkBoxes);
        document.setTextAreas(textAreas);
        new Thread(() -> {
            documentManager.updateDocument(document);
        }).start();
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
        for (Tab tabChild : tabPane.getTabs()) {
            GridPane scrollPaneContent = (GridPane) ((ScrollPane) tabChild.getContent()).getContent();
            for (Node gridChild : scrollPaneContent.getChildren()) {
                if (gridChild instanceof Pane) {
                    loadFieldsFromPane((Pane) gridChild);
                } else {
                    setRelevantChildren(gridChild);
                }
            }
        }
    }

    private void loadFieldsFromPane(Pane pane) {
        for (Node gridChild : pane.getChildrenUnmodifiable()) {
            if (gridChild instanceof Pane) {
                loadFieldsFromPane((Pane) gridChild);
            } else {
                setRelevantChildren(gridChild);
            }
        }
    }

    private void setRelevantChildren(Node node) {
        if (node instanceof CheckBox) {
            checkBoxes.put((CheckBox) node, Boolean.FALSE);
        } else if (node instanceof TextInputControl) {
            textAreas.put((TextInputControl) node, "");
        }
    }

    /**
     *
     * @param <T>
     * @param childID
     * @param tab
     * @param type
     * @return
     */
    public static <T extends Node> T findChildInTab(String childID, Tab tab, Class<T> type) {
        GridPane contentOfTab = (GridPane) ((ScrollPane) tab.getContent()).getContent();
        Node foundNode = searchPane(contentOfTab, childID);
        if (foundNode != null) {
            return type.cast(foundNode);
        } else {
            return null;
        }
    }

    private static Node searchPane(Pane paneChild, String idSearch) {
        for (Node child : paneChild.getChildren()) {
            if (child instanceof Pane) {
                Node foundNode = searchPane((Pane) child, idSearch); // recursive system
                if (foundNode != null) { // searchpane has found the correct child
                    return foundNode;
                }
            } else {
                if (child.getId() == null) { // children without ID's is not searchable
                    continue;
                }
                Node foundNode = searchForChild(child, idSearch); // checks if the child is the one we're looking for
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }
        return null;
    }

    private static Node searchForChild(Node child, String idSearch) {
        if (child.getId().equalsIgnoreCase(idSearch)) {
            return child;
        } else {
            return null;
        }
    }
}
