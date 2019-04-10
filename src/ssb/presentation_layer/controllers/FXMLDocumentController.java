package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Employee.Sagsbehandler;
import ssb.domain_layer.Resident;
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

    private HandleplanController handleplanController;
    private SagsåbningController sagsåbningController;
    private final Employee employee = new Sagsbehandler("Michael", "tester", "telefon-nummer", "cpr nummer");
    private final Resident oliver = new Resident("Oliver", "van Komen", "05050505", "0202-432125");
    private final Resident thomas = new Resident("Thomas", "Steenfeldt", "782357823", "1245435-1234");
    private ObservableList<Document> allDocuments = FXCollections.observableArrayList();
    private final Process mentaltHandicap = new Process(oliver);
    private final Process retarderet = new Process(thomas);
    private ObservableList<Document> observableDocuments;
    private boolean updateList = true;
    private final DocumentManager docuManager = new DocumentManager();
    private final String NEW_BEBOER_CHOICE = "Ny beboer";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        InformationBridge.getINSTANCE().setLoggedInEmployee(employee);
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
//        docuManager.loadAllDocuments(InformationBridge.getINSTANCE().getLoggedInEmployee());
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
    public void openDocumentAction(MouseEvent event) throws IOException {
        Document selectedDocument = vumDocumentTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null && event.getClickCount() == 2) {
            InformationBridge.getINSTANCE().setChosenDocument(selectedDocument);
            loadDocumentController(selectedDocument);
        }
    }
        private void loadDocumentController(Document document) throws MalformedURLException, IOException {
        switch(document.getType()) {
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
            handleplanController = loaderHandleplan.getController();
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
            sagsåbningController = loaderSagsaabning.getController();
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
    private void sagerOnAction(ActionEvent event) {
        System.out.println("Sager pressed");
    }

    @FXML
    private void createVUMOnAction(ActionEvent event) throws MalformedURLException {
        List<Resident> choices = new ArrayList<>();
        if (InformationBridge.getINSTANCE().getLoggedInEmployee().canCreateNewProcessDoc()) {
            choices.add(oliver);
        }

        InformationBridge.getINSTANCE().getLoggedInEmployee().getResidents().forEach((resident) -> {
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

    private void selectVUMDialog(Resident resident) throws MalformedURLException {
        List<String> choices = new ArrayList<>();
        if (InformationBridge.getINSTANCE().getLoggedInEmployee().canCreateNewProcessDoc()) {
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
         switch (result.get()) {
             case "SAGSÅBNING":
                  try{
                     Stage handleplanStage = new Stage();
                     URL handleplanUrl = new File("src/ssb/presentation_layer/fxml_documents/sagsåbning.fxml").toURL();
                     handleplanStage.setScene(new Scene(FXMLLoader.load(handleplanUrl)));
                     handleplanStage.setTitle("Dette er en ny stage");
                     handleplanStage.show();
                 }catch(IOException e){
                     e.printStackTrace();
                 }
                 break;
             case "AFGØRELSE":
                 break;
             case "BESTILLING":
                 break;
             case "HANDLEPLAN":
                 try{
                     Stage handleplanStage = new Stage();
                     URL handleplanUrl = new File("src/ssb/presentation_layer/fxml_documents/Handleplan.fxml").toURL();
                     handleplanStage.setScene(new Scene(FXMLLoader.load(handleplanUrl)));
                     handleplanStage.setTitle("Dette er en ny stage");
                     handleplanStage.show();
                 }catch(IOException e){
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

    public void saveDocument(Document doc) {
        System.out.println("this is second base!");
        Resident res = InformationBridge.getINSTANCE().getChosenResident();
        System.out.println(res.toString());

        for (Process pros : res.getProcess()) {
            pros.addDocument(doc);
//            docuManager.addDocument(doc);
            System.out.println(pros.getDocuments());
        }
        System.out.println("this is 3rd base, you will never get here");
    }
}
