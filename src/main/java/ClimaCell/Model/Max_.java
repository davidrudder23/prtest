package ClimaCell.Model;

import com.squareup.moshi.Json;

public class Max_ {

    @Json(name = "value")
    private Double value;
    @Json(name = "units")
    private String units;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Max_ withValue(Double value) {
        this.value = value;
        return this;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Max_ withUnits(String units) {
        this.units = units;
        return this;
    }

}