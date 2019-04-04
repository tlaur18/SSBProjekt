package ssb.domain_layer.Employee;

import java.util.List;
import ssb.domain_layer.Resident;

public class Sagsbehandler extends Employee {
    
    public Sagsbehandler(List<Resident> residents) {
        super(residents);
        setCanEditDoc(true);
        setCanPrintDoc(true);
        setCanAccessCreateDocBtn(true);
        setCanCreateOpencaseDoc(true);
    }
    
    
    
}