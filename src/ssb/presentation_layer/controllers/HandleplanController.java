package ssb.presentation_layer.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private DocumentManager documentManager;
    private Resident chosenResident;
    private HashMap<CheckBox, Boolean> checkBoxes;
    private HashMap<TextField, String> textFields;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        documentManager = InformationBridge.getINSTANCE().getDocumentManager();
        chosenResident = InformationBridge.getINSTANCE().getChosenResident();
        checkBoxes = new HashMap();
        textFields = new HashMap();
        loadTextFields();
        loadCheckBoxes();

        if (InformationBridge.getINSTANCE().getChosenDocument() != null) {
            loadDocumentContent(InformationBridge.getINSTANCE().getChosenDocument());
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

        Document doc = new Document(Document.type.HANDLEPLAN);
        doc.setSelectedCheckboxes(checkBoxes);
        doc.setTextFieldInput(textFields);
        documentManager.addDocument(doc, chosenResident);

        Stage stage = (Stage) navnTxtF.getScene().getWindow();
        stage.close();
    }

    private void saveInfo() {
        for (CheckBox checkBox : checkBoxes.keySet()) {
            checkBoxes.put(checkBox, checkBox.isSelected());
        }

        for (TextField textField : textFields.keySet()) {
            textFields.put(textField, textField.getText());
        }
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) navnTxtF.getScene().getWindow()).close();
    }

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
