package ssb.domain_layer.person;

public class SocialPædagog extends Employee {

    final String employeeRole = "socialpædagog";

    public SocialPædagog(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
        setCanCreateNotification(true);
        setCanSeeNotifications(true);
    }

    @Override
    public String getEmployeeRole() {
        return employeeRole;
    }

}
