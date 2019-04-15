package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        try {
            //Tilføjer test-dokumenter ud fra nogle forudgenererede Strings:
            documentManager.addDocument(documentManager.decodeDocument("rO0ABXNyABlzc2IuZG9tYWluX2xheWVyLkRvY3VtZW50wlcCV5/LHRACAAhMAAxjcmVhdGlvbkRhdGV0ABBMamF2YS91dGlsL0RhdGU7TAAMZG9jdW1lbnROYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAAIZWRpdERhdGVxAH4AAUwADHJlc2lkZW50TmFtZXEAfgACTAASc2VsZWN0ZWRDaGVja0JveGVzdAATTGphdmEvdXRpbC9IYXNoTWFwO0wACXRleHRBcmVhc3EAfgADTAAOdGV4dEZpZWxkSW5wdXRxAH4AA0wABHR5cGV0ACBMc3NiL2RvbWFpbl9sYXllci9Eb2N1bWVudCR0eXBlO3hwc3IADmphdmEudXRpbC5EYXRlaGqBAUtZdBkDAAB4cHcIAAAAAAN5b5p4dAALT2xpU0FHIFRlc3RzcQB+AAZ3CAAAAWohXIikeHQABk9saXZlcnNyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAABh3CAAAACAAAAAWdAAYZHJvcGRvd25wYWFSb2VyZW5kZUNoZWNrc3IAEWphdmEubGFuZy5Cb29sZWFuzSBygNWc+u4CAAFaAAV2YWx1ZXhwAHQAFnZhZXJnZW1hYWxLb250YWt0Q2hlY2tzcQB+AA4BdAASZHJvcGRvd25BbmRlbkNoZWNrcQB+AA90ABJkcm9wZG93bkFuZHJlQ2hlY2txAH4AD3QAFGhlbnZlbmRlbHNlQ2hlY2tOZWoxcQB+ABF0ABNoZW52ZW5kZWxzZUphQ2hlY2sxcQB+AA90ABRkcm9wZG93bkluZHNhdHNDaGVja3EAfgAPdAATZHJvcGRvd25ib3JnZXJDaGVja3EAfgAPdAAOZm9ybG9iTmVqQ2hlY2txAH4AEXQAG3JlcHJlc2VudGF0aW9uQmlzaWRkZXJDaGVja3EAfgAPdAAVdmFlcmdlbWFhbFBhcmFnN0NoZWNrcQB+AA90ABByZXR0aWdoZWRlckNoZWNrcQB+ABF0ABRoZW52ZW5kZWxzZUNoZWNrTmVqMnEAfgARdAAbcmVwcmVzZW50YXRpb25GdWxkbWFndENoZWNrcQB+AA90ABZwYXJ0c1JlcHJlc2VudGFudENoZWNrcQB+ABF0ABV2YWVyZ2VtYWFsUGFyYWc2Y2hlY2txAH4AEXQAE2hlbnZlbmRlbHNlQ2hlY2tKYTJxAH4AD3QAEmRyb3Bkb3duTGFlZ2VDaGVja3EAfgARdAAVZHJvcGRvd25Ib3NwaXRhbENoZWNrcQB+AA90AA1mb3Jsb2JKYUNoZWNrcQB+AA90ABV2YWVyZ2VtYWFsUGFyYWc1Q2hlY2txAH4AD3QAGmRyb3Bkb3duQW5kZW5Lb21tbXVuZUNoZWNrcQB+AA94c3EAfgALP0AAAAAAAAx3CAAAABAAAAADdAARZm9ybG9iQWZ0YWxlclR4dEF0ACRIYW4ga29tbWVyIGVmdGVyIG9zISBSRUQgSkVSIFNFTFYhISF0ABJyZXByZXNlbnRhdGlvblR4dEF0AAlISsOGTFAhISF0ABBoZW52ZW5kZWxzZVRYVGExdABSSmVnIHZlZCBpa2tlIGh2YWQgZGV0IGVyLCBtZW4gZGVyIGVyIGV0IGVsbGVyIGFuZGV0IGdydWVsaWd0IGdhbHQgbWVkIGRlbiBoZXIgZ3V0LnhwfnIAHnNzYi5kb21haW5fbGF5ZXIuRG9jdW1lbnQkdHlwZQAAAAAAAAAAEgAAeHIADmphdmEubGFuZy5FbnVtAAAAAAAAAAASAAB4cHQAC1NBR1PDhUJOSU5H"), oliver);
            documentManager.addDocument(documentManager.decodeDocument("rO0ABXNyABlzc2IuZG9tYWluX2xheWVyLkRvY3VtZW50wlcCV5/LHRACAAhMAAxjcmVhdGlvbkRhdGV0ABBMamF2YS91dGlsL0RhdGU7TAAMZG9jdW1lbnROYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAAIZWRpdERhdGVxAH4AAUwADHJlc2lkZW50TmFtZXEAfgACTAASc2VsZWN0ZWRDaGVja0JveGVzdAATTGphdmEvdXRpbC9IYXNoTWFwO0wACXRleHRBcmVhc3EAfgADTAAOdGV4dEZpZWxkSW5wdXRxAH4AA0wABHR5cGV0ACBMc3NiL2RvbWFpbl9sYXllci9Eb2N1bWVudCR0eXBlO3hwc3IADmphdmEudXRpbC5EYXRlaGqBAUtZdBkDAAB4cHcIAAAAAAIJIVp4dAALVGhvU0FHIFRlc3RzcQB+AAZ3CAAAAWohYUlkeHQABlRob21hc3NyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAABh3CAAAACAAAAAWdAAWdmFlcmdlbWFhbEtvbnRha3RDaGVja3NyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAB0ABhkcm9wZG93bnBhYVJvZXJlbmRlQ2hlY2txAH4AD3QAEmRyb3Bkb3duQW5kZW5DaGVja3EAfgAPdAASZHJvcGRvd25BbmRyZUNoZWNrcQB+AA90ABRoZW52ZW5kZWxzZUNoZWNrTmVqMXEAfgAPdAATaGVudmVuZGVsc2VKYUNoZWNrMXNxAH4ADgF0ABNkcm9wZG93bmJvcmdlckNoZWNrcQB+AA90ABRkcm9wZG93bkluZHNhdHNDaGVja3EAfgAPdAAOZm9ybG9iTmVqQ2hlY2txAH4AD3QAG3JlcHJlc2VudGF0aW9uQmlzaWRkZXJDaGVja3EAfgAPdAAQcmV0dGlnaGVkZXJDaGVja3EAfgAPdAAVdmFlcmdlbWFhbFBhcmFnN0NoZWNrcQB+AA90ABRoZW52ZW5kZWxzZUNoZWNrTmVqMnEAfgAPdAAbcmVwcmVzZW50YXRpb25GdWxkbWFndENoZWNrcQB+AA90ABZwYXJ0c1JlcHJlc2VudGFudENoZWNrcQB+AA90ABNoZW52ZW5kZWxzZUNoZWNrSmEycQB+ABV0ABV2YWVyZ2VtYWFsUGFyYWc2Y2hlY2txAH4AD3QAEmRyb3Bkb3duTGFlZ2VDaGVja3EAfgAPdAAVZHJvcGRvd25Ib3NwaXRhbENoZWNrcQB+AA90AA1mb3Jsb2JKYUNoZWNrcQB+ABV0ABV2YWVyZ2VtYWFsUGFyYWc1Q2hlY2txAH4AD3QAGmRyb3Bkb3duQW5kZW5Lb21tbXVuZUNoZWNrcQB+AA94c3EAfgALP0AAAAAAAAx3CAAAABAAAAADdAARZm9ybG9iQWZ0YWxlclR4dEF0ADVuZWouLi4gbmVqLi4uIGRldCBlciBmb3IgdGlkbGlndC4KVMOmbmsgcMOlIGLDuHJuZW5lLnQAEnJlcHJlc2VudGF0aW9uVHh0QXQAFFNlbmQgZGV0IHR1bmdlIHNreXRzdAAQaGVudmVuZGVsc2VUWFRhMXQAaUh2YWQgZXIgc3RhdHVzLCBNZXN0ZXI/CgpKYWEuLi4gRGV0IHNlciBpa2tlIGZvciBnb2R0IHVkLi4uClZpIGhhciBtaXN0ZXQgaGVsZSB0cmVkamUga3ZhZHJhbnQuCgptaWc6IDBvMHhwfnIAHnNzYi5kb21haW5fbGF5ZXIuRG9jdW1lbnQkdHlwZQAAAAAAAAAAEgAAeHIADmphdmEubGFuZy5FbnVtAAAAAAAAAAASAAB4cHQAC1NBR1PDhUJOSU5H"), thomas);
            documentManager.addDocument(documentManager.decodeDocument("rO0ABXNyABlzc2IuZG9tYWluX2xheWVyLkRvY3VtZW50wlcCV5/LHRACAAhMAAxjcmVhdGlvbkRhdGV0ABBMamF2YS91dGlsL0RhdGU7TAAMZG9jdW1lbnROYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAAIZWRpdERhdGVxAH4AAUwADHJlc2lkZW50TmFtZXEAfgACTAASc2VsZWN0ZWRDaGVja0JveGVzdAATTGphdmEvdXRpbC9IYXNoTWFwO0wACXRleHRBcmVhc3EAfgADTAAOdGV4dEZpZWxkSW5wdXRxAH4AA0wABHR5cGV0ACBMc3NiL2RvbWFpbl9sYXllci9Eb2N1bWVudCR0eXBlO3hwc3IADmphdmEudXRpbC5EYXRlaGqBAUtZdBkDAAB4cHcIAAAAAAFUnL54dAALVGhvSEFOIFRlc3RzcQB+AAZ3CAAAAWohv/e5eHQABlRob21hc3NyABFqYXZhLnV0aWwuSGFzaE1hcAUH2sHDFmDRAwACRgAKbG9hZEZhY3RvckkACXRocmVzaG9sZHhwP0AAAAAAAAx3CAAAABAAAAAHdAAQdmFlcmdlbWFhbENoZWNrM3NyABFqYXZhLmxhbmcuQm9vbGVhbs0gcoDVnPruAgABWgAFdmFsdWV4cAF0ABVyZXByYWVzZW50YXRpb25DaGVjazFxAH4AD3QAEHZhZXJnZW1hYWxDaGVjazRzcQB+AA4AdAAVcmVwcmFlc2VudGF0aW9uQ2hlY2szcQB+AA90ABVyZXByYWVzZW50YXRpb25DaGVjazJxAH4AEnQAEHZhZXJnZW1hYWxDaGVjazFxAH4AEnQAEHZhZXJnZW1hYWxDaGVjazJxAH4AD3hwc3EAfgALP0AAAAAAAAx3CAAAABAAAAAHdAAIZGF0b1R4dEZ0AAoxNS0wNC0yMDE5dAALdGVsZWZvblR4dEZ0AAgzMDk2MzY2OXQAC2FkcmVzc2VUeHRGdAAGVWtlbmR0dAAHY3ByVHh0RnQACjEyMTIxMjEyMTJ0AAhuYXZuVHh0RnQAGVRob21hcyBTdGVlbmZlbGR0IExhdXJzZW50AAhtYWlsVHh0RnQAFnRsYXVyMThAc3R1ZGVudC5zZHUuZGt0AA9wYWFyb2VyZW5kZVR4dEZ0AAlCbmFnLXPDpmx4fnIAHnNzYi5kb21haW5fbGF5ZXIuRG9jdW1lbnQkdHlwZQAAAAAAAAAAEgAAeHIADmphdmEubGFuZy5FbnVtAAAAAAAAAAASAAB4cHQACkhBTkRMRVBMQU4="), thomas);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

            //Her er lidt tests på det der serializable-noget:
            String encodedString = documentManager.encodeDocument(selectedDocument);
            System.out.println(encodedString);
            System.out.println(documentManager.decodeDocument(encodedString).toString());
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
        InformationBridge.getINSTANCE().setChosenDocument(null);        //Sikrer at dokumentcontrollerne ikke begynder at loade dokumenter.

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
