/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssb.presentation_layer.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author morte
 */
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
    private CheckBox HenvendelseCheckJa2;
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
    private CheckBox RepresentationBisidderCheck;
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

    private HashMap<CheckBox, String> selectedBoxesHashMap = new HashMap<CheckBox, String>();
    private HashMap<TextArea, String> textAreaInfoHashMap = new HashMap<TextArea, String>();
    private ArrayList<CheckBox> checkboxesArrayList = new ArrayList<CheckBox>();
    private ArrayList<TextArea> textAreas = new ArrayList<TextArea>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCheckbox(parentBorderPane);;
    }

    private void loadCheckbox(Pane pane) {
        for (Node child : pane.getChildren()) {
            System.out.println("testing Pane");
            if (child instanceof CheckBox) {
                checkboxesArrayList.add((CheckBox) child);
                System.out.println("Dette er en test af array:");

            }
            if (child instanceof TextArea) {
                textAreas.add((TextArea) child);
                System.out.println("Dette er en test af array:");
            }

            if (child instanceof TabPane){
                System.out.println("3");
                for(Node child1 : ((TabPane) child).getChildrenUnmodifiable()){
                    if(child1 instanceof CheckBox) {
                        System.out.println("hejsa");
                    }
                }
            }
        }

    }

}
