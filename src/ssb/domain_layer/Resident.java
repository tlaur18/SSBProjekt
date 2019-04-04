package ssb.domain_layer;

import java.util.ArrayList;
import java.util.List;

public class Resident extends Person {
    private final List<Process> processes;

    public Resident(String firstName, String lastName, String phoneNr, String cprNr, ArrayList<Process> processes) {
        super(firstName, lastName, phoneNr, cprNr);
        this.processes = processes;
    }
}