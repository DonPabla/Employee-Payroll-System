package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
    private static final String logPath = "data/logs.txt";

    public static void log(String action) {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs(); // создаём папку, если её нет
            }

            FileWriter fw = new FileWriter(logPath, true);
            fw.write(LocalDateTime.now() + " - " + action + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to log file.");
        }
    }
}
