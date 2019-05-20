package ssb.presentation_layer.controllers.vum_document_controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ssb.domain_layer.document.SystemDocument;
import com.aspose.pdf.*;

public class SagsåbningController extends VumDocumentController implements Initializable {

    @FXML
    private Button saveButton;
    private Button cancelButton;
    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTabPaneChildren(tabPane);

        if (chosenDocument != null) {
            loadDocumentContent(chosenDocument);
        }
//        addTextListener(textArea1, 0);


    }

    private void cancelButtonOnAction(ActionEvent event) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    private void saveButtonOnAction(ActionEvent event) {
        saveInfo();
        if (chosenDocument != null) {
            saveExistingDocument();
        } else {
            saveNewDocument(SystemDocument.type.SAGSÅBNING);
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveBtnHandler(ActionEvent event) {
    }

    @FXML
    private void cancelBtnHandler(ActionEvent event) {
    }

//    private void addTextListener(TextArea textArea, double oldHeight) {
//        Text textHolder = new Text();
//        textHolder.textProperty().bind(textArea.textProperty());
//        textHolder.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
//            @Override
//            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
//                if (oldHeight != newValue.getHeight()) {
//                    System.out.println("newValue = " + newValue.getHeight());
//                    oldHeight = newValue.getHeight();
//                    textArea.setPrefHeight(textHolder.getLayoutBounds().getHeight() + 20); // +20 is for paddings
//                }
//            }
//        });
//    }
    
    
   private void pdfOutput(TextArea textArea){
           String dir = "D:/github/SSB/SSBProjekt/pdf_documents/";
           
           
           
           
           Document doc = new Document();
           
           Page pdf = (Page) doc.getPages().add();
           
           TextFragment textFragment = new TextFragment("test");
           
           textFragment.setPosition(new Position(40, 800));
           
           TextBuilder textBuilder = new TextBuilder(pdf);
           
           textBuilder.appendText(textFragment);
           
           doc.save(dir+"PDF_Test.pdf");
   }
}
