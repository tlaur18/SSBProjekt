package ssb.domain_layer;

import java.util.ArrayList;
import java.util.List;
import ssb.domain_layer.Employee.Employee;

public class Home {

    private String homeName;
    private List<Employee> employees;
    private List<Resident> residents;
    private int id;

    public Home(String departmentName, int id) {
        this.homeName = departmentName;
        this.id = id;
        this.employees = new ArrayList();
        this.residents = new ArrayList();
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addResident(Resident resident) {
        residents.add(resident);
    }

    public void removeResident(String residentID) {
        List<Resident> toRemove = new ArrayList();

        for (Resident res : residents) {
            if (residentID.equals(res.getID())) {
                toRemove.add(res);
            }
        }

        residents.removeAll(toRemove);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(String employeeID) {
        List<Employee> toRemove = new ArrayList();

        for (Employee emp : employees) {
            if (employeeID.equals(emp.getID())) {
                toRemove.add(emp);
            }
        }

        employees.removeAll(toRemove);
    }
}
