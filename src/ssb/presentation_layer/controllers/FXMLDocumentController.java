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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ssb.domain_layer.Document;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Employee.Sagsbehandler;
import ssb.domain_layer.Resident;
import ssb.presentation_layer.fxml_documents.InformationBridge;

public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView homeBtn;
    @FXML
    private ImageView backBtn;
    @FXML
    private TextField searchCommandTxtField;
    @FXML
    private Button sagerFaneBtn;
    @FXML
    private TextField searchResidentTxtField;
    @FXML
    private TextField searchDocNameTxtField;
    @FXML
    private Button createVUMDocBtn;
    @FXML
    private TableView<Document> vumDocumentTableView;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TableColumn<?, ?> beboerColumn;

    private DocumentManager documentManager;

    //Test-personer
    private Employee employee;
    private Resident oliver;
    private Resident thomas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        documentManager = InformationBridge.getINSTANCE().getDocumentManager();

        //Initialiserer en test-Employee
        employee = new Sagsbehandler("Michael", "tester", "telefon-nummer", "cpr nummer");

        //Initialiserer test-Residents og tildeler dem til vores Employee:
        thomas = new Resident("Thomas", "Steenfeldt", "782357823", "1245435-1234");
        oliver = new Resident("Oliver", "van Komen", "05050505", "0202-432125");
        employee.addResident(oliver);
        employee.addResident(thomas);

        //Tilføjer nye dokumenter til disse Residents igennem DocumentManager:
        documentManager.addDocument(new Document(Document.type.SAGSÅBNING), oliver);
        documentManager.addDocument(new Document(Document.type.UDREDNING), oliver);
        documentManager.addDocument(new Document(Document.type.SAGSÅBNING), thomas);
        documentManager.addDocument(new Document(Document.type.HANDLEPLAN), thomas);

        //Forbinder TableView med ObservableList der indeholder dokumenterne fra DocumentManager:
        vumDocumentTableView.setItems(documentManager.getAllDocuments());
        //Gør så hver kolonne i TebleView fylder 20% af bredden.
        for (Object column : vumDocumentTableView.getColumns().toArray()) {
            TableColumn<Document, ?> column1 = (TableColumn<Document, ?>) column;
            column1.prefWidthProperty().bind(vumDocumentTableView.widthProperty().divide(5));
        }
    }

    @FXML
    public void openDocumentAction(MouseEvent event) throws IOException {
        Document selectedDocument = vumDocumentTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null && event.getClickCount() == 2) {
            InformationBridge.getINSTANCE().setChosenDocument(selectedDocument);
            loadDocumentController(selectedDocument);
        }
    }

    private void loadDocumentController(Document document) throws MalformedURLException, IOException {
        switch (document.getType()) {
            case AFGØRELSE:
                break;
            case BESTILLING:
                break;
            case FAGLIGVURDERING:
                break;
            case HANDLEPLAN:
                URL urlHandleplan = new File("src/ssb/presentation_layer/fxml_documents/Handleplan.fxml").toURL();
                FXMLLoader loaderHandleplan = new FXMLLoader(urlHandleplan);
                Parent rootHandleplan = (Parent) loaderHandleplan.load();
                Stage stageHandleplan = new Stage();
                stageHandleplan.setScene(new Scene(FXMLLoader.load(urlHandleplan)));
                stageHandleplan.setTitle("Morten er awesome");
                stageHandleplan.show();
                break;
            case INDSTILLING:
                break;
            case OPFØLGNING:
                break;
            case SAGSÅBNING:
                URL urlSagsaabning = new File("src/ssb/presentation_layer/fxml_documents/sagsåbning.fxml").toURL();
                FXMLLoader loaderSagsaabning = new FXMLLoader(urlSagsaabning);
                Parent rootSagsaabning = (Parent) loaderSagsaabning.load();
                Stage stageSagsaabning = new Stage();
                stageSagsaabning.setScene(new Scene(FXMLLoader.load(urlSagsaabning)));
                stageSagsaabning.setTitle("Morten er awesome");
                stageSagsaabning.show();
                break;
            case STATUSNOTAT:
                break;
            case UDREDNING:
                break;
        }
    }

    @FXML
    private void createVUMOnAction(ActionEvent event) {
        List<Resident> choices = new ArrayList<>();
        Resident defaultChoice = employee.getResidents().get(0);

        //Adds a "Ny Beboer" choice if the employee has authority to create a new Resident.
        if (employee.canCreateNewProcessDoc()) {
            Resident newRes = new Resident("Opret Ny", "Beboer", "123567890", "1234567890");
            choices.add(newRes);
            defaultChoice = newRes;
        }

        //Loads the Employee's Residents
        for (Resident res : employee.getResidents()) {
            choices.add(res);
        }

        ChoiceDialog<Resident> dialog = new ChoiceDialog(defaultChoice, choices);
        dialog.setTitle("Opret VUM-Dokument");
        dialog.setHeaderText("Vælg beboer");
        dialog.setContentText("Vælg den beboer VUM-dokumentet skal tilknyttes til: ");

        Optional<Resident> result = dialog.showAndWait();
        if (result.isPresent()) {
            selectVUMDialog(result.get());
        }
    }

    private void selectVUMDialog(Resident resident) {
        List<String> choices = new ArrayList<>();
        String defaultChoice = Document.type.AFGØRELSE.toString();

        //Initializes the drop down menu.
        if (employee.canCreateNewProcessDoc()) {
            choices.add(Document.type.SAGSÅBNING.toString());
            defaultChoice = Document.type.SAGSÅBNING.toString();
        }
        choices.add(Document.type.AFGØRELSE.toString());
        choices.add(Document.type.BESTILLING.toString());
        choices.add(Document.type.FAGLIGVURDERING.toString());
        choices.add(Document.type.HANDLEPLAN.toString());
        choices.add(Document.type.INDSTILLING.toString());
        choices.add(Document.type.OPFØLGNING.toString());
        choices.add(Document.type.STATUSNOTAT.toString());
        choices.add(Document.type.UDREDNING.toString());

        InformationBridge.getINSTANCE().putChosenResident(resident);
        ChoiceDialog<String> dialog = new ChoiceDialog<>(defaultChoice, choices);
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
                        handleplanStage.setTitle("Dette er en ny stage");
                        handleplanStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "AFGØRELSE":
                    break;
                case "BESTILLING":
                    break;
                case "HANDLEPLAN":
                    try {
                        Stage handleplanStage = new Stage();
                        URL handleplanUrl = new File("src/ssb/presentation_layer/fxml_documents/Handleplan.fxml").toURL();
                        handleplanStage.setScene(new Scene(FXMLLoader.load(handleplanUrl)));
                        handleplanStage.setTitle("Dette er en ny stage");
                        handleplanStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "INDSTILLING":
                    break;
                case "FAGLIGVURDERING":
                    break;
                case "OPFØLGNING":
                    break;
                case "STATUSNOTAT":
                    break;
                case "UDREDNING":
                    break;
            }
        }
    }

    private void loadFXML(String documentName) throws MalformedURLException {
        if (borderPane.getCenter() == null) {
            Parent root = null;
            URL url = new File("src/ssb/presentation_layer/fxml_documents/" + documentName + ".fxml").toURL();
            FXMLLoader loader = new FXMLLoader(url);
            try {
                root = (Parent) loader.load();
                borderPane.setCenter(root);
            } catch (IOException e) {
            }
        } else {
            borderPane.setCenter(null);
        }
    }

    @FXML
    private void sagerOnAction(ActionEvent event) {
    }

    @FXML
    private void HandleplanOnAction(ActionEvent event) {
    }
}
