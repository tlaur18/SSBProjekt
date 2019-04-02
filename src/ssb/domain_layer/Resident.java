package ssb.domain_layer;

import java.util.ArrayList;
import java.util.List;

public class Resident extends Person {
    private final List<Case> cases = new ArrayList<>();

    public Resident(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
    }
}