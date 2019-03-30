package ssb.presentation_layer.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author oliver
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button sagerBtn;
    @FXML
    private Button navnBtn;
    
    @FXML
    private void sagerOnAction(ActionEvent event) {
        System.out.println("Sager pressed");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
