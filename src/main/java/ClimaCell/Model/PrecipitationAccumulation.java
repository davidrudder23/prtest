package ClimaCell.Model;

import com.squareup.moshi.Json;

public class PrecipitationAccumulation {
    @Json(name = "value")
    private double value;
    @Json(name = "units")
    private String units;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public PrecipitationAccumulation withValue(Double value) {
        this.value = value;
        return this;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public PrecipitationAccumulation withUnits(String units) {
        this.units = units;
        return this;
    }
    @Override
    public String toString() {
        return "PrecipitationAccumulation{" +
                "value=" + value +
                ", units='" + units + '\'' +
                '}';
    }
}
