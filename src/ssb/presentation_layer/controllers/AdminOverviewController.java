
package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.EmployeeManager;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.Person;

public class AdminOverviewController implements Initializable {

    @FXML
    private TableView<Person> oversigtTbl;
    private final EmployeeManager employeeManager = EmployeeManager.getInstance();
    @FXML
    private TableColumn<Employee, String> tableColumnRolle;
    @FXML
    private Label chooseAUserError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Set the Items of the Observable list 
        oversigtTbl.sort();
        oversigtTbl.setItems(employeeManager.getAllEmployees());
        
        //Loads the Role form the Employee, and places it in the column
        loadTableView();
        // Sets the width om the colums to 20 %
        for (Object column : oversigtTbl.getColumns().toArray()) {
            TableColumn<Employee, ?> column1 = (TableColumn<Employee, ?>) column;
            column1.prefWidthProperty().bind(oversigtTbl.widthProperty().divide(5));
        }

    }

    private void loadTableView() {
        
        tableColumnRolle.setCellValueFactory(new PropertyValueFactory<>("Rolle"));

        //Manually adds the Role of the Employee to the Tableview
        tableColumnRolle.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Employee, String> p) {
                // p.getValue() returns the PersonType instance for a particular TableView row
                if (p.getValue() != null && p.getValue().getEmployeeRole() != null) {
                    return new SimpleStringProperty(p.getValue().getEmployeeRole());
                } else {
                    return new SimpleStringProperty("<No TC Rolle>");
                }
            }
        });
    }

    @FXML
    private void newUserBttn(ActionEvent event) {

        try {
            InformationBridge.getInstance().putChosenEmployee(null);
            URL urlEmployeeEditor = new File("src/ssb/presentation_layer/fxml_documents/adminNyBruger.fxml").toURL();
            FXMLLoader loaderEmployeeEditor = new FXMLLoader(urlEmployeeEditor);
            Parent rootEmployee = (Parent) loaderEmployeeEditor.load();
            Stage stageEmployeeEditor = new Stage();
            stageEmployeeEditor.setMinHeight(425);
            stageEmployeeEditor.setMinWidth(650);
            stageEmployeeEditor.setScene(new Scene(FXMLLoader.load(urlEmployeeEditor)));
            stageEmployeeEditor.setTitle("Ny Bruger");
            stageEmployeeEditor.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void EditUserBttn(ActionEvent event) {
        InformationBridge.getInstance().putChosenEmployee(oversigtTbl.getSelectionModel().getSelectedItem());
        if (InformationBridge.getInstance().getChosenEmployee() != null) {
            try {
                URL urlEmployeeEditor = new File("src/ssb/presentation_layer/fxml_documents/adminNyBruger.fxml").toURL();
                FXMLLoader loaderEmployeeEditor = new FXMLLoader(urlEmployeeEditor);
                Parent rootEmployee = (Parent) loaderEmployeeEditor.load();
                Stage stageEmployeeEditor = new Stage();
                stageEmployeeEditor.setMinHeight(425);
                stageEmployeeEditor.setMinWidth(650);
                stageEmployeeEditor.setScene(new Scene(FXMLLoader.load(urlEmployeeEditor)));
                stageEmployeeEditor.setTitle("Employee Editor");
                stageEmployeeEditor.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            chooseAUserError.setVisible(true);
        }
    }

    @FXML
    private void deleteUserBttn(ActionEvent event) {
        InformationBridge.getInstance().putChosenEmployee(oversigtTbl.getSelectionModel().getSelectedItem());
        Person person = InformationBridge.getInstance().getChosenEmployee();
        if (InformationBridge.getInstance().getChosenEmployee() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Slet bruger");
            alert.setHeaderText(null);
            alert.setContentText("Ønsker du at slette " + person.getFirstName());

            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.OK) {
                    //Deletes the user from the Observable List and Database
                    employeeManager.deleteEmployeeFromObservable(person);
                    employeeManager.deleteEmployee(person.getCprNr());
                } else if (type == ButtonType.CANCEL) {
                    System.out.println("no is chosen");
                } else {
                }
            });
        } else {
            chooseAUserError.setVisible(true);
        }
    }
}
