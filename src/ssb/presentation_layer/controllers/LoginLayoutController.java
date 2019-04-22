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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ssb.domain_layer.LoginManager;

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
    private Button logInBtn;
    @FXML
    private Label ugyldigtLoginLabel;
    private String enteredUsername;
    private String enteredPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        enteredUsername = userNameTxtField.getText();
        enteredPassword = passwordTxtField.getText();
        LoginManager loginManager = new LoginManager();
        boolean correctCredentials = loginManager.checkUserLogIn(enteredUsername, enteredPassword);
        if (correctCredentials) {
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
