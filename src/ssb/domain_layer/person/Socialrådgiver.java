package ssb.domain_layer.person;

public class Socialrådgiver extends Employee {
    
    final String employeeRole = "socialrådgiver";

    public Socialrådgiver(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
        setCanCloseCase(true);
        setCanPrintDoc(true);
        setCanCreateReportDocs(true);
        setCanEditDoc(true);
    }

    @Override
    public String getEmployeeRole() {
        return employeeRole;
    }

}
