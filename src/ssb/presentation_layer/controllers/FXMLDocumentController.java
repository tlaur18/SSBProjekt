package ssb.presentation_layer.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    private Button sortNameBtn;
    @FXML
    private Button sortResidentBtn;
    @FXML
    private Button sortTypeBtn;
    @FXML
    private Button sortEditBtn;
    @FXML
    private Button sortCreationBtn;
    @FXML
    private ListView<?> documentsListView;
    @FXML
    private TextField searchResidentTxtField;
    @FXML
    private TextField searchDocNameTxtField;
    @FXML
    private Button createVUMDocBtn;
    private final String NEW_BEBOER_CHOICE = "Ny beboer";
    private final Employee employee = new Employee(Employee.job.SAGSBEHANDLER, new ArrayList<>(Arrays.asList(new Resident("Phillip"), new Resident("Thomas"))));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
