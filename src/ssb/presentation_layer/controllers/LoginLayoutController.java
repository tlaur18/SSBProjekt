package ssb.presentation_layer.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        String userID = loginManager.checkUserInformation(enteredUsername, enteredPassword);
        loginManager.setLoggedInUser(userID);
        System.out.println(userID);
//        if (validInformation) {
//            InformationBridge.getINSTANCE().setLoggedInEmployee(loginManager.setLoggedInUser());
//        }
//        InformationBridge.getINSTANCE().setLoggedInEmployee(loggedInEmployee);
        
        
        
        
        
        
        
        
        
//        try {
//            URL url3 = new File("src/ssb/presentation_layer/fxml_documents/main_layout.fxml").toURL();
//            FXMLLoader loader = new FXMLLoader(url3);
//            Parent root = (Parent) loader.load();
//            FXMLDocumentController controller = loader.getController();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
