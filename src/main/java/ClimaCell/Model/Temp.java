package ClimaCell.Model;

import com.squareup.moshi.Json;

public class Temp {
    @Json(name = "observation_time")
    private String observationTime;
    @Json(name = "min")
    private Min min;
    @Json(name = "max")
    private Max max;

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public Temp withObservationTime(String observationTime) {
        this.observationTime = observationTime;
        return this;
    }

    public Min getMin() {
        return min;
    }

    public void setMin(Min min) {
        this.min = min;
    }

    public Temp withMin(Min min) {
        this.min = min;
        return this;
    }

    public Max getMax() {
        return max;
    }

    public void setMax(Max max) {
        this.max = max;
    }

    public Temp withMax(Max max) {
        this.max = max;
        return this;
    }
}
