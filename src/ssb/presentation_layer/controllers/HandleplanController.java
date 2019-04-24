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
import ssb.domain_layer.InformationBridge;

public class HandleplanController extends VumDocumentController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTextFields();
        loadCheckBoxes();

        if (chosenDocument != null) {
            loadDocumentContent(chosenDocument);
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
        textAreas.put(navnTxtF, null);
        textAreas.put(cprTxtF, null);
        textAreas.put(adresseTxtF, null);
        textAreas.put(paaroerendeTxtF, null);
        textAreas.put(mailTxtF, null);
        textAreas.put(telefonTxtF, null);
        textAreas.put(datoTxtF, null);
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) throws IOException {
        saveInfo();
        if (chosenDocument != null) {
            saveExistingDocument();
        } else {
            saveNewDocument();
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) navnTxtF.getScene().getWindow()).close();
    }
}
