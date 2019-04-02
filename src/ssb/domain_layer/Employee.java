package ssb.domain_layer;

import java.util.List;

public class Employee {
    public enum job {
        SAGSBEHANDLER, SOCIALRÅDGIVER, SOCIALPÆDAGOG, VIKAR
    }
    
    private final job jobType;
    private final List<Resident> residents;
    
    public Employee(job jobType, List<Resident> residents) {
        this.jobType = jobType;
        this.residents = residents;
    }

    public job getJobType() {
        return jobType;
    }

    public List<Resident> getResidents() {
        return residents;
    }
}
