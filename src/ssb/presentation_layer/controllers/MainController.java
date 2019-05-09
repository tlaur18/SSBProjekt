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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ssb.domain_layer.person.EmployeeManager;
import ssb.domain_layer.InformationBridge;

public class MainController implements Initializable {

    @FXML
    private ImageView homeBtn;
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox vBoxMenu;
    @FXML
    private Button oversigtbttnid;
    @FXML
    private ProgressIndicator centerLoaderIndicator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Controls if the logged in employee is administrator.
        if (InformationBridge.getInstance().getLoggedInEmployee().canUseAdminRights()) {
            adminLogin();
        } else {
            loadFXML("tabs/notifications");
        }
    }

    @FXML
    private void sagerOnAction(ActionEvent event) {
        loadFXML("tabs/sagerTab");
    }

    @FXML
    private void HandleplanOnAction(ActionEvent event) {
        loadFXML("tabs/handleplantest");
    }

    private void loadFXML(String documentName) {
        try {
            URL url = new File("src/ssb/presentation_layer/fxml_documents/" + documentName + ".fxml").toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = (Parent) loader.load();
            borderPane.setCenter(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void logOutHandler(MouseEvent event) {
        InformationBridge.getInstance().resetSystem();
        try {
            URL url = new File("src/ssb/presentation_layer/fxml_documents/login_layout.fxml").toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = (Parent) loader.load();
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            ((Stage) homeBtn.getScene().getWindow()).close(); //close login stage
            loginStage.show();
        } catch (IOException e) {
            System.out.println("Log out handler: " + e.getMessage());
        }
    }

    @FXML
    private void homeButtonHandler(MouseEvent event) {
        loadFXML("tabs/notifications");
    }

    public void adminLogin() {
        //removes all the buttons in the left Menu
        //Loads all employees from the database and create new employees
        EmployeeManager.getInstance().clearObservableList();
        EmployeeManager.getInstance().loadAllEmployess();
        vBoxMenu.getChildren().removeAll(vBoxMenu.getChildren());

        // Shows the admin button in the left menu
        oversigtbttnid.setDisable(false);
        oversigtbttnid.setVisible(true);
        //Adds the button to the Vbox menu.
        vBoxMenu.getChildren().add(oversigtbttnid);
    }

    @FXML
    private void oversigtOnAction(ActionEvent event) {
        loadFXML("admin/adminOversigt");
    }
}
