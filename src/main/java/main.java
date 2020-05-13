import ClimaCell.API.ClimaCellData;
import ClimaCell.Model.ClimaCell;
import Ford.FordDealer;
import Ford.FordDealerInformation;
import Location.LonLatLocator;
import Time.GenerateTime;
import Time.TimeTracker;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;

/*
This program will not currently produce any data since it is utilizing the free version of ClimaCell. Plans to get paid version.

// TODO Track request to ensure no calls are made if rate limit has already been reached.
// TODO Write weather data to file and store it for attempt to utilize 2 hours to reach the needed 181 request needed for free
// TODO Create cache for most common long and lats
// TODO Write GUI to Generate and Select a download location

 */
public class main {
    public static void main(String[] args) throws IOException {

        // Get all ford data from DealerInformation.csv
        ArrayList<FordDealer> fordDealers = FordDealerInformation.loadFordData();

        // Get all longitude and latitude from USZipCodesFrom2013GovernmentData
        for (FordDealer fordDealer : fordDealers) {
            fordDealer.setLonLatZip(LonLatLocator.getWithZipCode(fordDealer.getZipCode()));
        }

        fordDealers.forEach(System.out::println);

        // TODO track epoch time here
        TimeTracker.saveCurrentExecutionTime();
        long lastExecutionTime = TimeTracker.readLastExecutionTime();
        if (lastExecutionTime == -1){
            System.err.println("There was an error reading ExecutionTime.txt ");
        }else{
            /*
            Checking if at minimum an hour has passed since the last time this program
            attempted to generate the weather utilizing the ClimaCell API. This tracks
            solely for the purpose of not running the application when the rate limit
            has already been reached for an hour. 100 calls/hr x2 since there is 2 keys
             */
            if ((GenerateTime.getUnixTime() - lastExecutionTime) > 3600){




                TimeTracker.saveCurrentExecutionTime();
            }
        }

        /*
        RunTime.getRuntime().availableProcessors will return the number of processors a system
        has available based on hardware.
         */
//        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
//        for (FordDealer fordDealer : fordDealers) {
//
//            Callable<ClimaCell> dailyClimaData = new ClimaCellData(
//                    GenerateTime.getISO8601TimeNow(),
//                    // replace is because some longitudes have white space in front of the string. This causes a %20 in request URL which will generate a bad request.
//                    fordDealer.getLonLatZip().getLongitude().replace(" ", ""),
//                    fordDealer.getLonLatZip().getLatitude());
//            Future<ClimaCell> submit = executor.submit(dailyClimaData);
//            try {
//                System.out.println(submit.get());
//                fordDealer.setWeather(submit.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        executor.shutdown();
        Excel.generateNewWeatherTemplate(fordDealers);
    }
}