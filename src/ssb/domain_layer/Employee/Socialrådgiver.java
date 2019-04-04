package ssb.domain_layer.Employee;

import java.util.List;
import ssb.domain_layer.Resident;

public class Socialrådgiver extends Employee {

    public Socialrådgiver(List<Resident> residents, String firstName, String lastName, String phoneNr, String cprNr) {
        super(residents, firstName, lastName, phoneNr, cprNr);
        setCanCloseCase(true);
        setCanPrintDoc(true);
        setCanCreateReportDocs(true);
        setCanAccessCreateDocBtn(true);
        setCanEditDoc(true);
    }
    
}