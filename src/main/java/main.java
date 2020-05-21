import UI.ReportUI;
import javafx.application.Application;

import java.io.File;

public class main {
    public static void main(String[] args) {
        initDirectories();
        Application.launch(ReportUI.class, args);
    }

    private static void initDirectories() {
        String[] directories = {"Time", "Excel"};
        for (String directory : directories) {
            File file = new File(System.getProperty("user.dir") + "/" + directory);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }
}