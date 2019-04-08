package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import ssb.domain_layer.Process;
import ssb.domain_layer.Document;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Employee.Sagsbehandler;
import ssb.domain_layer.Employee.SocialPædagog;
import ssb.domain_layer.Resident;
import ssb.presentation_layer.controllers.Test_viewController;
import ssb.presentation_layer.fxml_documents.InformationBridge;

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

    private Test_viewController test_viewController;
    private final Employee employee = new Sagsbehandler("Michael", "tester", "telefon-nummer", "cpr nummer");
    private final Resident oliver = new Resident("Oliver", "van Komen", "05050505", "0202-432125");
    private final Resident thomas = new Resident("Thomas", "Steenfeldt", "782357823", "1245435-1234");
    private ObservableList<Document> allDocuments = FXCollections.observableArrayList();
    private final Process mentaltHandicap = new Process(oliver);
    private final Process retarderet = new Process(thomas);
    private ObservableList<Document> observableDocuments;
    private boolean updateList = true;
    private final String NEW_BEBOER_CHOICE = "Ny beboer";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        retarderet.addDocument(new Document(Document.type.FAGLIGVURDERING));
        retarderet.addDocument(new Document(Document.type.FAGLIGVURDERING));
        retarderet.addDocument(new Document(Document.type.FAGLIGVURDERING));
        retarderet.addDocument(new Document(Document.type.SAGSÅBNING));
        thomas.addProcess(retarderet);
        employee.addResident(oliver);
        employee.addResident(thomas);
        oliver.addProcess(mentaltHandicap);
        mentaltHandicap.addDocument(new Document(Document.type.UDREDNING));
        mentaltHandicap.addDocument(new Document(Document.type.AFGØRELSE));
        mentaltHandicap.addDocument(new Document(Document.type.BESTILLING));
        // ObservableList som opdateres hvis "documents" Arraylisten opdateres

        // Forbinder tableView med observable list med dokumenterne
       observableDocuments = mentaltHandicap.getDocuments();
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
        List<Resident> choices = new ArrayList<>();
        if (employee.canCreateNewProcessDoc()) {
            choices.add(oliver);
        }

        employee.getResidents().forEach((resident) -> {
            choices.add(resident);
        });

        ChoiceDialog<Resident> dialog = new ChoiceDialog<>(oliver, choices);
        dialog.setTitle("Opret VUM-Dokument");
        dialog.setHeaderText("Vælg beboer");
        dialog.setContentText("Vælg den beboer VUM-dokumentet skal tilknyttes til: ");

        Optional<Resident> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get().toString());
            selectVUMDialog(result.get());
        }
    }

    private void selectVUMDialog(Resident resident) {
        List<String> choices = new ArrayList<>();
        if (employee.canCreateNewProcessDoc()) {
            choices.add(Document.type.SAGSÅBNING.toString());
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
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Opret VUM-Dokument");
        dialog.setHeaderText("Vælg dokument type til: " + resident.toString());
        dialog.setContentText("Vælg en dokument type fra listen: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
            try {
                Stage stage = new Stage();
                URL url = new File("src/ssb/presentation_layer/fxml_documents/test_view.fxml").toURL();
                stage.setScene(new Scene(FXMLLoader.load(url)));
                stage.setTitle("Morten er awesome");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveDocument(Document doc) {
        System.out.println("this is second base!");
        Resident res = InformationBridge.getINSTANCE().getChosenResident();
        System.out.println(res.toString());

        for (Process pros : res.getProcess()) {
            pros.addDocument(doc);
            System.out.println(pros.getDocuments());
            

        }
        System.out.println("this is 3rd base, you will never get here");
    }


}
