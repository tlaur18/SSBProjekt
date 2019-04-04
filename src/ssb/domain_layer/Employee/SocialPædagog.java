package ssb.domain_layer.Employee;

import java.util.List;
import ssb.domain_layer.Resident;

public class SocialPædagog extends Employee {

    public SocialPædagog(List<Resident> residents, String firstName, String lastName, String phoneNr, String cprNr) {
        super(residents, firstName, lastName, phoneNr, cprNr);
        setCanCreateNotification(true);
        setCanSeeNotifications(true);
    }
}