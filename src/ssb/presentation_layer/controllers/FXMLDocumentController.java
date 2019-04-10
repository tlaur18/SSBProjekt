package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ssb.domain_layer.Document;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.InformationBridge;

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

    private final InformationBridge informationBridge = InformationBridge.getINSTANCE();
    private ObservableList<Document> observableDocuments;
    private Employee employee;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Henter employee der lige er logget ind fra informationBridge
        employee = informationBridge.getLoggedInEmployee();
        
        // ObservableList som opdateres hvis "documents" Arraylisten opdateres
        observableDocuments = FXCollections.observableArrayList(employee.getResidentDocuments());
        // Forbinder tableView med observable list med dokumenterne
        vumDocumentTableView.setItems(observableDocuments);
        // Sætter kolonner til at fylde 20% af bredden
        for (Object column : vumDocumentTableView.getColumns().toArray()) {
            TableColumn<Document, ?> column1 = (TableColumn<Document, ?>) column;
            column1.prefWidthProperty().bind(vumDocumentTableView.widthProperty().divide(5));
        }

        if (!employee.canAccessCreateDocBtn()) {
            createVUMDocBtn.setVisible(false);
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
        if (employee.canCreateNewProcessDoc()) {
            choices.add("Ny beboer");
        }
        employee.getResidents().forEach((resident) -> {
            choices.add(resident.getFirstName() + resident.getLastName());
        });

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Opret VUM-Dokument");
        dialog.setHeaderText("Vælg beboer");
        dialog.setContentText("Vælg den beboer VUM-dokumentet skal tilknyttes til: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
            try {
                Stage stage = new Stage();
                URL url = new File("src/ssb/presentation_layer/fxml_documents/test_view.fxml").toURL();
                stage.setScene(new Scene(FXMLLoader.load(url)));
                stage.setTitle("Oliver forsøger");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void logOutHandler(MouseEvent event) {
        InformationBridge.getINSTANCE().resetSystem();
        try {
            URL url = new File("src/ssb/presentation_layer/fxml_documents/login_layout.fxml").toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = (Parent) loader.load();
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            ((Stage) homeBtn.getScene().getWindow()).close(); //close login stage
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
