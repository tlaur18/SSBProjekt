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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import ssb.domain_layer.document.Document;
import ssb.domain_layer.document.DocumentManager;
import ssb.domain_layer.person.Employee;
import ssb.domain_layer.Home;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.person.Resident;

public class SagerTabController implements Initializable {

    @FXML
    private TableView<Document> vumDocumentTableView;
    @FXML
    private Button createVUMDocBtn;

    private final DocumentManager documentManager = DocumentManager.getInstance();
    private final InformationBridge informationBridge = InformationBridge.getInstance();
    private Employee loggedInEmployee;
    private Home currentHome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO - Sagsbehandlere skal kun kunne oprette ny beboere med en ny sag hvor en resident bliver lavet med alle oplysninger
        
        loggedInEmployee = informationBridge.getLoggedInEmployee();
        currentHome = informationBridge.getCurrentHome();
        vumDocumentTableView.setItems(documentManager.getAllDocuments());

        // Changes the create new document button if the user is Sagsbahandler
        if (loggedInEmployee.canCreateNewProcessDoc()) {
            createVUMDocBtn.setText("Opret ny sag");
        }

        // Columns width set to 20%
        for (Object column : vumDocumentTableView.getColumns().toArray()) {
            TableColumn<Document, ?> column1 = (TableColumn<Document, ?>) column;
            column1.prefWidthProperty().bind(vumDocumentTableView.widthProperty().divide(5));
        }
        openDocumentListener();
    }

    // Double click to open the document
    private void openDocumentListener() {
        vumDocumentTableView.setRowFactory((TableView<Document> tv) -> {
            TableRow<Document> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                    && event.getClickCount() == 2) {
                    Document clickedRow = row.getItem();
                    InformationBridge.getInstance().setChosenDocument(clickedRow);
                    loadDocumentController(clickedRow);
                }
            });
            return row;
        });
    }

    // Selects the right FXML to load, based on the document type.
    private void loadDocumentController(Document document) {
        switch (document.getType()) {
            case HANDLEPLAN:
                loadDocument("src/ssb/presentation_layer/fxml_documents/vum_documents/handleplan/handleplan.fxml", "Handleplan");
                break;
            case SAGSÅBNING:
                loadDocument("src/ssb/presentation_layer/fxml_documents/vum_documents/sagsåbning/sagsåbning.fxml", "Sagsåbning");
                break;
        }
    }

    @FXML
    private void createVUMOnAction(ActionEvent event) {
        if (loggedInEmployee.canCreateNewProcessDoc()) {
            createNewResident();
        } else {
            chooseExistingResident();
        }
    }

    private void createNewResident() {
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

    private void chooseExistingResident() {
        // Ensures that document controller doesn't start loading documents.
        InformationBridge.getInstance().setChosenDocument(null);

        //Loads the Employee's Residents
        List<Resident> residentDialogChoices = new ArrayList<>();
        for (Resident res : currentHome.getResidents()) {
            residentDialogChoices.add(res);
        }
        ChoiceDialog<Resident> dialog = new ChoiceDialog("", residentDialogChoices);
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
                chooseExistingResident();
            } else {
                selectVUMDialog(result.get());
            }
        }
    }

    public void selectVUMDialog(Resident resident) {
        InformationBridge.getInstance().putChosenResident(resident);

        //Initializes the drop down menu.
        if (loggedInEmployee.canCreateNewProcessDoc()) {
            loadDocument("src/ssb/presentation_layer/fxml_documents/vum_documents/sagsåbning/sagsåbning.fxml", "Sagsåbning");
        } else {
            List<String> documentDialogChoices = new ArrayList<>();
            documentDialogChoices.add(Document.type.HANDLEPLAN.toString());
            ChoiceDialog<String> dialog = new ChoiceDialog<>("", documentDialogChoices);
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
                        case "HANDLEPLAN":
                            loadDocument("src/ssb/presentation_layer/fxml_documents/vum_documents/handleplan/handleplan.fxml", "Handleplan");
                            break;
                    }
                }
            }
        }
    }

    private void loadDocument(String fileURL, String vumDocumentTitle) {
        try {
            URL url = new File(fileURL).toURL();
            FXMLLoader loaderHandleplan = new FXMLLoader(url);
            Parent rootHandleplan = (Parent) loaderHandleplan.load();
            Stage stageHandleplan = new Stage();
            stageHandleplan.setMinHeight(425);
            stageHandleplan.setMinWidth(650);
            stageHandleplan.setScene(new Scene(FXMLLoader.load(url)));
            stageHandleplan.setTitle(vumDocumentTitle);
            stageHandleplan.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
}
