package ssb.presentation_layer.controllers.vum_document_controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.document.Document;

public class HandleplanController extends VumDocumentController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTabPaneChildren(tabPane);
        if (chosenDocument != null) {
            loadDocumentContent(chosenDocument);
        }
        fillOutKnownFields();
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) {
        saveInfo();
        if (chosenDocument != null) {
            saveExistingDocument();
        } else {
            saveNewDocument(Document.type.HANDLEPLAN);
        }
        InformationBridge.getInstance().putChosenResident(null);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) saveButton.getScene().getWindow()).close();
    }

    private void fillOutKnownFields() {
        if (chosenResident != null) { // If the document is created the first time, chosen resident is not null
            TextField borgerNavnTxtF = findChildInTab("borgerNavnTxtF", tabPane.getTabs().get(0), TextField.class);
            TextField borgerCPRTxtF = findChildInTab("borgerCPRTxtF", tabPane.getTabs().get(0), TextField.class);
            TextField addresseTxtF = findChildInTab("addresseTxtF", tabPane.getTabs().get(0), TextField.class);
            TextField telefonTxtF = findChildInTab("telefonTxtF", tabPane.getTabs().get(0), TextField.class);
            TextField mailTxtF = findChildInTab("mailTxtF", tabPane.getTabs().get(0), TextField.class);

            String residentName = chosenResident.getFirstName() + " " + chosenResident.getLastName();
            borgerNavnTxtF.setText(residentName);

            borgerCPRTxtF.setText(chosenResident.getCprNr());

            String residentAddressString = chosenResident.getStreetName() + " " + chosenResident.getCityName() + " " + chosenResident.getPostCode();
            addresseTxtF.setText(residentAddressString);

            telefonTxtF.setText(chosenResident.getPhoneNr());
            mailTxtF.setText(chosenResident.getEmail());
        }
    }
}
