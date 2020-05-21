package Time;

import java.io.*;

public class TimeTracker {
    public static void saveCurrentExecutionTime() {
        String resource = System.getProperty("user.dir") + "/Time/ExecutionTime.txt";
        File file = new File(resource);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.write(GenerateTime.getUnixTime() + "");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long readLastExecutionTime() {
        BufferedReader br;
        try {
            String location = System.getProperty("user.dir") + "/Time/ExecutionTime.txt";
            File file = new File(location);
            if (file.exists()) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String temp = br.readLine();
                if (!temp.isBlank()) {
                    return Long.parseLong(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // returns empty string if ExecutionTime.txt is empty or if is the programs first time running
        return 0;
    }
}
