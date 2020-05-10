import Ford.FordDealer;
import Ford.FordDealerInformation;
import Location.LonLatLocator;

import java.io.*;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) throws IOException {
        ArrayList<FordDealer> fordDealers = FordDealerInformation.loadFordData();
        
        for (FordDealer fordDealer : fordDealers) {
            fordDealer.setLonLatZip(LonLatLocator.getWithZipCode(fordDealer.getZipCode()));
        }

        Excel.generateNewWeatherTemplate(fordDealers);


//        long startTime = System.nanoTime();
//        long duration = System.nanoTime() - startTime;
//        System.out.println(duration);



        /*
        RunTime.getRuntime().availableProcessors will return the number of processors a system
        has available based on hardware.
         */
//         ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
//        List<Future<ClimaCell>> listOfWeatherData = new ArrayList<>();
//        Callable<ClimaCell> dailyClimateData = new ClimaCellData(ISO8601.getTimeNow(), "-72.560791", "42.08069");
//        listOfWeatherData.add(executor.submit(dailyClimateData));
//
//
//        listOfWeatherData.forEach(data -> {
//            try {
//                System.out.println(data.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
//        executor.shutdown();
    }
}

// TODO Create basic excel sheet
// TODO Fill excel sheet with data
// TODO Setup color coding and any other filtering
// TODO Create cache for most common long and lats