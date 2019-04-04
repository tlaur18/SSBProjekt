package ssb.domain_layer.Employee;

import java.util.List;
import ssb.domain_layer.Resident;

public class Vikar extends Employee {

    public Vikar(List<Resident> residents) {
        super(null);
        setCanCreateNotification(true);
        setCanSeeNotifications(true);
    }
}