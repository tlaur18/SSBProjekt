package ssb.domain_layer;

import java.util.ArrayList;
import java.util.List;

public class Department {

    public enum specializations {
        ÆLDREHJEM, ALKOHOLMISBRUG, STOFMISBRUG, HJERNESKADEDE;
    }

    private String departmentName;
    private specializations specialization;
    private List<Employee> employees;
    private List<Resident> residents;
    private Address address;

    public Department(String departmentName, specializations specialization, Address address) {
        this.departmentName = departmentName;
        this.specialization = specialization;
        this.employees = new ArrayList();
        this.residents = new ArrayList();
        this.address = address;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public specializations getSpecialization() {
        return specialization;
    }

    public void setSpecialization(specializations specialization) {
        this.specialization = specialization;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void addResident(Resident resident) {
        residents.add(resident);
    }
    
    //HER MANGLER DER LIDT TILFØJELSES- OG FJERNELSES-METODER.
}
