package ssb.presentation_layer.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class SagsåbningController extends VumDocumentController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCheckboxes();
        loadTextAreas();
        if (chosenDocument != null) {
            loadDocumentContent(chosenDocument);
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
        if (chosenDocument != null) {
            saveExistingDocument();
        } else {
            saveNewDocument();
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();

    }
}
