package ClimaCell;

import java.util.HashMap;
import java.util.Map;

public enum WeatherCodes {
    RAIN_HEAVY("rain_heavy"),
    RAIN("rain"),
    RAIN_LIGHT("rain_light"),
    FREEZING_RAIN_HEAVY("freezing_rain_heavy"),
    FREEZING_RAIN("freezing_rain"),
    FREEZING_RAIN_LIGHT("freezing_rain_light"),
    FREEZING_DRIZZLE("freezing_drizzle"),
    DRIZZLE("drizzle"),
    ICE_PELLETS_HEAVY("ice_pellets_heavy"),
    SNOW_HEAVY("snow_heavy"),
    SNOW("snow"),
    SNOW_LIGHT("snow_light"),
    FLURRIES("flurries"),
    THUNDERSTORM("tstorm"),
    FOG_LIGHT("fog_light"),
    FOG("fog"),
    CLOUDY("cloudy"),
    MOSTLY_CLOUDY("mostly_cloudy"),
    PARTLY_CLOUDY("partly_cloudy"),
    MOSTLY_CLEAR("mostly_clear"),
    CLEAR("clear");

    private String code;

    WeatherCodes(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<String, WeatherCodes> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static
    {
        for(WeatherCodes var : WeatherCodes.values())
        {
            lookup.put(var.getCode(), var);
        }
    }

    //This method can be used for reverse lookup purpose
    public static WeatherCodes get(String url)
    {
        return lookup.get(url);
    }

    @Override
    public String toString() {
        return this.code;  }
}
