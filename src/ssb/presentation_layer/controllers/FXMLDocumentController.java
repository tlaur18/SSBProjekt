package ssb.presentation_layer.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ssb.domain_layer.Document;
import ssb.domain_layer.Employee;
import ssb.domain_layer.Resident;

/**
 *
 * @author oliver
 */
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
    private ObservableList<Document> observableDocuments;
    private final String NEW_BEBOER_CHOICE = "Ny beboer";
    private final List<Resident> residents = new ArrayList<>(Arrays.asList(new Resident("Oliver"), new Resident("Nicolai"), new Resident("Michael")));
    private final Employee employee = new Employee(Employee.job.SAGSBEHANDLER, residents);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ArrayList med VUM-dokumenter
        List<Document> documents = new ArrayList<>(Arrays.asList(new Document(residents.get(0), Document.type.SAGSÅBNING),
            new Document(residents.get(1), Document.type.UDREDNING), new Document(residents.get(2), Document.type.BESTILLING)));

        // ObservableList som opdateres hvis "documents" Arraylisten opdateres
        observableDocuments = FXCollections.observableArrayList(documents);

        // Forbinder tableView med observable list med dokumenterne
        vumDocumentTableView.setItems(observableDocuments);
        for (Object column : vumDocumentTableView.getColumns().toArray()) {
            TableColumn<Document, ?> column1 = (TableColumn<Document, ?>) column;
            column1.prefWidthProperty().bind(vumDocumentTableView.widthProperty().divide(5));
        }
    }

    @FXML
    public void openDocumentAction(MouseEvent event) {
        Document selectedDocument = vumDocumentTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null && event.getClickCount() == 2) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("VUM-Dokument");
            alert.setHeaderText("Se her! Jeg fandt et dokument :O *Insert pikachu face here*");
            alert.setContentText(selectedDocument.toString());
            alert.showAndWait();
        }
    }

    @FXML
    private void sagerOnAction(ActionEvent event) {
        System.out.println("Sager pressed");
    }

    @FXML
    private void createVUMOnAction(ActionEvent event) {
        List<String> choices = new ArrayList<>();
        if (employee.getJobType().equals(Employee.job.SAGSBEHANDLER)) {
            choices.add(NEW_BEBOER_CHOICE);
        }
        employee.getResidents().forEach((resident) -> {
            choices.add(resident.getName());
        });

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Opret VUM-Dokument");
        dialog.setHeaderText("Vælg beboer");
        dialog.setContentText("Vælg den beboer VUM-dokumentet skal tilknyttes til: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }
    }
}
