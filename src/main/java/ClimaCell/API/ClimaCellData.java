package ClimaCell.API;

import ClimaCell.Model.ClimaCell;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.concurrent.Callable;

public class ClimaCellData implements Callable<ClimaCell> {
    private final static OkHttpClient client = new OkHttpClient();
    private final static String climateCellUrl = "https://api.climacell.co/v3/weather/forecast/daily?";
    /**
     * Potential fields for Daily:
     * precipitation, precipitation_accumulation, temp, feels_like, dewpoint, wind_speed,
     * visibility, humidity, wind_direction, sunrise, sunset, moon_phase, weather_code
     */
    private final static String climateCellUnitAndCountryCode = "&unit_system=us&start_time=now";
    private final static String climateCellFields = "" +
            "&fields=precipitation" +
            ",temp" +
            ",weather_code" +
            ",precipitation_accumulation" +
            ",precipitation";

    private final static String climateCellKey = "&apikey=1h0MB1CLlmZBNEeSaxl8nstk296WD0UV";

    private String iso8601EndDate;
    private String longitude;
    private String latitude;

    /*
    Moshi.Builder() and JsonAdapter are documented as thread safe and should not
    cause an race/lock conditions.

    Todo determine if Moshi is being re instantiated on call to a thread or if it is a shared single instance
    Todo determine if there is a more optimal way for this to be done.
     */
    private final Moshi moshi = new Moshi.Builder().build();
    private final JsonAdapter<ClimaCell> climaCellJsonAdapter = moshi.adapter(ClimaCell.class);

    /**
     * @param iso8601EndDate
     * @param longitude
     * @param latitude
     */
    public ClimaCellData(String iso8601EndDate, String longitude, String latitude) {
        this.iso8601EndDate = iso8601EndDate;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * ClimaCell: https://developer.climacell.co/v3/reference#get-daily
     * This API utilizes ISO 8601 format to handle Start and end time.
     * For my purpose I am utilizing &start_time=now which handles getting
     * the current ISO 8601 time. An &end_time=<ISO 8601> time must be passed in
     * if the desired end date is less than 16 days.
     *
     * @return ClimaCell object for a single day. It does not currently handle a multi-day parse.
     */
    public ClimaCell call() {
        ClimaCell climaCell = null;
        Request request = new Request.Builder().url(
                climateCellUrl
                        + "lat=" + latitude
                        + "&lon=" + longitude
                        + climateCellUnitAndCountryCode
                        + "&end_time="
                        + iso8601EndDate
                        + climateCellFields
                        + climateCellKey
        ).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
            String temp1 = response.body().string();
            /*
            Clima Cell Api is return an array and moshi-adapter is expecting an object. By removing the
            beginning and end characters we should be left with a Json Object.
             */
            String temp2 = StringUtils.removeStart(temp1, "[");
            String temp3 = StringUtils.removeEnd(temp2, "]");
            climaCell = climaCellJsonAdapter.fromJson(temp3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return climaCell;
    }

    /**
     * Utilized to print all headers in case of debugging
     *
     * @param responseHeaders
     */
    public static void printHeaders(Headers responseHeaders) {
        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }
    }
}
