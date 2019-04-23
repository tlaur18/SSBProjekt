package ssb.presentation_layer.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ssb.domain_layer.Document;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.Resident;
import ssb.presentation_layer.fxml_documents.InformationBridge;

public class HandleplanController implements Initializable {

    @FXML
    private TextField navnTxtF;
    @FXML
    private TextField cprTxtF;
    @FXML
    private TextField adresseTxtF;
    @FXML
    private TextField paaroerendeTxtF;
    @FXML
    private TextField mailTxtF;
    @FXML
    private TextField telefonTxtF;
    @FXML
    private TextField datoTxtF;
    @FXML
    private CheckBox vaergemaalCheck1;
    @FXML
    private CheckBox vaergemaalCheck2;
    @FXML
    private CheckBox vaergemaalCheck3;
    @FXML
    private CheckBox vaergemaalCheck4;
    @FXML
    private CheckBox repraesentationCheck1;
    @FXML
    private CheckBox repraesentationCheck2;
    @FXML
    private CheckBox repraesentationCheck3;
    @FXML
    private Button saveButton;
    
    private DocumentManager documentManager;
    private Resident chosenResident;
    private HashMap<CheckBox, Boolean> checkBoxes;
    private HashMap<TextField, String> textFields;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        documentManager = DocumentManager.getInstance();
        chosenResident = InformationBridge.getInstance().getChosenResident();
        checkBoxes = new HashMap();
        textFields = new HashMap();
        loadTextFields();
        loadCheckBoxes();

        if (InformationBridge.getInstance().getChosenDocument() != null) {
            loadDocumentContent(InformationBridge.getInstance().getChosenDocument());
        }
    }

    private void loadCheckBoxes() {
        checkBoxes.put(vaergemaalCheck1, Boolean.FALSE);
        checkBoxes.put(vaergemaalCheck2, Boolean.FALSE);
        checkBoxes.put(vaergemaalCheck3, Boolean.FALSE);
        checkBoxes.put(vaergemaalCheck4, Boolean.FALSE);
        checkBoxes.put(repraesentationCheck1, Boolean.FALSE);
        checkBoxes.put(repraesentationCheck2, Boolean.FALSE);
        checkBoxes.put(repraesentationCheck3, Boolean.FALSE);
    }

    private void loadTextFields() {
        textFields.put(navnTxtF, null);
        textFields.put(cprTxtF, null);
        textFields.put(adresseTxtF, null);
        textFields.put(paaroerendeTxtF, null);
        textFields.put(mailTxtF, null);
        textFields.put(telefonTxtF, null);
        textFields.put(datoTxtF, null);
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) throws IOException {
        saveInfo();
        if(InformationBridge.getInstance().getChosenDocument() != null) {
            saveExistingDocument();
        }
        else {
            saveNewDocument();
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        
    }
    // Saves all the Checkboxes and textAreas to their hashmaps
    private void saveInfo() {
        for (CheckBox checkBox : checkBoxes.keySet()) {
            checkBoxes.put(checkBox, checkBox.isSelected());
        }

        for (TextField textField : textFields.keySet()) {
            textFields.put(textField, textField.getText());
        }
    }
    
    // Creating a new Document object, saves the checkboxes and textareas to it, and adds it to the residents list of Documents
        public void saveNewDocument() {
        Document doc = new Document(Document.type.HANDLEPLAN);
        doc.setSelectedCheckboxes(checkBoxes);
        doc.setTextFieldInput(textFields);
        documentManager.addDocument(doc, chosenResident);   
    }
        
     // adds the checkboxes and textAreas to the existing document
    public void saveExistingDocument() {
        Document document = InformationBridge.getInstance().getChosenDocument();
        document.setSelectedCheckboxes(checkBoxes);
        document.setTextFieldInput(textFields);
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) navnTxtF.getScene().getWindow()).close();
    }
    // Gets the Hashmaps and set the values to the correct textAreas and checkboxes
    public void loadDocumentContent(Document doc) {
        HashMap<String, Boolean> checkBoxesFromDoc = doc.getSelectedCheckboxes();
        HashMap<String, String> textFieldsFromDoc = doc.getTextFieldInput();

        for (CheckBox checkBox : checkBoxes.keySet()) {
            for (String IDFromDoc : checkBoxesFromDoc.keySet()) {
                if (checkBox.getId().equals(IDFromDoc)) {
                    checkBox.setSelected(checkBoxesFromDoc.get(IDFromDoc));
                }
            }
        }

        for (TextField textField : textFields.keySet()) {
            for (String IDFromDoc : textFieldsFromDoc.keySet()) {
                if (textField.getId().equals(IDFromDoc)) {
                    textField.setText(textFieldsFromDoc.get(IDFromDoc));
                }
            }
        }
    }
}
