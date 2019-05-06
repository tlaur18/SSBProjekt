/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import ssb.domain_layer.Document;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.EmployeeManager;
import ssb.domain_layer.InformationBridge;
import ssb.domain_layer.Person;

/**
 * FXML Controller class
 *
 * @author morte
 */
public class AdminOversigtController implements Initializable {

    @FXML
    private TableView<Person> oversigtTbl;
    private EmployeeManager employeeManager = EmployeeManager.getInstance();
    @FXML
    private TableColumn<Employee, String> tableColumnFornavn;
    @FXML
    private TableColumn<Employee, String> tableColumnEftrnavn;
    @FXML
    private TableColumn<Employee, String> tableColumnCPR;
    @FXML
    private TableColumn<Employee, String> tableColumnTelefon;
    @FXML
    private TableColumn<Employee, String> tableColumnRolle;
    @FXML
    private Button newUserBttn;
    @FXML
    private Button EditUserBttn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        
        // Set the Items of the Observable list 
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
            
            URL url = new File("src/ssb/presentation_layer/fxml_documents/main_layout.fxml").toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = (Parent) loader.load();
            MainScreenController msc = new MainScreenController();
            loader.getController();
            msc.nybrugerOnAction(event);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void EditUserBttn(ActionEvent event) {
        InformationBridge.getInstance().putChosenEmployee(oversigtTbl.getSelectionModel().getSelectedItem());
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
    }
}
