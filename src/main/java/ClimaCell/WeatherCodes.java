package ClimaCell;

public enum WeatherCodes {
    RAIN_HEAVY("rain_heavy"),
    RAIN("rain"),
    RAIN_LIGHT("rain_light"),
    FREEZING_RAIN_HEAVY("FREEZING_RAIN_HEAVY"),
    FREEZING_RAIN("freezing_rain"),
    FREEZING_RAIN_LIGHT("freezing_rain_light"),
    FREEZING_DRIZZLE("freezing_drizzle"),
    DRIZZLE("drizzle"),
    ICE_PELLETS_HEAVY("ice_pellets_heavy"),
    SNOW_HEAVY("snow_heavy"),
    SNOW("snow"),
    SNOW_LIGHT("snow_light"),
    FLURRIES("FLURRIES"),
    THUNDERSTORM("tstorm"),
    FOG_LIGHT("fog_light"),
    FOG("fog"),
    CLOUDY("cloudy"),
    MOSTLY_CLOUDY("mostly_cloud"),
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
}
