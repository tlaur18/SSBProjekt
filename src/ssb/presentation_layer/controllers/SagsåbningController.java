package ssb.presentation_layer.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ssb.domain_layer.Document;
import ssb.domain_layer.DocumentManager;
import ssb.domain_layer.Resident;
import ssb.presentation_layer.fxml_documents.InformationBridge;

public class SagsåbningController implements Initializable {

    @FXML
    private BorderPane parentBorderPane;
    @FXML
    private TextArea henvendelseTXTa1;
    @FXML
    private CheckBox henvendelseCheckNej1;
    @FXML
    private CheckBox henvendelseJaCheck1;
    @FXML
    private CheckBox dropdownborgerCheck;
    @FXML
    private CheckBox dropdownpaaRoerendeCheck;
    @FXML
    private CheckBox dropdownLaegeCheck;
    @FXML
    private CheckBox dropdownHospitalCheck;
    @FXML
    private CheckBox dropdownAndenCheck;
    @FXML
    private CheckBox dropdownIndsatsCheck;
    @FXML
    private CheckBox dropdownAndenKommmuneCheck;
    @FXML
    private CheckBox dropdownAndreCheck;
    @FXML
    private CheckBox henvendelseCheckJa2;
    @FXML
    private CheckBox henvendelseCheckNej2;
    @FXML
    private CheckBox vaergemaalParag5Check;
    @FXML
    private CheckBox vaergemaalParag6check;
    @FXML
    private CheckBox vaergemaalParag7Check;
    @FXML
    private CheckBox vaergemaalKontaktCheck;
    @FXML
    private CheckBox representationBisidderCheck;
    @FXML
    private CheckBox partsRepresentantCheck;
    @FXML
    private CheckBox representationFuldmagtCheck;
    @FXML
    private TextArea representationTxtA;
    @FXML
    private CheckBox rettighederCheck;
    @FXML
    private CheckBox forlobJaCheck;
    @FXML
    private CheckBox forlobNejCheck;
    @FXML
    private TextArea forlobAftalerTxtA;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private DocumentManager documentManager;
    private Resident chosenResident;
    private HashMap<CheckBox, Boolean> selectedBoxesHashMap;
    private HashMap<TextArea, String> textAreaInfoHashMap;
    private ArrayList<CheckBox> checkboxesArrayList;
    private ArrayList<TextArea> textAreas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        documentManager = InformationBridge.getINSTANCE().getDocumentManager();
        chosenResident = InformationBridge.getINSTANCE().getChosenResident();
        selectedBoxesHashMap = new HashMap<>();
        textAreaInfoHashMap = new HashMap<>();
        checkboxesArrayList = new ArrayList<>();
        textAreas = new ArrayList<>();
        loadCheckbox(parentBorderPane);

        if (InformationBridge.getINSTANCE().getChosenDocument() != null) {
            loadDocumentContent(InformationBridge.getINSTANCE().getChosenDocument());
        }
    }

    private void loadCheckbox(Pane pane) {
        //Adding all checkboxes to the checkboxArray, to be used in later iteration.
        checkboxesArrayList.add(henvendelseCheckNej1);
        checkboxesArrayList.add(henvendelseJaCheck1);
        checkboxesArrayList.add(dropdownborgerCheck);
        checkboxesArrayList.add(dropdownpaaRoerendeCheck);
        checkboxesArrayList.add(dropdownLaegeCheck);
        checkboxesArrayList.add(dropdownHospitalCheck);
        checkboxesArrayList.add(dropdownAndenCheck);
        checkboxesArrayList.add(dropdownIndsatsCheck);
        checkboxesArrayList.add(dropdownAndenKommmuneCheck);
        checkboxesArrayList.add(dropdownAndreCheck);
        checkboxesArrayList.add(henvendelseCheckJa2);
        checkboxesArrayList.add(henvendelseCheckNej2);
        checkboxesArrayList.add(vaergemaalParag5Check);
        checkboxesArrayList.add(vaergemaalParag6check);
        checkboxesArrayList.add(vaergemaalParag7Check);
        checkboxesArrayList.add(vaergemaalKontaktCheck);
        checkboxesArrayList.add(representationBisidderCheck);
        checkboxesArrayList.add(partsRepresentantCheck);
        checkboxesArrayList.add(representationFuldmagtCheck);
        checkboxesArrayList.add(rettighederCheck);
        checkboxesArrayList.add(forlobJaCheck);
        checkboxesArrayList.add(forlobNejCheck);

        //Adding all TextAreas to Array, to be used in later iteration.
        textAreas.add(henvendelseTXTa1);
        textAreas.add(representationTxtA);
        textAreas.add(forlobAftalerTxtA);
    }

    private void sortCheckBox() {
        for (CheckBox checkbox : checkboxesArrayList) {
            selectedBoxesHashMap.put(checkbox, checkbox.isSelected());
        }
    }

    private void sortTeaxArea() {
        for (TextArea textArea : textAreas) {
            textAreaInfoHashMap.put(textArea, textArea.getText());
        }
    }

    @FXML
    private void saveButtonOnAction(ActionEvent event) throws MalformedURLException, IOException {
        sortCheckBox();
        sortTeaxArea();

        Document doc = new Document(Document.type.SAGSÅBNING, selectedBoxesHashMap, textAreaInfoHashMap);
        documentManager.addDocument(doc, chosenResident);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void loadDocumentContent(Document doc) {
        selectedBoxesHashMap = doc.getSelectedCheckboxes();
        textAreaInfoHashMap = doc.getTextAreas();

        for (CheckBox checkBoxList : checkboxesArrayList) {
            for (Map.Entry<CheckBox, Boolean> set : selectedBoxesHashMap.entrySet()) {
                if (set.getValue() == true) {
                    if (checkBoxList.getId().equals(set.getKey().getId())) {
                        checkBoxList.setSelected(true);
                    }
                }
            }
        }
        for (TextArea textArea : textAreas) {
            for (Map.Entry<TextArea, String> set : textAreaInfoHashMap.entrySet()) {
                if (textArea.getId().equals(set.getKey().getId())) {
                    textArea.setText(set.getValue());
                }
            }
        }
    }
}
