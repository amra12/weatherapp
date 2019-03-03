package hu.ait.weatheraport.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class City extends RealmObject{

    private String cityName;
    private String weatherIcon;

    @PrimaryKey
    private String cityID;


    public City() {
    }

    public City(String name) {
        this.cityName = name;
    }

    public String getCitiesName() {
        return cityName;
    }

    public void setCitiesName(String name) {
        this.cityName = name;
    }

    public String getCitiesID() {
        return cityID;
    }

    public String getWeatherIcon() { return weatherIcon; }

    public void setWeatherIcon(String weatherIcon) { this.weatherIcon = weatherIcon; }
}
