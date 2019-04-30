/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssb.domain_layer.Employee;

/**
 *
 * @author morte
 */
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
