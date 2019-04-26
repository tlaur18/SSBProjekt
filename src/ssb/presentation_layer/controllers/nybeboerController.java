/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.EmployeeManager;
import ssb.domain_layer.Resident;
import ssb.domain_layer.InformationBridge;

/**
 * FXML Controller class
 *
 * @author morte
 */
public class nybeboerController implements Initializable {

    @FXML
    private TextField cprTxtF;
    @FXML
    private TextField mailTxtF;
    @FXML
    private TextField telefonTxtF;
    @FXML
    private TextField postnrTxtf;
    @FXML
    private TextField vejnavnTxtf;
    @FXML
    private TextField byTxtf;
    @FXML
    private Button saveButton;
    @FXML
    private TextField sogTxTF;
    @FXML
    private TextField fornavnTxtF;
    @FXML
    private TextField efternavnTxtF1;
    private SagerTabController sagerTabController;
    @FXML
    private Label requiredFieldsLbl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) fornavnTxtF.getScene().getWindow()).close();
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) {
        if (requiredBoxCheck()) {
            Resident newRes = new Resident(fornavnTxtF.getText(), efternavnTxtF1.getText(), telefonTxtF.getText(), cprTxtF.getText());
            newRes.setCityName(byTxtf.getText());
            newRes.setPostCode(postnrTxtf.getText());
            newRes.setStreetName(vejnavnTxtf.getText());
            Employee loggedInEmployee = InformationBridge.getInstance().getLoggedInEmployee();
            EmployeeManager employeeManager = new EmployeeManager();
            employeeManager.addResidentToEmployee(loggedInEmployee.getCprNr(), newRes);
            InformationBridge.getInstance().getLoggedInEmployee().addResident(newRes);

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
            try {
                URL controllerUrl = new File("src/ssb/presentation_layer/fxml_documents/sagerTab.fxml").toURL();
                FXMLLoader loader = new FXMLLoader(controllerUrl);
                loader.load();
                sagerTabController = loader.getController();
                sagerTabController.selectVUMDialog(newRes);
            } catch (MalformedURLException ex) {
                System.out.println("Invalid URL: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("Could not load url: " + ex.getMessage());
            }
        } else {
            requiredFieldsLbl.setVisible(true);
        }

    }

    private boolean requiredBoxCheck() {
        if (cprTxtF.getText().length() >= 4 && fornavnTxtF.getText().length() >= 4
            && efternavnTxtF1.getText().length() >= 4 && !telefonTxtF.getText().isEmpty()
            && !mailTxtF.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void sogBtnHandler(ActionEvent event) {
        sogTxTF.setText("mangler stadig....");
    }

}
