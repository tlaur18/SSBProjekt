/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssb.presentation_layer.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ssb.domain_layer.Document;
import ssb.domain_layer.Employee.Administrator;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Employee.Sagsbehandler;
import ssb.domain_layer.Employee.SocialPædagog;
import ssb.domain_layer.Employee.Socialrådgiver;
import ssb.domain_layer.Employee.Vikar;
import ssb.domain_layer.EmployeeManager;
import ssb.domain_layer.InformationBridge;

/**
 * FXML Controller class
 *
 * @author morte
 */
public class AdminNyBrugerController implements Initializable {

    @FXML
    private Button saveBttn;
    @FXML
    private TextField brugernavnTxtf;
    @FXML
    private TextField kodeordTxtf;
    @FXML
    private TextField fornavnTxtf;
    @FXML
    private TextField efternavnTxtf;
    @FXML
    private TextField cprFxtf;
    @FXML
    private TextField tlkTxtf;
    @FXML
    private Label requiredFieldsLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void saveBttn(ActionEvent event) {
        EmployeeManager employeeManager = new EmployeeManager();
            List<String> choices = new ArrayList<>();

        //Initializes the drop down menu.
        choices.add("SAGSBEHANDLER");
        choices.add("SOCIALRÅDGIVER");
        choices.add("SOCIALPÆDAGOG");
        choices.add("ADMINISTRATOR");
        choices.add("VIKAR");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Opret ny Beboer");
        dialog.setHeaderText("Vælg Rolle: ");
        dialog.setContentText("Vælg en rolle fra listen: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            //Displays an alert if the user did not pick any documenttype
            if (result.get().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vælg Rolle");
                alert.setHeaderText(null);
                alert.setContentText("Ingen rolle valgt.");
                alert.showAndWait();
                //Allows the user to try again by showing the first dialog again.
            } else {
                switch (result.get()) {
                    case "SAGSBEHANDLER":
                        Employee sagsbehandler = new Sagsbehandler(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                        employeeManager.addEmployeeToDB(sagsbehandler, brugernavnTxtf.getText(), kodeordTxtf.getText());
                        break;
                    case "SOCIALRÅDGIVER":
                        Employee socialraadgiver = new Socialrådgiver(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                        employeeManager.addEmployeeToDB(socialraadgiver, brugernavnTxtf.getText(), kodeordTxtf.getText());
                        break;
                    case "SOCIALPÆDAGOG":
                        Employee socialpaedagog = new SocialPædagog(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                        employeeManager.addEmployeeToDB(socialpaedagog, brugernavnTxtf.getText(), kodeordTxtf.getText());
                        break;
                    case "ADMINISTRATOR":
                        Employee administrator = new Administrator(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                        employeeManager.addEmployeeToDB(administrator, brugernavnTxtf.getText(), kodeordTxtf.getText());
                        break;
                    case "VIKAR":
                        Employee vikar = new Vikar(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                        employeeManager.addEmployeeToDB(vikar, brugernavnTxtf.getText(), kodeordTxtf.getText());
                        break;
                }
            }
        }
    }
    
}
