package ssb.presentation_layer.controllers.vum_document_controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.document.Document;
import ssb.domain_layer.person.Employee;

public class SagsåbningController extends VumDocumentController implements Initializable {

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
        Employee loggedinEmployee = InformationBridge.getInstance().getLoggedInEmployee();
        if (!loggedinEmployee.canCreateReportDocs() && !loggedinEmployee.canCreateNewProcessDoc()) {
            saveButton.setDisable(true);
        }
    }
    
    @FXML
    public void cancelButtonOnAction(ActionEvent event) {
        ((Stage) saveButton.getScene().getWindow()).close();
    }

    @FXML
    public void saveButtonOnAction(ActionEvent event) {
        saveInfo();
        if (chosenDocument != null) {
            saveExistingDocument();
        } else {
            saveNewDocument(Document.type.SAGSÅBNING);
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
