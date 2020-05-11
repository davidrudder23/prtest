package ClimaCell.Model;

import com.squareup.moshi.Json;

public class WeatherCode {
    @Json(name = "value")
    private String value;

    public WeatherCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "WeatherCode{" +
                "value='" + value + '\'' +
                '}';
    }
}
