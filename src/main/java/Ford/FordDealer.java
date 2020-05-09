package Ford;

import ClimaCell.Model.ClimaCell;
import Location.LonLatZip;

public class FordDealer {
    private String route;
    private String zipCode;
    private String name;
    private String city;
    private String stateCode;
    private String actualTime;
    private String expectedTime;
    private LonLatZip lonLatZip;
    private ClimaCell weather;

    public FordDealer(String route, String zipCode, String name, String city, String stateCode, String actualTime, String expectedTime, LonLatZip lonLatZip, ClimaCell weather) {
        this.route = route;
        this.zipCode = zipCode;
        this.name = name;
        this.city = city;
        this.stateCode = stateCode;
        this.actualTime = actualTime;
        this.expectedTime = expectedTime;
        this.lonLatZip = lonLatZip;
        this.weather = weather;
    }

    public FordDealer(String route, String zipCode, String name, String city, String stateCode, String actualTime, String expectedTime, LonLatZip lonLatZip) {
        this.route = route;
        this.zipCode = zipCode;
        this.name = name;
        this.city = city;
        this.stateCode = stateCode;
        this.actualTime = actualTime;
        this.expectedTime = expectedTime;
        this.lonLatZip = lonLatZip;
    }

    public FordDealer(String route, String zipCode, String name, String city, String stateCode, String actualTime, String expectedTime) {
        this.route = route;
        this.zipCode = zipCode;
        this.name = name;
        this.city = city;
        this.stateCode = stateCode;
        this.actualTime = actualTime;
        this.expectedTime = expectedTime;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public LonLatZip getLonLatZip() {
        return lonLatZip;
    }

    public void setLonLatZip(LonLatZip lonLatZip) {
        this.lonLatZip = lonLatZip;
    }

    public ClimaCell getWeather() {
        return weather;
    }

    public void setWeather(ClimaCell weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "FordDealer{" +
                "route='" + route + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", actualTime='" + actualTime + '\'' +
                ", expectedTime='" + expectedTime + '\'' +
                ", lonLatZip=" + lonLatZip +
                ", weather=" + weather +
                '}';
    }
}
