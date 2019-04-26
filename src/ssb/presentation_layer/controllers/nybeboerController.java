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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private void saveBtnHandler(ActionEvent event) throws MalformedURLException, IOException {
        if(requiredBoxCheck()) {
        Resident newRes = new Resident(fornavnTxtF.getText(), efternavnTxtF1.getText(), telefonTxtF.getText(), cprTxtF.getText());
        newRes.setCityName(byTxtf.getText());
        newRes.setPostCode(postnrTxtf.getText());
        newRes.setStreetName(vejnavnTxtf.getText());
        InformationBridge.getInstance().getLoggedInEmployee().addResident(newRes);

        URL controllerUrl = new File("src/ssb/presentation_layer/fxml_documents/sagerTab.fxml").toURL();
        FXMLLoader loader = new FXMLLoader(controllerUrl);
        loader.load();
        sagerTabController = loader.getController();
        sagerTabController.selectVUMDialog(newRes);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        }
        else {
            
        }

    }
    
    private boolean requiredBoxCheck(){
        if (cprTxtF.getText().length() >= 4 && fornavnTxtF.getText().length() >= 4
                && efternavnTxtF1.getText().length() >= 4 && !telefonTxtF.getText().isEmpty()
                && !mailTxtF.getText().isEmpty()) {
            return true;
        }
        else{
            requiredFieldsLbl.setVisible(true);
            return false;
        }
    }

    @FXML
    private void sogBtnHandler(ActionEvent event) {
        sogTxTF.setText("mangler stadig....");
    }

}
