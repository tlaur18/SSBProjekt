/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author morte
 */
public class AdminMainController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView homeBtn;
    @FXML
    private ImageView backBtn;
    @FXML
    private TextField searchCommandTxtField;
    @FXML
    private Button oversigtBtn;
    @FXML
    private Button nybrugerBttn;
    @FXML
    private Button editUserBttn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logOutHandler(MouseEvent event) {
    }

    @FXML
    private void oversigtOnAction(ActionEvent event) {
//        loadFXML("adminoversigt");
            
    }

    @FXML
    private void nyBrugerOnAction(ActionEvent event) {
        loadFXML("adminNyBruger");
    }

    @FXML
    private void editUserOnAction(ActionEvent event) {
//        loadFXML("adminRedigerBruger");
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
    
}
