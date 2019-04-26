package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ssb.domain_layer.Document;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.Resident;

public class SagerTabController implements Initializable {

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
    public void openDocumentAction(MouseEvent event) {
        Document selectedDocument = vumDocumentTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null && event.getClickCount() == 2) {
            InformationBridge.getInstance().setChosenDocument(selectedDocument);
            loadDocumentController(selectedDocument);
        }
    }

    // Selects the right FXML to load, based on the document type.
    private void loadDocumentController(Document document) {
        switch (document.getType()) {
            case HANDLEPLAN:
                loadDocument("src/ssb/presentation_layer/fxml_documents/handleplan.fxml", "Handleplan");
                break;
            case SAGSÅBNING:
                loadDocument("src/ssb/presentation_layer/fxml_documents/sagsåbning.fxml", "Sagsåbning");
                break;
        }
    }

    @FXML
    private void createVUMOnAction(ActionEvent event) {
        List<Resident> choices = new ArrayList<>();
        //Sikrer at dokumentcontrollerne ikke begynder at loade dokumenter.
        InformationBridge.getInstance().setChosenDocument(null);

        Resident newRes = new Resident("Opret Ny", "Beboer", "123567890", "1234567890");
        // Adds a "Ny Beboer" choice if the loggedInEmployee has authority to create a new Resident.
        if (loggedInEmployee.canCreateNewProcessDoc()) {
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vælg beboer");
                alert.setHeaderText(null);
                alert.setContentText("Ingen beboer valgt.");
                alert.showAndWait();
                //Allows the user to try again by showing the first dialog again.
                createVUMOnAction(new ActionEvent());
            } else if (result.get().equals(newRes)) {
                newResident();
            } else {
                selectVUMDialog(result.get());
            }
        }
    }

    private void newResident() {
        try {
            Stage nybeboerStage = new Stage();
            URL nybeboerUrl = new File("src/ssb/presentation_layer/fxml_documents/nybeboer.fxml").toURL();
            nybeboerStage.setScene(new Scene(FXMLLoader.load(nybeboerUrl)));
            nybeboerStage.setTitle("Sagsåbning");
            nybeboerStage.setMinHeight(425);
            nybeboerStage.setMinWidth(650);
            nybeboerStage.show();
        } catch (IOException e) {
            System.out.println("Sagsåbinigs stage change: " + e.getMessage());
        }
    }

    public void selectVUMDialog(Resident resident) {
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
            //Displays an alert if the user did not pick any documenttype
            if (result.get().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vælg dokument");
                alert.setHeaderText(null);
                alert.setContentText("Ingen dokumenttype valgt.");
                alert.showAndWait();
                //Allows the user to try again by showing the first dialog again.
                selectVUMDialog(resident);
            } else {
                switch (result.get()) {
                    case "SAGSÅBNING":
                        loadDocument("src/ssb/presentation_layer/fxml_documents/sagsåbning.fxml", "Sagsåbning");
                        break;
                    case "HANDLEPLAN":
                        loadDocument("src/ssb/presentation_layer/fxml_documents/Handleplan.fxml", "Handleplan");
                }
            }
        }
    }

    private void loadDocument(String fileURL, String vumDocumentTitle) {
        try {
            URL urlHandleplan = new File(fileURL).toURL();
            FXMLLoader loaderHandleplan = new FXMLLoader(urlHandleplan);
            Parent rootHandleplan = (Parent) loaderHandleplan.load();
            Stage stageHandleplan = new Stage();
            stageHandleplan.setMinHeight(425);
            stageHandleplan.setMinWidth(650);
            stageHandleplan.setScene(new Scene(FXMLLoader.load(urlHandleplan)));
            stageHandleplan.setTitle(vumDocumentTitle);
            stageHandleplan.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
