package ssb.domain_layer.Employee;

public class SocialPædagog extends Employee {

    public SocialPædagog(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
        setCanCreateNotification(true);
        setCanSeeNotifications(true);
    }
}
