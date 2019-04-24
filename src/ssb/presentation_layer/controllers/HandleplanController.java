package ssb.presentation_layer.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import ssb.domain_layer.Document;

public class HandleplanController extends VumDocumentController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTabPaneChildren(tabPane);

        if (chosenDocument != null) {
            loadDocumentContent(chosenDocument);
        }
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) {
        saveInfo();
        if (chosenDocument != null) {
            saveExistingDocument();
        } else {
            saveNewDocument(Document.type.HANDLEPLAN);
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
        ((Stage) saveButton.getScene().getWindow()).close();
    }
}
