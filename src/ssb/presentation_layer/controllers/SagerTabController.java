package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ssb.domain_layer.Document;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.Resident;

public class SagerTabController implements Initializable {

    @FXML
    private TextField searchResidentTxtField;
    @FXML
    private TextField searchDocNameTxtField;
    @FXML
    private Button createVUMDocBtn;
    @FXML
    private TableView<Document> vumDocumentTableView;

    private final DocumentManager documentManager = DocumentManager.getInstance();
    private InformationBridge informationBridge;
    private Employee loggedInEmployee;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        informationBridge = InformationBridge.getInstance();
        //Henter loggedInEmployee der lige er logget ind fra informationBridge
        loggedInEmployee = informationBridge.getLoggedInEmployee();
        // Forbinder tableView med observable list med dokumenterne
        vumDocumentTableView.setItems(documentManager.getAllDocuments());
        // Sætter kolonner til at fylde 20% af bredden
        for (Object column : vumDocumentTableView.getColumns().toArray()) {
            TableColumn<Document, ?> column1 = (TableColumn<Document, ?>) column;
            column1.prefWidthProperty().bind(vumDocumentTableView.widthProperty().divide(5));
        }
    }

    // Get the selected document from the tableview and opens it, if it´s clicked twice.
    @FXML
    public void openDocumentAction(MouseEvent event) throws IOException {
        Document selectedDocument = vumDocumentTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null && event.getClickCount() == 2) {
            InformationBridge.getInstance().setChosenDocument(selectedDocument);

            loadDocumentController(selectedDocument);
        }
    }

    // Selects the right FXML to load, based on the document type.
    private void loadDocumentController(Document document) throws MalformedURLException, IOException {
        switch (document.getType()) {
            case HANDLEPLAN:
                URL urlHandleplan = new File("src/ssb/presentation_layer/fxml_documents/Handleplan.fxml").toURL();
                FXMLLoader loaderHandleplan = new FXMLLoader(urlHandleplan);
                Parent rootHandleplan = (Parent) loaderHandleplan.load();
                Stage stageHandleplan = new Stage();
                stageHandleplan.setMinHeight(425);
                stageHandleplan.setMinWidth(650);
                stageHandleplan.setScene(new Scene(FXMLLoader.load(urlHandleplan)));
                stageHandleplan.setTitle("Morten er awesome");
                stageHandleplan.show();
                break;
            case SAGSÅBNING:
                URL urlSagsaabning = new File("src/ssb/presentation_layer/fxml_documents/sagsåbning.fxml").toURL();
                FXMLLoader loaderSagsaabning = new FXMLLoader(urlSagsaabning);
                Parent rootSagsaabning = (Parent) loaderSagsaabning.load();
                Stage stageSagsaabning = new Stage();
                stageSagsaabning.setMinHeight(425);
                stageSagsaabning.setMinWidth(650);
                stageSagsaabning.setScene(new Scene(FXMLLoader.load(urlSagsaabning)));
                stageSagsaabning.setTitle("Morten er awesome");
                stageSagsaabning.show();
                break;
        }
    }

    @FXML
    private void createVUMOnAction(ActionEvent event) {
        List<Resident> choices = new ArrayList<>();
        InformationBridge.getInstance().setChosenDocument(null);                //Sikrer at dokumentcontrollerne ikke begynder at loade dokumenter.

        // Adds a "Ny Beboer" choice if the loggedInEmployee has authority to create a new Resident.
        if (loggedInEmployee.canCreateNewProcessDoc()) {
            Resident newRes = new Resident("Opret Ny", "Beboer", "123567890", "1234567890");
            choices.add(newRes);
        }

        //Loads the Employee's Residents
        for (Resident res : loggedInEmployee.getResidents()) {
            choices.add(res);
        }

        ChoiceDialog<Resident> dialog = new ChoiceDialog("", choices);
        dialog.setTitle("Opret VUM-Dokument");
        dialog.setHeaderText("Vælg beboer");
        dialog.setContentText("Vælg den beboer VUM-dokumentet skal tilknyttes til: ");

        Optional<Resident> result = dialog.showAndWait();
        if (result.isPresent()) {
            //Displays an alert if the user did not pick any resident
            if (!(result.get() instanceof Resident)) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Vælg beboer");
                alert.setHeaderText(null);
                alert.setContentText("Ingen beboer valgt.");
                alert.showAndWait();
                //Allows the user to try again by showing the first dialog again.
                createVUMOnAction(new ActionEvent());
                return;
            }
            selectVUMDialog(result.get());
        }
    }

    private void selectVUMDialog(Resident resident) {
        List<String> choices = new ArrayList<>();

        //Initializes the drop down menu.
        if (loggedInEmployee.canCreateNewProcessDoc()) {
            choices.add(Document.type.SAGSÅBNING.toString());
        }
        choices.add(Document.type.HANDLEPLAN.toString());

        InformationBridge.getInstance().putChosenResident(resident);
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Opret VUM-Dokument");
        dialog.setHeaderText("Vælg dokument type til: " + resident.toString());
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
}
