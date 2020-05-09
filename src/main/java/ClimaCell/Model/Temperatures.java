package ClimaCell.Model;

public class Temperatures {
    private String observation_time;
    private Temp temp;

    public Temperatures() {
    }

    public Temperatures(String observation_time, Temp temp) {
        this.observation_time = observation_time;
        this.temp = temp;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public void setObservation_time(String observation_time) {
        this.observation_time = observation_time;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Temperatures{" +
                "observation_time='" + observation_time + '\'' +
                ", temp=" + temp +
                '}';
    }
}
