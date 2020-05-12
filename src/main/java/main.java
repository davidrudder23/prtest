import ClimaCell.API.ClimaCellData;
import ClimaCell.Model.ClimaCell;
import Ford.FordDealer;
import Ford.FordDealerInformation;
import Location.LonLatLocator;
import Time.GenerateTime;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class main {
    public static void main(String[] args) throws IOException {

//        long startTime = System.nanoTime();
//        long duration = System.nanoTime() - startTime;
//        System.out.println(duration);


        // Get all ford data from DealerInformation.csv
        ArrayList<FordDealer> fordDealers = FordDealerInformation.loadFordData();

        // Get all longitude and latitude from USZipCodesFrom2013GovernmentData
        for (FordDealer fordDealer : fordDealers) {
            fordDealer.setLonLatZip(LonLatLocator.getWithZipCode(fordDealer.getZipCode()));
        }

        /*
        RunTime.getRuntime().availableProcessors will return the number of processors a system
        has available based on hardware.
         */
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        List<Future<ClimaCell>> listOfWeatherData = new ArrayList<>();


        Callable<ClimaCell> dailyClimateData = new ClimaCellData(GenerateTime.getISO8601TimeNow(), "-72.560791", "42.08069");
        listOfWeatherData.add(executor.submit(dailyClimateData));


        listOfWeatherData.forEach(data -> {
            try {
                System.out.println(data.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();

        Excel.generateNewWeatherTemplate(fordDealers);
    }
}

// TODO Create cache for most common long and lats