package ssb.presentation_layer.controllers;

import java.awt.Checkbox;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import ssb.domain_layer.Document;

/**
 * FXML Controller class
 *
 * @author olive
 */
public class Test_viewController implements Initializable {

    @FXML
    private CheckBox CheckBox1;
    @FXML
    private CheckBox CheckBox2;
    @FXML
    private CheckBox CheckBox3;
    @FXML
    private CheckBox CheckBox4;
    @FXML
    private CheckBox CheckBox5;
    @FXML
    private CheckBox CheckBox6;
    @FXML
    private CheckBox CheckBox7;
    @FXML
    private CheckBox CheckBox8;
    @FXML
    private CheckBox CheckBox9;
    @FXML
    private CheckBox CheckBox10;
    @FXML
    private CheckBox CheckBox11;
    @FXML
    private CheckBox CheckBox12;
    @FXML
    private CheckBox CheckBox13;
    @FXML
    private CheckBox CheckBox14;
    @FXML
    private CheckBox CheckBox15;
    @FXML
    private CheckBox CheckBox16;
    @FXML
    private CheckBox CheckBox17;
    @FXML
    private CheckBox CheckBox18;
    @FXML
    private Button saveBttn;
    @FXML
    private Button cancelBttn;

    private FXMLDocumentController fxmlDocumentController;
    private List<CheckBox> checkbox;
    private List<CheckBox> selectedCheckbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        checkbox = new ArrayList<CheckBox>();
        selectedCheckbox = new ArrayList<CheckBox>();
        loadCheckbox();
    }
    
    public void init(FXMLDocumentController controller) {
        this.fxmlDocumentController = controller;
    }

    private void loadCheckbox() {
        checkbox.add(CheckBox1);
        checkbox.add(CheckBox2);
        checkbox.add(CheckBox3);
        checkbox.add(CheckBox4);
        checkbox.add(CheckBox5);
        checkbox.add(CheckBox6);
        checkbox.add(CheckBox7);
        checkbox.add(CheckBox8);
        checkbox.add(CheckBox9);
        checkbox.add(CheckBox10);
        checkbox.add(CheckBox11);
        checkbox.add(CheckBox12);
        checkbox.add(CheckBox13);
        checkbox.add(CheckBox14);
        checkbox.add(CheckBox15);
        checkbox.add(CheckBox16);
        checkbox.add(CheckBox17);
        checkbox.add(CheckBox18);
    }

    private void sortCheckBox() {
        for (CheckBox checkbox : checkbox) {
            if (checkbox.isSelected()) {
                selectedCheckbox.add(checkbox);
                System.out.println("I have added this item to the selected list" + checkbox);
            } else {
                System.out.println("this item is not added: " + checkbox);
            }
        }
    }

    @FXML
    private void saveBttn(ActionEvent event) throws IOException{
        sortCheckBox();
        Document doc = new Document(Document.type.SAGSÅBNING, selectedCheckbox);
        URL url = new File("src/ssb/presentation_layer/fxml_documents/main_layout.fxml").toURL();
        System.out.println(doc.toString());
        FXMLLoader loader = new FXMLLoader(url);
        fxmlDocumentController = loader.getController();
        System.out.println("u got to 1st base");
        
    }

    @FXML
    private void cancelBttn(ActionEvent event) {
        try {
            Stage stage = new Stage();
            URL url = new File("src/ssb/presentation_layer/fxml_documents/main_layout.fxml").toURL();
            stage.setScene(new Scene(FXMLLoader.load(url)));
            stage.initOwner(saveBttn.getScene().getWindow());
            stage.setTitle("Morten er awesome!");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
