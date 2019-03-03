package hu.ait.weatheraport.Model;

import java.util.List;

/**
 * Created by rohit on 10/15/15.
 */

public class WeatherData {

    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;
    private Long dt;
    private Sys sys;
    private Long id;
    private String name;
    private Long cod;

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Rain getRain() {
        return rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public Long getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCod() {
        return cod;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }
}