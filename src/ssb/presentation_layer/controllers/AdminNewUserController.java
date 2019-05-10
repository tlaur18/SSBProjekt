package ssb.presentation_layer.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import ssb.domain_layer.person.Administrator;
import ssb.domain_layer.person.Employee;
import ssb.domain_layer.person.Sagsbehandler;
import ssb.domain_layer.person.SocialPædagog;
import ssb.domain_layer.person.Socialrådgiver;
import ssb.domain_layer.person.Vikar;
import ssb.domain_layer.EmployeeManager;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.person.Person;

public class AdminNewUserController implements Initializable {

    @FXML
    private Button saveBttn;
    @FXML
    private TextField brugernavnTxtf;
    @FXML
    private TextField kodeordTxtf;
    @FXML
    private TextField fornavnTxtf;
    @FXML
    private TextField efternavnTxtf;
    @FXML
    private TextField cprFxtf;
    @FXML
    private TextField tlkTxtf;
    @FXML
    private Label requiredFieldsLbl;
    private final InformationBridge informationBridge = InformationBridge.getInstance();
    private final EmployeeManager employeeManager = EmployeeManager.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (informationBridge.getChosenEmployee() != null) {
            // TODO - thread instead and then a progress indicator
            new Thread(() -> {
                loadEmployeeDetails();
            }).start();
            cprFxtf.setDisable(true);
        }
    }

    @FXML
    private void saveBttn(ActionEvent event) {
        // Controls if there is a chosen employee
        if (informationBridge.getChosenEmployee() == null) {
            //Controls if the required fields are filled
            if (requiredFields()) {
                Optional<String> result = createDialog();
                if (result.isPresent()) {
                    //Displays an alert if the user did not pick any Role
                    if (result.get().equals("")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Vælg Rolle");
                        alert.setHeaderText(null);
                        alert.setContentText("Ingen rolle valgt.");
                        alert.showAndWait();
                        //Allows the user to try again by showing the first dialog again.
                    } else {
                        createNewUser(result.get());
                    }
                } else {
                    requiredFieldsLbl.setVisible(true);
                }
                //Closes the stageaadmin
                Stage stage = (Stage) saveBttn.getScene().getWindow();
                stage.close();
            } else {
                Person person = new Person(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText()) {
                };
                //Updates the new details of the Employee to the database
                new Thread(() -> {
                    employeeManager.updateEmployeeDetails(person, brugernavnTxtf.getText(), kodeordTxtf.getText());
                }).start();
                //Closes the stage
                Stage stage = (Stage) saveBttn.getScene().getWindow();
                stage.close();
            }
        }
    }

    public boolean requiredFields() {
        //Controls that no fields are empty
        if (brugernavnTxtf.getText().length() != 0 && kodeordTxtf.getText().length() != 0
            && fornavnTxtf.getText().length() != 0 && efternavnTxtf.getText().length() != 0
            && cprFxtf.getText().length() != 0 && tlkTxtf.getText().length() != 0) {
            return true;
        } else {
            return false;
        }
    }

    private void loadEmployeeDetails() {
        //Iterates through the Hashmap to find the username and password
        Pair<String, String> userNamePasswordPair = employeeManager.getEmployeeLogin(informationBridge.getChosenEmployee());
        brugernavnTxtf.setText(userNamePasswordPair.getKey());
        kodeordTxtf.setText(userNamePasswordPair.getValue());
        //Fill the information into the textfields
        Person editUser = informationBridge.getChosenEmployee();
        fornavnTxtf.setText(editUser.getFirstName());
        efternavnTxtf.setText(editUser.getLastName());
        cprFxtf.setText(editUser.getCprNr());
        tlkTxtf.setText(editUser.getPhoneNr());

    }

    public void createNewUser(String result) {
        //Find the chosen Role, and creates a new employee and adds it to the Database
        switch (result) {
            case "SAGSBEHANDLER":
                Employee sagsbehandler = new Sagsbehandler(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                addUserToDatabase(sagsbehandler);
                break;
            case "SOCIALRÅDGIVER":
                Employee socialraadgiver = new Socialrådgiver(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                addUserToDatabase(socialraadgiver);
                break;
            case "SOCIALPÆDAGOG":
                Employee socialpaedagog = new SocialPædagog(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                addUserToDatabase(socialpaedagog);
                break;
            case "ADMINISTRATOR":
                Employee administrator = new Administrator(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                addUserToDatabase(administrator);
                break;
            case "VIKAR":
                Employee vikar = new Vikar(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                addUserToDatabase(vikar);
                break;
        }
    }

    private void addUserToDatabase(Employee employee) {
        employeeManager.addEmployeeToObservable(employee);
        new Thread(() -> {
            employeeManager.addEmployeeToDB(employee, brugernavnTxtf.getText(), kodeordTxtf.getText());
        }).start();
    }

    private Optional<String> createDialog() {
        List<String> choices = new ArrayList<>();
        //Initializes the drop down menu.
        choices.add("SAGSBEHANDLER");
        choices.add("SOCIALRÅDGIVER");
        choices.add("SOCIALPÆDAGOG");
        choices.add("ADMINISTRATOR");
        choices.add("VIKAR");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Opret ny Beboer");
        dialog.setHeaderText("Vælg Rolle: ");
        dialog.setContentText("Vælg en rolle fra listen: ");

        Optional<String> result = dialog.showAndWait();
        return result;
    }
}
