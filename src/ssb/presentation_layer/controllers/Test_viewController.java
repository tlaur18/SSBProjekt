package ssb.presentation_layer.controllers;

import java.awt.Checkbox;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import ssb.presentation_layer.fxml_documents.InformationBridge;

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

    private HashMap<CheckBox, Boolean> selectedBox = new HashMap<CheckBox, Boolean>();
    private FXMLDocumentController fxmlDocumentController;
    private ArrayList<CheckBox> checkbox = new ArrayList<CheckBox>();
    private List<CheckBox> selectedCheckbox = new ArrayList<CheckBox>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCheckbox();
        if (InformationBridge.getINSTANCE().getChosenDocument() != null) {
            System.out.println("Loading Documents");
            loadDocumentContent(InformationBridge.getINSTANCE().getChosenDocument());

        }
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
                selectedBox.put(checkbox, true);
                System.out.println(selectedBox.keySet());
                System.out.println("I have added this item to the selected list" + checkbox);
            } else {
                System.out.println("this item is not added: " + checkbox);
            }
        }
    }

    @FXML
    private void saveBttn(ActionEvent event) throws IOException {
        sortCheckBox();
        Document doc = new Document(Document.type.SAGSÅBNING, selectedBox);
        URL url = new File("src/ssb/presentation_layer/fxml_documents/main_layout.fxml").toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent) loader.load();
        fxmlDocumentController = loader.getController();
        fxmlDocumentController.saveDocument(doc);
    }

    @FXML
    private void cancelBttn(ActionEvent event) {
        Stage stage = (Stage) cancelBttn.getScene().getWindow();
        stage.close();
    }

    public void loadDocumentContent(Document doc) {
        selectedBox = doc.getSelectedCheckbox();

        for (CheckBox checkBoxList : checkbox) {
            System.out.println(checkBoxList.getId());
            for (Map.Entry<CheckBox, Boolean> set : selectedBox.entrySet()) {
                if (set.getValue() == true) {
//                    System.out.println(set.getKey().getId());
//                    System.out.println(set.getKey().equals(checkBoxList));
                    if (checkBoxList.getId().equals(set.getKey().getId())) {
                    checkBoxList.setSelected(true);
                    }
                }
            }
        }
    }
}
