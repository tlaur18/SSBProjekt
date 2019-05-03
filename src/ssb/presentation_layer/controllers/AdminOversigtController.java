/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssb.presentation_layer.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ssb.domain_layer.Document;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.EmployeeManager;

/**
 * FXML Controller class
 *
 * @author morte
 */
public class AdminOversigtController implements Initializable {

    
    @FXML
    private Button saveBttn;
    @FXML
    private TableView<Employee> oversigtTbl;
    private EmployeeManager employeeManager = EmployeeManager.getInstance();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        employeeManager.loadAllEmployess();
         oversigtTbl.setItems(employeeManager.getAllEmployees());
         employeeManager.testMethod();
        // Sætter kolonner til at fylde 20% af bredden
        for (Object column : oversigtTbl.getColumns().toArray()) {
            TableColumn<Employee, ?> column1 = (TableColumn<Employee, ?>) column;
            column1.prefWidthProperty().bind(oversigtTbl.widthProperty().divide(5));
            
        }
    }    

    @FXML
    private void saveBttn(ActionEvent event) {
    }
    
}
