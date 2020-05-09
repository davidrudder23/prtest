package ClimaCell.Model;

import com.squareup.moshi.Json;

class ObservationTime {

    @Json(name = "value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ObservationTime withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "ObservationTime{" +
                "value='" + value + '\'' +
                '}';
    }
}
