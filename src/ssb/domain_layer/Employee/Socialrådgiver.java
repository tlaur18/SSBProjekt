package ssb.domain_layer.Employee;

public class Socialrådgiver extends Employee {
    
    final String employeeRole = "socialrådgiver";

    public Socialrådgiver(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
        setCanCloseCase(true);
        setCanPrintDoc(true);
        setCanCreateReportDocs(true);
        setCanAccessCreateDocBtn(true);
        setCanEditDoc(true);
    }

    @Override
    public String getEmployeeRole() {
        return employeeRole;
    }

}
