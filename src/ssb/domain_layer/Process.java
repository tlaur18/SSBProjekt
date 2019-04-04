package ssb.domain_layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Process {
    private final Date creationDate;
    private final List<Document> documents;

    public Process(ArrayList<Document> documents) {
        creationDate = new Date();
        this.documents = documents;
    }
    
    public void endCase() {
        
    }
}