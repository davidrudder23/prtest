package ClimaCell.Model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * This class is utilized to Access Clima Cell Data.
 * Additionally, this class is utilized by square/Moshi to parse Json from Clima Cell;
 */
public class ClimaCell {
    @Json(name = "temp")
    private List<Temp> temp = null;
    @Json(name = "precipitation_accumulation")
    private PrecipitationAccumulation precipitationAccumulation;
    @Json(name = "precipitation")
    private List<Precipitation> precipitation = null;
    @Json(name = "weather_code")
    private WeatherCode weatherCode;
    @Json(name = "observation_time")
    private ObservationTime observationTime;
    @Json(name = "lat")
    private Double lat;
    @Json(name = "lon")
    private Double lon;

    public List<Temp> getTemp() {
        return temp;
    }

    public void setTemp(List<Temp> temp) {
        this.temp = temp;
    }

    public ClimaCell withTemp(List<Temp> temp) {
        this.temp = temp;
        return this;
    }

    public PrecipitationAccumulation getPrecipitationAccumulation() {
        return precipitationAccumulation;
    }

    public void setPrecipitationAccumulation(PrecipitationAccumulation precipitationAccumulation) {
        this.precipitationAccumulation = precipitationAccumulation;
    }

    public ClimaCell withPrecipitationAccumulation(PrecipitationAccumulation precipitationAccumulation) {
        this.precipitationAccumulation = precipitationAccumulation;
        return this;
    }

    public List<Precipitation> getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(List<Precipitation> precipitation) {
        this.precipitation = precipitation;
    }

    public ClimaCell withPrecipitation(List<Precipitation> precipitation) {
        this.precipitation = precipitation;
        return this;
    }

    public WeatherCode getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(WeatherCode weatherCode) {
        this.weatherCode = weatherCode;
    }

    public ClimaCell withWeatherCode(WeatherCode weatherCode) {
        this.weatherCode = weatherCode;
        return this;
    }

    public ObservationTime getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(ObservationTime observationTime) {
        this.observationTime = observationTime;
    }

    public ClimaCell withObservationTime(ObservationTime observationTime) {
        this.observationTime = observationTime;
        return this;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public ClimaCell withLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public ClimaCell withLon(Double lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public String toString() {
        return "ClimaCell{" + "\n" +
                "   temp=" + temp + "\n" +
                ",  precipitation_accumulation=" + precipitationAccumulation + "\n" +
                ",  precipitation=" + precipitation + "\n" +
                ",  weather_code=" + weatherCode + "\n" +
                ",  observationTime=" + observationTime + "\n" +
                ",  lat=" + lat + "\n" +
                ",  lon=" + lon + "\n" +
                '}';
    }
}