package ssb.presentation_layer.controllers.vum_document_controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TextField addresseTxtF;
    private TextField telefonTxtF;
    private TextField mailTxtF;
    private Label addressValidatorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTabPaneChildren(tabPane);
        if (chosenDocument != null) {
            loadDocumentContent(chosenDocument);
        }
        addresseTxtF = findChildInTab("addresseTxtF", tabPane.getTabs().get(0), TextField.class);
        telefonTxtF = findChildInTab("telefonTxtF", tabPane.getTabs().get(0), TextField.class);
        mailTxtF = findChildInTab("mailTxtF", tabPane.getTabs().get(0), TextField.class);
        fillOutKnownFields();
        setValidationListeners();
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) {
        if (validateFields()) {
            saveInfo();
            if (chosenDocument != null) {
                saveExistingDocument();
            } else {
                saveNewDocument(Document.type.HANDLEPLAN);
            }
            InformationBridge.getInstance().putChosenResident(null);
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } else {
            // TODO - alert dialog med udfyld alle røde felter
        }
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) saveButton.getScene().getWindow()).close();
    }

    private void fillOutKnownFields() {
        if (chosenResident != null) { // If the document is created the first time, chosen resident is not null
            TextField borgerNavnTxtF = findChildInTab("borgerNavnTxtF", tabPane.getTabs().get(0), TextField.class);
            TextField borgerCPRTxtF = findChildInTab("borgerCPRTxtF", tabPane.getTabs().get(0), TextField.class);

            String residentName = chosenResident.getFirstName() + " " + chosenResident.getLastName();
            borgerNavnTxtF.setText(residentName);

            borgerCPRTxtF.setText(chosenResident.getCprNr());

            String residentAddressString = chosenResident.getStreetName() + " 5 " + chosenResident.getPostCode() + " " + chosenResident.getCityName();
            addresseTxtF.setText(residentAddressString);

            telefonTxtF.setText(chosenResident.getPhoneNr());
            mailTxtF.setText(chosenResident.getEmail());
        }
    }

    private boolean validateFields() {
        return false;
    }

    private void setValidationListeners() {
        addressValidatorLabel = findChildInTab("addressValidatorLabel", tabPane.getTabs().get(0), Label.class);
        System.out.println(addressValidatorLabel);
        addresseTxtF.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if (!addressIsValid()) {
                    System.out.println("Focus lost?");
                    addressValidatorLabel.setVisible(true);
                } else {
                    addressValidatorLabel.setVisible(false);
                }
            }

        });
    }

    private boolean addressIsValid() {
        String addressTxt = addresseTxtF.getText();
        if (addressTxt != null) {
            String[] addressElements = addressTxt.split(" ");
            if (addressElements.length >= 4) { // Skal følge formen [roadname] [houseNumber] [zip code] [city]
                for (Character charas : addressElements[0].toCharArray()) { // roadname check
                    if (Character.isDigit(charas)) {
                        return false;
                    }
                }
                try {
                    Double.parseDouble(addressElements[1]); // houseNumber check
                    Integer.parseInt(addressElements[2]); // zip code check
                } catch (NumberFormatException e) {
                    return false;
                }
                for (Character charas : addressElements[3].toCharArray()) { // city check
                    if (Character.isDigit(charas)) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
