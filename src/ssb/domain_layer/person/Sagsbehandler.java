package ssb.domain_layer.person;

public class Sagsbehandler extends Employee {
    final String employeeRole = "sagsbehandler"; 

    public Sagsbehandler(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
        setCanEditDoc(true);
        setCanPrintDoc(true);
        setCanAccessCreateDocBtn(true);
        setCanCreateNewProcessDoc(true);
    }

    @Override
    public String getEmployeeRole() {
        return employeeRole;
    }

}
