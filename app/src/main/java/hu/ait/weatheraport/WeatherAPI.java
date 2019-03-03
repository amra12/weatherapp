package hu.ait.weatheraport;

import hu.ait.weatheraport.Model.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by AIT on 10/15/17.
 */

public interface WeatherAPI {
    public static final String Base_URL = "http://api.openweathermap.org/";

    @GET("/data/2.5/weather")
    Call<WeatherData> getWeatherInfo(@Query("q") String name,
                                     @Query("units") String unit,
                                     @Query("APPID") String appid);
}
