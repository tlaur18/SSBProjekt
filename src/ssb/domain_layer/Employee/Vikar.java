package ssb.domain_layer.Employee;

import java.util.List;
import ssb.domain_layer.Resident;

public class Vikar extends Employee {

    public Vikar(List<Resident> residents, String firstName, String lastName, String phoneNr, String cprNr) {
        super(null, firstName, lastName, phoneNr, cprNr);
        setCanCreateNotification(true);
        setCanSeeNotifications(true);
    }
}