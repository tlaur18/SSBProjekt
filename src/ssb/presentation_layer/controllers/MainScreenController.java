package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ssb.domain_layer.Employee.Administrator;
import ssb.domain_layer.InformationBridge;

public class MainScreenController implements Initializable {
    
    @FXML
    private ImageView homeBtn;
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox vBoxMenu;
    @FXML
    private Button oversigtbttnid;
    @FXML
    private Button nybrugerid;
    @FXML
    private Button editbrugerid;
    @FXML
    private ImageView backBtn;
    @FXML
    private TextField searchCommandTxtField;
    @FXML
    private Button sagerFaneBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO - Notification screen loading
        
        //removes the admin buttons and disables them
        oversigtbttnid.setVisible(false);
        oversigtbttnid.setDisable(true);
        nybrugerid.setVisible(false);
        nybrugerid.setDisable(true);
        editbrugerid.setVisible(false);
        editbrugerid.setDisable(true);
        //Controls if the logged in employee is administrator.
        if(InformationBridge.getInstance().getLoggedInEmployee() instanceof Administrator){
        adminLogin();
        }
    }
    
    @FXML
    private void sagerOnAction(ActionEvent event) {
        loadFXML("sagerTab");
    }
    
    @FXML
    private void HandleplanOnAction(ActionEvent event) {
        loadFXML("handleplantest");
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

    public void adminLogin() {
        //removes all the buttons in the left Menu
        vBoxMenu.getChildren().removeAll(vBoxMenu.getChildren());
        
        // Shows the admin button in the left menu
        oversigtbttnid.setDisable(false);
        oversigtbttnid.setVisible(true);
        nybrugerid.setDisable(false);
        nybrugerid.setVisible(true);
        editbrugerid.setDisable(false);
        editbrugerid.setVisible(true);
        //Adds the buttons to the Vbox menu.
        vBoxMenu.getChildren().add(oversigtbttnid);
        vBoxMenu.getChildren().add(nybrugerid);
        vBoxMenu.getChildren().add(editbrugerid);
        
    }

    @FXML
    private void oversigtOnAction(ActionEvent event) {
        loadFXML("adminOversigt");
    }

    @FXML
    private void nybrugerOnAction(ActionEvent event) {
        loadFXML("adminNyBruger");
    }

    @FXML
    private void editbrugerOnAction(ActionEvent event) {
        System.out.println("Still to be done");
    }
}
