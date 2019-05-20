package ssb.presentation_layer.controllers.vum_document_controllers.handleplan;

import java.net.URL;
import java.util.HashSet;
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
import ssb.presentation_layer.controllers.vum_document_controllers.VumDocumentController;

public class HandleplanController extends VumDocumentController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private TabPane tabPane;
    private TextField addresseTxtF;
    private TextField telefonTxtF;
    private TextField mailTxtF;
    private HandleplanValidation validation;

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
        validation = new HandleplanValidation(tabPane);
        setValidationListeners();
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) {
        HashSet<TextField> wrongInputTextFields = validateFields();
        if (wrongInputTextFields.isEmpty()) {
            validation.clearAllErrors();
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
            validation.setErrors(wrongInputTextFields);
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

    private HashSet<TextField> validateFields() {
        HashSet<TextField> textFields = new HashSet<>();
        if (!validation.addressIsValid(addresseTxtF.getText())) {
            textFields.add(addresseTxtF);
        } else if (! validation.phoneNumberIsValid(telefonTxtF.getText())) {
            textFields.add(telefonTxtF);
        } else if (! validation.mailIsValid(mailTxtF.getText())) {
            textFields.add(mailTxtF);
        }
        return textFields;
    }

    private void setValidationListeners() {
        validation.setAddressValidation("addressValidatorLabel", "addressValidateErrorImage", addresseTxtF);
        validation.setPhoneNumberValidation("phoneNumberValidatorLabel", "phoneNumberValidatorErrorImage", telefonTxtF);
        validation.setMailValidation("mailValidatorLabel", "mailValidatorErrorImage", mailTxtF);
    }
}
