package ssb.domain_layer;

import java.util.ArrayList;
import java.util.List;

public class Resident extends Person {

    private final List<Document> documents = new ArrayList<>();

    public Resident(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void addDocument(Document document) {
        document.setResidentName(getFirstName());
        documents.add(document);
    }
}
