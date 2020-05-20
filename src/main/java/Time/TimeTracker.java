package Time;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class TimeTracker {
    public static void saveCurrentExecutionTime() throws URISyntaxException {
        URL resource = TimeTracker.class.getClassLoader().getResource("timetracking/ExecutionTime.txt");
        File file = new File(resource.toURI());
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file, false);
            fileWriter.write(GenerateTime.getUnixTime() + "");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long readLastExecutionTime() {
        FileReader file;
        BufferedReader br;
        try {
            URL resource = TimeTracker.class.getClassLoader().getResource("timetracking/ExecutionTime.txt");
            file = new FileReader(new File(resource.toURI()));
            br = new BufferedReader(file);
            String line;
            while ((line = br.readLine()) != null) {
                // There should only be 1 line
                return Long.valueOf(line);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        // returns empty string if ExecutionTime.txt is empty
        return -1;
    }
}
