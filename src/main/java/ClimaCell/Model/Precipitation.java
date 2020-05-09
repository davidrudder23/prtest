
package ClimaCell.Model;

import com.squareup.moshi.Json;

public class Precipitation {

    @Json(name = "observation_time")
    private String observationTime;
    @Json(name = "max")
    private Max_ max;

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public Precipitation withObservationTime(String observationTime) {
        this.observationTime = observationTime;
        return this;
    }

    public Max_ getMax() {
        return max;
    }

    public void setMax(Max_ max) {
        this.max = max;
    }

    public Precipitation withMax(Max_ max) {
        this.max = max;
        return this;
    }

}