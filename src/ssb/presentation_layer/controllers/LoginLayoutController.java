package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ssb.domain_layer.Address;
import ssb.domain_layer.Department;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.EmployeeManager;
import ssb.domain_layer.InformationBridge;

/**
 * FXML Controller class
 *
 * @author olive
 */
public class LoginLayoutController implements Initializable {

    @FXML
    private TextField userNameTxtField;
    @FXML
    private PasswordField passwordTxtField;
    @FXML
    private Label ugyldigtLoginLabel;
    @FXML
    private Button logInBtn;
    @FXML
    private ChoiceBox<String> homeChoice;
    
    
    private String enteredUsername;
    private String enteredPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeChoice.getItems().add("Vammelby");
        homeChoice.getItems().add("Dejligby");
        homeChoice.setValue("Vammelby");
    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        enteredUsername = userNameTxtField.getText();
        enteredPassword = passwordTxtField.getText();
        EmployeeManager loginManager = new EmployeeManager();
        boolean correctCredentials = loginManager.checkUserLogIn(enteredUsername, enteredPassword);
        if (correctCredentials && homeChoice.getValue() != null) {
            DocumentManager.getInstance().setDocumentsForEmployee();
            Department currentDepartment = new Department(homeChoice.getValue(), Department.specializations.STOFMISBRUG, new Address());
            if (homeChoice.getValue().equals("Vammelby")) {
                currentDepartment.setId(1);
            } else {
                currentDepartment.setId(2);
            }
            InformationBridge.getInstance().setCurrentDepartment(currentDepartment);
            changeStage();
        } else {
            ugyldigtLoginLabel.setVisible(true);
        }
    }

    private void changeStage() {
        try {
            URL url3 = new File("src/ssb/presentation_layer/fxml_documents/main_layout.fxml").toURL();
            FXMLLoader loader = new FXMLLoader(url3);
            Parent root = (Parent) loader.load();
            Stage mainStage = new Stage();
            mainStage.setMinHeight(450);
            mainStage.setMinWidth(800);
            mainStage.setScene(new Scene(root));
            ((Stage) ugyldigtLoginLabel.getScene().getWindow()).close(); //close login stage
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
