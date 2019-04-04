package ssb.domain_layer.Employee;

import java.util.List;
import ssb.domain_layer.Resident;

public class SocialPædagog extends Employee {

    public SocialPædagog(List<Resident> residents) {
        super(residents);
        setCanCreateNotification(true);
        setCanSeeNotifications(true);
    }
}