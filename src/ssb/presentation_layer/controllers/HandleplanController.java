package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ssb.domain_layer.Document;
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

    private HashMap<CheckBox, Boolean> checkBoxes;
    private HashMap<TextField, String> textFields;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkBoxes = new HashMap();
        textFields = new HashMap();
        loadTextFields();
        loadCheckBoxes();

        if (InformationBridge.getINSTANCE().getChosenDocument() != null) {
            System.out.println("Loading Documents");
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

        System.out.println(checkBoxes);

        Document doc = new Document(Document.type.HANDLEPLAN);
        doc.setSelectedCheckboxes(checkBoxes);
        doc.setTextFieldInput(textFields);

        URL url = new File("src/ssb/presentation_layer/fxml_documents/main_layout.fxml").toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent) loader.load();
        FXMLDocumentController fxmlDocumentController = loader.getController();
        fxmlDocumentController.saveDocument(doc);
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) navnTxtF.getScene().getWindow()).close();
    }

    private void saveInfo() {
        for (CheckBox checkBox : checkBoxes.keySet()) {
            checkBoxes.put(checkBox, checkBox.isSelected());
        }
        
        for (TextField textField : textFields.keySet()) {
            textFields.put(textField, textField.getText());
        }
    }

    public void loadDocumentContent(Document doc) {
        HashMap<CheckBox, Boolean> checkBoxesFromDoc = doc.getSelectedCheckboxes();
        HashMap<TextField, String> textFieldsFromDoc = doc.getTextFieldInput();

        for (CheckBox checkBox : checkBoxes.keySet()) {
            for (CheckBox checkBoxFromDoc : checkBoxesFromDoc.keySet()) {
                if (checkBox.getId().equals(checkBoxFromDoc.getId())) {
                    checkBox.setSelected(checkBoxesFromDoc.get(checkBoxFromDoc));
                }
            }
        }

        for (TextField textField : textFields.keySet()) {
            for (TextField textFieldFromDoc : textFieldsFromDoc.keySet()) {
                if (textField.getId().equals(textFieldFromDoc.getId())) {
                    textField.setText(textFieldsFromDoc.get(textFieldFromDoc));
                }
            }
        }
    }
}
