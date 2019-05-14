package ssb.presentation_layer.controllers.admin_controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import ssb.domain_layer.Home;
import ssb.domain_layer.person.Administrator;
import ssb.domain_layer.person.Employee;
import ssb.domain_layer.person.Sagsbehandler;
import ssb.domain_layer.person.SocialPædagog;
import ssb.domain_layer.person.Socialrådgiver;
import ssb.domain_layer.person.Vikar;
import ssb.domain_layer.person.EmployeeManager;
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
    @FXML
    private ChoiceBox<Home> homeDialog;
    @FXML
    private ListView<Home> homeList;
    @FXML
    private Button addHomebttn;
    @FXML
    private Button removeHomeBttn;
    private final InformationBridge informationBridge = InformationBridge.getInstance();
    private final EmployeeManager employeeManager = EmployeeManager.getInstance();
    private final ObservableList<Home> employeeHomes = FXCollections.observableArrayList();
    private final ArrayList<Home> deletedHomes = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setHomeDialog();

        homeList.setItems(employeeHomes);
        if (informationBridge.getChosenEmployee() != null) {
            // TODO - thread instead and then a progress indicator
            new Thread(() -> {
                loadEmployeeDetails();
                loadHomes();
            }).start();
            cprFxtf.setDisable(true);

        }
    }

    @FXML
    private void saveBttn(ActionEvent event) {
        // Controls if there is a chosen employee
        if (informationBridge.getChosenEmployee() == null) {
            //Controls if the required fields are filled
            System.out.println(homeDialog.getSelectionModel().getSelectedItem());
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
                }
                //Closes the stageaadmin
                Stage stage = (Stage) saveBttn.getScene().getWindow();
                stage.close();
            } else {
                requiredFieldsLbl.setVisible(true);
            }
        } else {
            // Updates the new details of the Employee to the database
            new Thread(() -> {
                 employeeManager.updateEmployeeDetails(informationBridge.getChosenEmployee(), brugernavnTxtf.getText(), kodeordTxtf.getText(), employeeHomes);
                 employeeManager.deletePersonHomeLink(informationBridge.getChosenEmployee(), deletedHomes);
            }).start();
            //Closes the stage
            Stage stage = (Stage) saveBttn.getScene().getWindow();
            stage.close();
        }
    }

    public boolean requiredFields() {
        //Controls that no fields are empty
        if (brugernavnTxtf.getText().length() != 0 && kodeordTxtf.getText().length() != 0
                && fornavnTxtf.getText().length() != 0 && efternavnTxtf.getText().length() != 0
                && cprFxtf.getText().length() != 0 && tlkTxtf.getText().length() != 0
                && homeDialog.getSelectionModel().getSelectedItem() != null) {
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

    public Employee createNewUser(String result) {
        Employee newEmployee = null;
        //Find the chosen Role, and creates a new employee and adds it to the Database
        switch (result) {
            case "SAGSBEHANDLER":
                Employee sagsbehandler = new Sagsbehandler(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                addUserToDatabase(sagsbehandler);
                newEmployee = sagsbehandler;
                break;
            case "SOCIALRÅDGIVER":
                Employee socialraadgiver = new Socialrådgiver(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                addUserToDatabase(socialraadgiver);
                newEmployee = socialraadgiver;
                break;
            case "SOCIALPÆDAGOG":
                Employee socialpaedagog = new SocialPædagog(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                addUserToDatabase(socialpaedagog);
                newEmployee = socialpaedagog;
                break;
            case "ADMINISTRATOR":
                Employee administrator = new Administrator(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                addUserToDatabase(administrator);
                newEmployee = administrator;
                break;
            case "VIKAR":
                Employee vikar = new Vikar(fornavnTxtf.getText(), efternavnTxtf.getText(), tlkTxtf.getText(), cprFxtf.getText());
                addUserToDatabase(vikar);
                newEmployee = vikar;
                break;
        }
        return newEmployee;
    }

    private void addUserToDatabase(Employee employee) {
        employeeManager.addEmployeeToObservable(employee);
        new Thread(() -> {
            if (employeeHomes.size() == 1) {
                employeeManager.addEmployeeToDB(employee, brugernavnTxtf.getText(), kodeordTxtf.getText(), employeeHomes);
            }
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

    public void setHomeDialog() {
        //Adds the homes to the dialog
        for (Home home : employeeManager.getAllHomes()) {
            homeDialog.getItems().add(home);
        }
    }

    public int getHomeID(String homeName) {
        //switch case to find the selected home
        int homeID = 0;
        switch (homeName) {
            case "Vammelby":
                homeID = 1;
                break;
            case "Dejligby":
                homeID = 2;
                break;
            default:
                homeID = 1;
        }
        return homeID;
    }

    @FXML
    private void addHomeBttn(ActionEvent event) {
        employeeHomes.add(homeDialog.getSelectionModel().getSelectedItem());

    }

    private void loadHomes() {
        for (Home home : employeeManager.getAllEmployeeHomes(informationBridge.getChosenEmployee().getCprNr())) {
            employeeHomes.add(home);
        }
    }

    @FXML
    private void removeHomeBttn(ActionEvent event) {
        deletedHomes.add(homeList.getSelectionModel().getSelectedItem());
        employeeHomes.remove(homeList.getSelectionModel().getSelectedItem());
    }
}
