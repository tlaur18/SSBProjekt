package ssb.domain_layer.Employee;

import java.util.List;
import ssb.domain_layer.Resident;

public class Sagsbehandler extends Employee {
    
    public Sagsbehandler(List<Resident> residents, String firstName, String lastName, String phoneNr, String cprNr) {
        super(residents, firstName, lastName, phoneNr, cprNr);
        setCanEditDoc(true);
        setCanPrintDoc(true);
        setCanAccessCreateDocBtn(true);
        setCanCreateNewProcessDoc(true);
    }
    
    
    
}