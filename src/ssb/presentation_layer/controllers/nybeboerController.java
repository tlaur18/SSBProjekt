package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ssb.data_layer.contracts.CPRRegisterPersonsContract;
import ssb.domain_layer.CPRRegisterManager;
import ssb.domain_layer.person.EmployeeManager;
import ssb.domain_layer.Home;
import ssb.domain_layer.person.Resident;
import ssb.domain_layer.InformationBridge;

public class nybeboerController implements Initializable {

    @FXML
    private TextField cprTxtF;
    @FXML
    private TextField mailTxtF;
    @FXML
    private TextField phoneTxtF;
    @FXML
    private TextField zipTxtF;
    @FXML
    private TextField streetTxtF;
    @FXML
    private TextField cityTxtF;
    @FXML
    private Button saveButton;
    @FXML
    private TextField searchTxtF;
    @FXML
    private TextField firstNameTxtF;
    @FXML
    private TextField lastNameTxtF;
    @FXML
    private Label requiredFieldsLbl;
    @FXML
    private Label searchLabel;
    @FXML
    private Label invalidSearchLabel;

    private final CPRRegisterManager cprRegisterManager = CPRRegisterManager.getInstance();
    private boolean searchCPRisValid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchTxtF.textProperty().addListener((observable, oldValue, newValue) -> {
            //This checks whether the CPR nr is valid.
            boolean isNumeric = true;
            try {
                Double.parseDouble(newValue);
            } catch (NumberFormatException e) {
                isNumeric = false;
            }

            if (newValue.length() != 10 || !isNumeric) {
                searchLabel.setVisible(false);
                invalidSearchLabel.setVisible(true);
                searchCPRisValid = false;
            } else {
                searchLabel.setVisible(true);
                invalidSearchLabel.setVisible(false);
                searchCPRisValid = true;
            }
        });
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) firstNameTxtF.getScene().getWindow()).close();
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) {
        if (!informationIsValid()) {
            requiredFieldsLbl.setVisible(true);
            return;
        }

        Resident newRes = new Resident(firstNameTxtF.getText(), lastNameTxtF.getText(), phoneTxtF.getText(), cprTxtF.getText());
        newRes.setCityName(cityTxtF.getText());
        newRes.setPostCode(zipTxtF.getText());
        newRes.setStreetName(streetTxtF.getText());
        Home currentHome = InformationBridge.getInstance().getCurrentHome();
        EmployeeManager employeeManager = EmployeeManager.getInstance();
        employeeManager.addResidentToHome(currentHome.getId(), newRes);
        currentHome.addResident(newRes);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        try {
            URL controllerUrl = new File("src/ssb/presentation_layer/fxml_documents/tabs/sagerTab.fxml").toURL();
            FXMLLoader loader = new FXMLLoader(controllerUrl);
            loader.load();
            SagerTabController sagerTabController = loader.getController();
            sagerTabController.selectVUMDialog(newRes);
        } catch (MalformedURLException ex) {
            System.out.println("Invalid URL: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Could not load url: " + ex.getMessage());
        }
    }

    private boolean informationIsValid() {
        //TODO - Make this better
        if (cprTxtF.getText().length() >= 4 && firstNameTxtF.getText().length() >= 4
                && lastNameTxtF.getText().length() >= 4 && !phoneTxtF.getText().isEmpty()
                && !mailTxtF.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void searchBtnHandler(ActionEvent event) {
        if (!searchCPRisValid) {
            return;
        }
        
        String cprToSearch = searchTxtF.getText();
        HashMap<String, String> personData = cprRegisterManager.searchCPRRegister(cprToSearch);

        if (personData.isEmpty()) {
            Dialog d = new Alert(Alert.AlertType.INFORMATION);
            d.setTitle("Opret ny beboer");
            d.setHeaderText("CPR-nummer ikke fundet i register");
            d.setContentText("Kontrollér CPR-nummeret i tekstfeltet og prøv igen.");
            d.showAndWait();
            return;
        }

        String personCPR = personData.get(CPRRegisterPersonsContract.COLUMN_CPR);
        String personFirstName = personData.get(CPRRegisterPersonsContract.COLUMN_FIRST_NAME);
        String personLastName = personData.get(CPRRegisterPersonsContract.COLUMN_LAST_NAME);
        String personPhoneNr = personData.get(CPRRegisterPersonsContract.COLUMN_PHONE);
        String personMail = personData.get(CPRRegisterPersonsContract.COLUMN_MAIL);
        String personCity = personData.get(CPRRegisterPersonsContract.COLUMN_CITY);
        String personZip = personData.get(CPRRegisterPersonsContract.COLUMN_ZIP_CODE);
        String personStreet = personData.get(CPRRegisterPersonsContract.COLUMN_STREET);

        cprTxtF.setText(personCPR);
        firstNameTxtF.setText(personFirstName);
        lastNameTxtF.setText(personLastName);
        phoneTxtF.setText(personPhoneNr);
        mailTxtF.setText(personMail);
        cityTxtF.setText(personCity);
        zipTxtF.setText(personZip);
        streetTxtF.setText(personStreet);
    }
}
