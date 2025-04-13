package util;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import model.Employee;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class FileHandler {
    private final String filePath = "data/employees.json";  // исправленный путь
    private final Gson gson = new Gson();

    public List<Employee> loadEmployees() {
        try (Reader reader = new FileReader(filePath)) {
            Type empListType = new TypeToken<ArrayList<Employee>>() {}.getType();
            return gson.fromJson(reader, empListType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void saveEmployees(List<Employee> employees) {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();  // создаём папку, если её нет
            }

            try (Writer writer = new FileWriter(filePath)) {
                gson.toJson(employees, writer);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных.");
        }
    }

    public void exportToCSV(List<Employee> employees) {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();  // создаём папку, если её нет
            }

            try (PrintWriter writer = new PrintWriter("data/employees.csv")) {  // исправленный путь
                writer.println("ID,Name,Email,Salary");
                for (Employee e : employees) {
                    writer.printf("%d,%s,%s,%.2f%n", e.getId(), e.getName(), e.getEmail(), e.getSalary());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка экспорта в CSV.");
        }
    }
}
