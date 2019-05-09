package ssb.domain_layer.person;

public class Vikar extends Employee {
    
    final String employeeRole = "vikar";

    public Vikar(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
        setCanCreateNotification(true);
        setCanSeeNotifications(true);
    }

    @Override
    public String getEmployeeRole() {
        return employeeRole;
    }
}
