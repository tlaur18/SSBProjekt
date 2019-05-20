package ssb.domain_layer.person;

import ssb.domain_layer.person.Person;
import ssb.domain_layer.document.SystemDocument;
import java.util.ArrayList;
import java.util.List;

public class Resident extends Person {

    private String streetName;
    private String cityName;
    private String postCode;
    private final List<SystemDocument> documents;

    public Resident(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
        documents = new ArrayList<>();
    }

    public List<SystemDocument> getDocuments() {
        return documents;
    }

    public void addDocument(SystemDocument document) {
        document.setResidentName(getFirstName());
        documents.add(document);
    }
    
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
