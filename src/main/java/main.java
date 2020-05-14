import ClimaCell.API.ClimaCellData;
import ClimaCell.Model.ClimaCell;
import Ford.FordDealer;
import Ford.FordDealerInformation;
import Location.LonLatLocator;
import Time.GenerateTime;
import Time.TimeTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

/*

// TODO Ensure same report can be exported

 */
public class main {
    public static void main(String[] args) throws IOException {

        // Get all ford data from DealerInformation.csv
        ArrayList<FordDealer> fordDealers = FordDealerInformation.loadFordData();

        // Get all longitude and latitude from USZipCodesFrom2013GovernmentData
        for (FordDealer fordDealer : fordDealers) {
            fordDealer.setLonLatZip(LonLatLocator.getWithZipCode(fordDealer.getZipCode()));
        }

        long lastExecutionTime = TimeTracker.readLastExecutionTime();
        if (lastExecutionTime == -1) {
            System.err.println("There was an error reading ExecutionTime.txt ");
        } else {
            /*
            Checking if at minimum an hour has passed since the last time this program
            attempted to generate the weather utilizing the ClimaCell API. This tracks
            solely for the purpose of not running the application when the rate limit
            has already been reached for an hour. 100 calls/hr x2 since there is 2 keys
             */
            System.out.println(GenerateTime.getUnixTime());
            if ((GenerateTime.getUnixTime() - lastExecutionTime) > 3600) {
            /*
            RunTime.getRuntime().availableProcessors will return the number of processors a system
            has available based on hardware.
             */
                ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
                for (FordDealer fordDealer : fordDealers) {

                    Callable<ClimaCell> dailyClimaData = new ClimaCellData(
                            GenerateTime.getISO8601TimeNow(),
                    /*
                     The replace is because some longitudes have white space in front of the string.
                     This causes a %20 in request URL which will generate a bad request.
                   `*/
                            fordDealer.getLonLatZip().getLongitude().replace(" ", ""),
                            fordDealer.getLonLatZip().getLatitude());
                    Future<ClimaCell> submit = executor.submit(dailyClimaData);
                    try {
                        System.out.println(submit.get());
                        fordDealer.setWeather(submit.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                executor.shutdown();
                TimeTracker.saveCurrentExecutionTime();
            } else {
                // TODO replace with something more meaningful to client e.g., popup window with notification
                System.out.println("there was an error running the program");
            }
        }
        Excel.generateNewWeatherTemplate(fordDealers);

        // TODO javaFX UI to export report

    }
}