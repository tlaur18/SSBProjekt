/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssb.domain_layer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ssb.domain_layer.Employee.Employee;

/**
 *
 * @author morte
 */
public class DocumentManager {
    private final ObservableList<Document> allDocuments = FXCollections.observableArrayList();

    public DocumentManager() {
    }
    
    public ObservableList<Document> getAllDocuments(){
        return this.allDocuments;
    }
    
    public void loadAllDocuments(Employee employee) {
      for(Document doc : employee.getResidentDocuments()) {
          allDocuments.add(doc);
      }
    }
    public void addDocument(Document document) {
        allDocuments.add(document);
    }
}
