package Ford;

import ClimaCell.Model.ClimaCell;
import Location.LonLatZip;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FordDealer {
    private String route;
    private String dealerCode;
    private String name;
    private String city;
    private String stateCode;
    private String actualTime;
    private String expectedTime;
    private String zipCode;
    private LonLatZip lonLatZip;
    private Future<ClimaCell> weather;


    public FordDealer(String route, String dealerCode, String name, String city, String stateCode, String actualTime, String expectedTime, LonLatZip lonLatZip, Future<ClimaCell> weather) {
        this.route = route;
        this.dealerCode = dealerCode;
        this.name = name;
        this.city = city;
        this.stateCode = stateCode;
        this.actualTime = actualTime;
        this.expectedTime = expectedTime;
        this.lonLatZip = lonLatZip;
        this.weather = weather;
    }

    public FordDealer(String route, String dealerCode, String name, String city, String stateCode, String actualTime, String expectedTime, String zipCode, LonLatZip lonLatZip) {
        this.route = route;
        this.dealerCode = dealerCode;
        this.name = name;
        this.city = city;
        this.stateCode = stateCode;
        this.actualTime = actualTime;
        this.expectedTime = expectedTime;
        this.lonLatZip = lonLatZip;
        this.zipCode = zipCode;
    }

    public FordDealer(String route, String dealerCode, String name, String city, String stateCode, String actualTime, String expectedTime, String zipCode) {
        this.route = route;
        this.dealerCode = dealerCode;
        this.name = name;
        this.city = city;
        this.stateCode = stateCode;
        this.actualTime = actualTime;
        this.expectedTime = expectedTime;
        this.zipCode = zipCode;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
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

    public ClimaCell getWeather() throws ExecutionException, InterruptedException {
        return weather.get();
    }

    public void setWeather(Future<ClimaCell> weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "FordDealer{" +
                "route='" + route + '\'' +
                ", dealerCode='" + dealerCode + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", actualTime='" + actualTime + '\'' +
                ", expectedTime='" + expectedTime + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", lonLatZip=" + lonLatZip +
                ", weather=" + weather +
                '}';
    }
}
