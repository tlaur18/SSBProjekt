package ssb.domain_layer.Employee;

public class Vikar extends Employee {

    public Vikar(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
        setCanCreateNotification(true);
        setCanSeeNotifications(true);
    }
}
