package hu.ait.weatheraport;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.ait.weatheraport.Model.WeatherData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AIT on 10/15/17.
 */

public class WeatherMapModel implements Callback<WeatherData> {
    private String nameOfCity;



    private MyCallback addCityCallback;

    private GetInfo infoCallback;

    private Integer caller;

    public static final String TAG = WeatherAPI.class.getSimpleName();
    private String API_KEY="fd268631bb989e40e375692330f76cb9";

    private void getCityData(String location) {
        nameOfCity = location;
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherAPI.Base_URL).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        WeatherAPI openWeatherAPIInterface = retrofit.create(WeatherAPI.class);

        Call<WeatherData> call = openWeatherAPIInterface.getWeatherInfo(nameOfCity,
                "metric",
                API_KEY);
        call.enqueue(this);
    }

    public void getCityWeather(String location, GetInfo callback) {
        this.infoCallback = callback;
        caller =1;
        getCityData(location);
    }

    public void getTheImageIcon(String location, GetInfo callback) {

        this.infoCallback = callback;
        caller =2;
        getCityData(location);
    }

    public void checkForMsg(String location, MyCallback callback) {
        nameOfCity = location;
        this.addCityCallback = callback;
        caller =2;
        getCityData(location);
    }

    @Override
    public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {

        if(response.isSuccessful()) {
            WeatherData currentWeather = response.body();
            nameOfCity = currentWeather.getName();

           if (caller == 1) {
               infoCallback.getInfo(currentWeather);

           } else {
               infoCallback.show(nameOfCity, currentWeather.getWeather().get(0).getIcon());
           }

        } else {
            if (caller == 2) {
                infoCallback.show(response.message(), null);
            }
            Log.v(TAG, response.message());
        }
    }

    @Override
    public void onFailure(Call<WeatherData> call, Throwable t) {
        Log.v(TAG, t.getMessage());
    }


}
