package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ssb.domain_layer.Document;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.Employee.Employee;
import ssb.domain_layer.Resident;
import ssb.domain_layer.InformationBridge;

public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView homeBtn;
    @FXML
    private TableView<Document> vumDocumentTableView;
    @FXML
    private BorderPane borderPane;

    private final DocumentManager documentManager = DocumentManager.getInstance();
    private InformationBridge informationBridge;
    private Employee loggedInEmployee;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    private void sagerOnAction(ActionEvent event) throws MalformedURLException {
        loadFXML("sagerTab");
    }

    @FXML
    private void HandleplanOnAction(ActionEvent event) {
    }
    
    @FXML
    public void logOutHandler(MouseEvent event) {
        InformationBridge.getInstance().resetSystem();
        try {
            URL url = new File("src/ssb/presentation_layer/fxml_documents/login_layout.fxml").toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = (Parent) loader.load();
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            ((Stage) homeBtn.getScene().getWindow()).close(); //close login stage
            loginStage.show();
        } catch (IOException e) {
            System.out.println("Log out handler: " + e.getMessage());
        }
    }
}
