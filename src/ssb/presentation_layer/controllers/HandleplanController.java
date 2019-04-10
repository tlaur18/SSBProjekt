package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ssb.domain_layer.Document;

public class HandleplanController implements Initializable {

    @FXML
    private TextField navnTxtF;
    @FXML
    private TextField cprTxtF;
    @FXML
    private TextField adresseTxtF;
    @FXML
    private TextField paaroerendeTxtF;
    @FXML
    private TextField mailTxtF;
    @FXML
    private TextField telefonTxtF;
    @FXML
    private TextField datoTxtF;
    @FXML
    private CheckBox vaergemaalCheck1;
    @FXML
    private CheckBox vaergemaalCheck2;
    @FXML
    private CheckBox vaergemaalCheck3;
    @FXML
    private CheckBox vaergemaalCheck4;
    @FXML
    private CheckBox repraesentationCheck1;
    @FXML
    private CheckBox repraesentationCheck2;
    @FXML
    private CheckBox repraesentationCheck3;

    private HashMap<CheckBox, Boolean> selectedBoxes;
    private HashMap<TextField, String> textInput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedBoxes = new HashMap();
        textInput = new HashMap();
        loadTextFields();
        loadCheckBoxes();
    }

    private void loadCheckBoxes() {
        selectedBoxes.put(vaergemaalCheck1, Boolean.FALSE);
        selectedBoxes.put(vaergemaalCheck2, Boolean.FALSE);
        selectedBoxes.put(vaergemaalCheck3, Boolean.FALSE);
        selectedBoxes.put(vaergemaalCheck4, Boolean.FALSE);
        selectedBoxes.put(repraesentationCheck1, Boolean.FALSE);
        selectedBoxes.put(repraesentationCheck2, Boolean.FALSE);
        selectedBoxes.put(repraesentationCheck3, Boolean.FALSE);
    }

    private void loadTextFields() {
        textInput.put(navnTxtF, null);
        textInput.put(cprTxtF, null);
        textInput.put(adresseTxtF, null);
        textInput.put(paaroerendeTxtF, null);
        textInput.put(mailTxtF, null);
        textInput.put(telefonTxtF, null);
        textInput.put(datoTxtF, null);
    }
    
    @FXML
    private void saveBtnHandler(ActionEvent event) throws IOException {
        Document doc = new Document(Document.type.HANDLEPLAN);
        doc.setSelectedCheckboxes(selectedBoxes);
        doc.setTextFieldInput(textInput);
        
        URL url = new File("src/ssb/presentation_layer/fxml_documents/main_layout.fxml").toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent) loader.load();
        FXMLDocumentController fxmlDocumentController = loader.getController();
        fxmlDocumentController.saveDocument(doc);
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) navnTxtF.getScene().getWindow()).close();
    }
}
