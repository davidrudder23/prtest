package Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ISO8601 {

    /**
     * This method is utilized to get the current Time.ISO8601 time.
     * This is required due to an expected end_time for ClimaCell API
     *
     * @return String ISO 8601 current date in format yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static String getTimeNow() {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(timeZone);
        String currentTimeAsISO8601 = dateFormat.format(new Date());
        return currentTimeAsISO8601;
    }
}
