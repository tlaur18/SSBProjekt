package ssb.presentation_layer.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ssb.domain_layer.Document;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.Resident;
import ssb.domain_layer.InformationBridge;

public class SagsåbningController implements Initializable {

    @FXML
    private TextArea henvendelseTXTa1;
    @FXML
    private CheckBox henvendelseCheckNej1;
    @FXML
    private CheckBox henvendelseJaCheck1;
    @FXML
    private CheckBox dropdownborgerCheck;
    @FXML
    private CheckBox dropdownpaaRoerendeCheck;
    @FXML
    private CheckBox dropdownLaegeCheck;
    @FXML
    private CheckBox dropdownHospitalCheck;
    @FXML
    private CheckBox dropdownAndenCheck;
    @FXML
    private CheckBox dropdownIndsatsCheck;
    @FXML
    private CheckBox dropdownAndenKommmuneCheck;
    @FXML
    private CheckBox dropdownAndreCheck;
    @FXML
    private CheckBox henvendelseCheckJa2;
    @FXML
    private CheckBox henvendelseCheckNej2;
    @FXML
    private CheckBox vaergemaalParag5Check;
    @FXML
    private CheckBox vaergemaalParag6check;
    @FXML
    private CheckBox vaergemaalParag7Check;
    @FXML
    private CheckBox vaergemaalKontaktCheck;
    @FXML
    private CheckBox representationBisidderCheck;
    @FXML
    private CheckBox partsRepresentantCheck;
    @FXML
    private CheckBox representationFuldmagtCheck;
    @FXML
    private TextArea representationTxtA;
    @FXML
    private CheckBox rettighederCheck;
    @FXML
    private CheckBox forlobJaCheck;
    @FXML
    private CheckBox forlobNejCheck;
    @FXML
    private TextArea forlobAftalerTxtA;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private DocumentManager documentManager;
    private Resident chosenResident;
    private HashMap<CheckBox, Boolean> checkBoxes;
    private HashMap<TextArea, String> textAreas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        documentManager = DocumentManager.getInstance();
        chosenResident = InformationBridge.getInstance().getChosenResident();
        checkBoxes = new HashMap<>();
        textAreas = new HashMap<>();
        loadCheckboxes();
        loadTextAreas();

        if (InformationBridge.getInstance().getChosenDocument() != null) {
            loadDocumentContent(InformationBridge.getInstance().getChosenDocument());
        }
    }

    //Adding all checkboxes to the HashMap, to be used in later iteration.
    private void loadCheckboxes() {
        checkBoxes.put(henvendelseCheckNej1, Boolean.FALSE);
        checkBoxes.put(henvendelseJaCheck1, Boolean.FALSE);
        checkBoxes.put(dropdownborgerCheck, Boolean.FALSE);
        checkBoxes.put(dropdownpaaRoerendeCheck, Boolean.FALSE);
        checkBoxes.put(dropdownLaegeCheck, Boolean.FALSE);
        checkBoxes.put(dropdownHospitalCheck, Boolean.FALSE);
        checkBoxes.put(dropdownAndenCheck, Boolean.FALSE);
        checkBoxes.put(dropdownIndsatsCheck, Boolean.FALSE);
        checkBoxes.put(dropdownAndenKommmuneCheck, Boolean.FALSE);
        checkBoxes.put(dropdownAndreCheck, Boolean.FALSE);
        checkBoxes.put(henvendelseCheckJa2, Boolean.FALSE);
        checkBoxes.put(henvendelseCheckNej2, Boolean.FALSE);
        checkBoxes.put(vaergemaalParag5Check, Boolean.FALSE);
        checkBoxes.put(vaergemaalParag6check, Boolean.FALSE);
        checkBoxes.put(vaergemaalParag7Check, Boolean.FALSE);
        checkBoxes.put(vaergemaalKontaktCheck, Boolean.FALSE);
        checkBoxes.put(representationBisidderCheck, Boolean.FALSE);
        checkBoxes.put(partsRepresentantCheck, Boolean.FALSE);
        checkBoxes.put(representationFuldmagtCheck, Boolean.FALSE);
        checkBoxes.put(rettighederCheck, Boolean.FALSE);
        checkBoxes.put(forlobJaCheck, Boolean.FALSE);
        checkBoxes.put(forlobNejCheck, Boolean.FALSE);
    }

    //Adding all TextAreas to Array, to be used in later iteration.
    private void loadTextAreas() {
        textAreas.put(henvendelseTXTa1, null);
        textAreas.put(representationTxtA, null);
        textAreas.put(forlobAftalerTxtA, null);
    }
    
        @FXML
    private void cancelButtonOnAction(ActionEvent event) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
    

    @FXML
    private void saveButtonOnAction(ActionEvent event) throws MalformedURLException, IOException {
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
    
    // Creating a new Document object, saves the checkboxes and textareas to it, and adds it to the residents list of Documents
    public void saveNewDocument() {
        Document doc = new Document(Document.type.SAGSÅBNING);
        doc.setSelectedCheckboxes(checkBoxes);
        doc.setTextAreas(textAreas);
        documentManager.addDocument(doc, chosenResident);
    }
    
    // adds the checkboxes and textAreas to the existing document
        public void saveExistingDocument() {
        Document document = InformationBridge.getInstance().getChosenDocument();
        document.setSelectedCheckboxes(checkBoxes);
        document.setTextAreas(textAreas);
    }
        
    // Saves all the Checkboxes and textAreas to their hashmaps
    private void saveInfo() {
        for (CheckBox checkbox : checkBoxes.keySet()) {
            checkBoxes.put(checkbox, checkbox.isSelected());
        }

        for (TextArea textArea : textAreas.keySet()) {
            textAreas.put(textArea, textArea.getText());
        }
    }
    // Gets the Hashmaps and set the values to the correct textAreas and checkboxes
    public void loadDocumentContent(Document doc) {
        HashMap<String, Boolean> checkBoxesFromDoc = doc.getSelectedCheckboxes();
        HashMap<String, String> textAreasFromDoc = doc.getTextAreas();

        for (CheckBox checkBox : checkBoxes.keySet()) {
            for (String IDFromDoc : checkBoxesFromDoc.keySet()) {
                if (checkBox.getId().equals(IDFromDoc)) {
                    checkBox.setSelected(checkBoxesFromDoc.get(IDFromDoc));
                }
            }
        }
        for (TextArea textArea : textAreas.keySet()) {
            for (String IDFromDoc : textAreasFromDoc.keySet()) {
                if (textArea.getId().equals(IDFromDoc)) {
                    textArea.setText(textAreasFromDoc.get(IDFromDoc));
                }
            }
        }
    }
}
