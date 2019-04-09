package ssb.domain_layer;

import java.util.Date;

public class Process {

    private final Date creationDate;
    private final Resident associatedResident;
    private String paragraph;
    private String payingMunicipality;

    public Process(Resident associatedResident) {
        creationDate = new Date();
        this.associatedResident = associatedResident;
    }

    public void endCase() {
        // TODO
    }
}
