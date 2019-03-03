package hu.ait.weatheraport;

import hu.ait.weatheraport.Model.WeatherData;

/**
 * Created by AIT on 10/15/17.
 */

public interface GetInfo {
    void getInfo(WeatherData weatherData);
    void show(String name, String imgURL);
}
