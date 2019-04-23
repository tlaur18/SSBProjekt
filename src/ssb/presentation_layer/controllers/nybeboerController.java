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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ssb.domain_layer.Document;
import ssb.domain_layer.Resident;
import ssb.domain_layer.InformationBridge;
import ssb.presentation_layer.fxml_documents.SagerTabController;

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
    private Button sogBttn;

    private Resident chosenResident;
    @FXML
    private TextField fornavnTxtF;
    @FXML
    private TextField efternavnTxtF1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) fornavnTxtF.getScene().getWindow()).close();
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) throws MalformedURLException {
        Resident newRes = new Resident(fornavnTxtF.getText(), efternavnTxtF1.getText(), telefonTxtF.getText(), cprTxtF.getText());
        newRes.setCityName(byTxtf.getText());
        newRes.setPostCode(postnrTxtf.getText());
        newRes.setStreetName(vejnavnTxtf.getText());
        InformationBridge.getInstance().getLoggedInEmployee().addResident(newRes);
    
          Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        List<String> choices = new ArrayList<>();

        //Initializes the drop down menu.
        if (InformationBridge.getInstance().getLoggedInEmployee().canCreateNewProcessDoc()) {
            choices.add(Document.type.SAGSÅBNING.toString());
        }
        choices.add(Document.type.HANDLEPLAN.toString());

        InformationBridge.getInstance().putChosenResident(newRes);
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Opret VUM-Dokument");
        dialog.setHeaderText("Vælg dokument type til: " + newRes.toString());
        dialog.setContentText("Vælg en dokument type fra listen: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            switch (result.get()) {
                case "SAGSÅBNING":
                    try {
                        Stage handleplanStage = new Stage();
                        URL handleplanUrl = new File("src/ssb/presentation_layer/fxml_documents/sagsåbning.fxml").toURL();
                        handleplanStage.setScene(new Scene(FXMLLoader.load(handleplanUrl)));
                        handleplanStage.setTitle("Sagsåbning");
                        handleplanStage.setMinHeight(425);
                        handleplanStage.setMinWidth(650);
                        handleplanStage.show();
                    } catch (IOException e) {
                        System.out.println("Sagsåbinigs stage change: " + e.getMessage());
                    }
                    break;
                case "HANDLEPLAN":
                    try {
                        Stage handleplanStage = new Stage();
                        URL handleplanUrl = new File("src/ssb/presentation_layer/fxml_documents/Handleplan.fxml").toURL();
                        handleplanStage.setScene(new Scene(FXMLLoader.load(handleplanUrl)));
                        handleplanStage.setTitle("Handleplan");
                        handleplanStage.setMinHeight(425);
                        handleplanStage.setMinWidth(650);
                        handleplanStage.show();
                    } catch (IOException e) {
                        System.out.println("Handleplan stage change: " + e.getMessage());
                    }
                    break;
            }
        }
    }
      


    @FXML
    private void sogBtnHandler(ActionEvent event) {
        sogTxTF.setText("mangler stadig....");
    }

}
