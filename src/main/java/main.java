import ClimaCell.API.ClimaCellData;
import ClimaCell.Model.ClimaCell;
import Ford.FordDealer;
import Ford.FordDealerInformation;
import Location.LonLatLocator;
import Time.GenerateTime;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;

/*
This program will not currently produce any data since it is utilizing the free version of ClimaCell. Plans to get paid version.

// TODO(1) Track request to ensure no calls are made if rate limit has already been reached.
// TODO(2) Write weather data to file and store it for attempt to utilize 2 hours to reach the needed 181 request needed for free
// TODO(3) Write GUI to Generate and Select a download location
// TODO(4) Create cache for most common long and lats

 */
public class main {
    public static void main(String[] args) throws IOException {

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
        for (FordDealer fordDealer : fordDealers) {

            Callable<ClimaCell> dailyClimaData = new ClimaCellData(
                    GenerateTime.getISO8601TimeNow(),
                    // replace is because some longitudes have white space in front of the string. This causes a %20 in request URL which will generate a bad request.
                    fordDealer.getLonLatZip().getLongitude().replace(" ", ""),
                    fordDealer.getLonLatZip().getLatitude());
            Future<ClimaCell> submit = executor.submit(dailyClimaData);
            if (submit == null){
                /*
                If null then rate limit was reached. 100 request per hour with free account.
                 */
                break;
            }
            fordDealer.setWeather(submit);
        }
        executor.shutdown();
        Excel.generateNewWeatherTemplate(fordDealers);
    }
}