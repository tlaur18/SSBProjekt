package ssb.domain_layer.Employee;

public class Sagsbehandler extends Employee {

    public Sagsbehandler(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
        setCanEditDoc(true);
        setCanPrintDoc(true);
        setCanAccessCreateDocBtn(true);
        setCanCreateNewProcessDoc(true);
    }

}
