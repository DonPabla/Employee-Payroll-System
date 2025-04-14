package menu;

import model.Employee;
import service.EmployeeService;
import util.InputValidator;
import util.Logger;
import util.FileHandler;

import java.util.Scanner;

public class Menu {
    private final EmployeeService employeeService;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Employee Payroll System ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Export to CSV");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewAll();
                case 3 -> updateEmployee();
                case 4 -> deleteEmployee();
                case 5 -> exportCSV();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option! Please choose between 1 and 6.");
            }
        }
    }

    private void addEmployee() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        if (InputValidator.isEmpty(name)) return;

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        if (!InputValidator.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        System.out.print("Enter salary: ");
        double salary;
        try {
            salary = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary input. Please enter a valid number.");
            return;
        }

        int id = (int)(Math.random() * 10000); // Просто для примера
        Employee e = new Employee(id, name, email, salary);
        employeeService.addEmployee(e);
        Logger.log("Added employee: " + name);
    }

    private void viewAll() {
        var employees = employeeService.getAll();
        if (employees.isEmpty()) {
            System.out.println("No employees available.");
        } else {
            for (Employee e : employees) {
                System.out.printf("ID: %d | Name: %s | Email: %s | Salary: %.2f%n",
                        e.getId(), e.getName(), e.getEmail(), e.getSalary());
            }
        }
    }

    private void updateEmployee() {
        System.out.print("Enter ID to update: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a valid number.");
            return;
        }

        Employee old = employeeService.findById(id);
        if (old == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        if (InputValidator.isEmpty(name)) return;

        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        if (!InputValidator.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        System.out.print("Enter new salary: ");
        double salary;
        try {
            salary = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary input. Please enter a valid number.");
            return;
        }

        employeeService.updateEmployee(id, new Employee(id, name, email, salary));
        Logger.log("Updated employee ID: " + id);
    }

    private void deleteEmployee() {
        System.out.print("Enter ID to delete: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a valid number.");
            return;
        }

        employeeService.deleteEmployee(id);
        Logger.log("Deleted employee ID: " + id);
    }

    private void exportCSV() {
        var employees = employeeService.getAll();
        if (employees.isEmpty()) {
            System.out.println("No employees to export.");
        } else {
            new FileHandler().exportToCSV(employees);
            Logger.log("Exported to CSV");
        }
    }
}
