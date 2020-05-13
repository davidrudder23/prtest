package Time;

import java.io.*;

public class TimeTracker {
    public static void saveCurrentExecutionTime() {
        File file = new File("src/main/resources/timetracking/ExecutionTime.txt");
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file, false);
            fileWriter.write(GenerateTime.getUnixTime() + "");
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long readLastExecutionTime() {
        // Extract to class var
        FileReader file;
        BufferedReader br;
        try {
            file = new FileReader("src/main/resources/timetracking/ExecutionTime.txt");
            br = new BufferedReader(file);
            String line;
            while ((line = br.readLine()) != null) {
                // There should only be 1 line
                return Long.valueOf(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // returns empty string if ExecutionTime.txt is empty
        return -1;
    }
}
