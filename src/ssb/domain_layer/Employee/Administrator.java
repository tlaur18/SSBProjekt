
package ssb.domain_layer.Employee;

public class Administrator extends Employee {
    final String employeeRole = "admin"; 

    public Administrator(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
        setCanUseAdminRights(true);
    }

    @Override
    public String getEmployeeRole() {
        return employeeRole;
    }

}
