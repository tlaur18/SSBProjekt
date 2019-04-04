package ssb.domain_layer.Employee;

import java.util.List;
import ssb.domain_layer.Resident;

public class Socialrådgiver extends Employee {

    public Socialrådgiver(List<Resident> residents) {
        super(residents);
        setCanCloseCase(true);
        setCanPrintDoc(true);
        setCanCreateReportDocs(true);
        setCanAccessCreateDocBtn(true);
        setCanEditDoc(true);
    }
    
}