package Time;

import java.io.*;

public class TimeTracker {
    public static void saveCurrentExecutionTime(){
        File file = new File("ExecutionTime.txt");
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file, false);
            fileWriter.write(GenerateTime.getUnixTime()+"");
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readLastExecutionTime(){
        // Extract to class var
        FileReader file;
        BufferedReader br;
        try {
            file = new FileReader("ExecutionTime.txt");
            br = new BufferedReader(file);
            String line;
            while ((line=br.readLine()) != null){
                // There should only be 1 line
                return line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // returns empty string if ExecutionTime.txt is empty
        return "";
    }

}
