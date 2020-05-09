package ClimaCell.Model;

import com.squareup.moshi.Json;

public class Max {
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

    public Max withValue(Double value) {
        this.value = value;
        return this;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Max withUnits(String units) {
        this.units = units;
        return this;
    }

    @Override
    public String toString() {
        return "Max{" +
                "value=" + value +
                ", units='" + units + '\'' +
                '}';
    }
}
