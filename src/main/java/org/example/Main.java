package org.example;

import menu.Menu;
import service.EmployeeService;
import util.FileHandler;

public class Main {
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        EmployeeService service = new EmployeeService(fileHandler);
        Menu menu = new Menu(service);
        menu.start();
    }
}
