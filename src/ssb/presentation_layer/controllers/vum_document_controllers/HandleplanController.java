package ssb.presentation_layer.controllers.vum_document_controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import ssb.domain_layer.document.Document;

public class HandleplanController extends VumDocumentController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private TabPane tabPane;
    private DatePicker datePicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePicker = (DatePicker) findChildInTab("datePicker", tabPane.getTabs().get(0));
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LocalDate date = datePicker.getValue();
                System.out.println("selected date: " + date);
            }
        });
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
