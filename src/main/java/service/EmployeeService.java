package service;

import model.Employee;
import util.FileHandler;
import java.util.*;

public class EmployeeService {
    private List<Employee> employees;
    private final FileHandler fileHandler;

    public EmployeeService(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.employees = Optional.ofNullable(fileHandler.loadEmployees()).orElse(new ArrayList<>()); // Защищаем от null
    }

    public void addEmployee(Employee emp) {
        if (emp != null) {
            employees.add(emp);
            fileHandler.saveEmployees(employees);
        } else {
            System.out.println("Cannot add null employee");
        }
    }

    public Employee findById(int id) {
        return employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public List<Employee> getAll() {
        return employees;
    }

    public void updateEmployee(int id, Employee newEmp) {
        Employee emp = findById(id);
        if (emp != null) {
            emp.setName(newEmp.getName());
            emp.setEmail(newEmp.getEmail());
            emp.setSalary(newEmp.getSalary());
            fileHandler.saveEmployees(employees);
        }
    }

    public void deleteEmployee(int id) {
        employees.removeIf(e -> e.getId() == id);
        fileHandler.saveEmployees(employees);
    }
}
