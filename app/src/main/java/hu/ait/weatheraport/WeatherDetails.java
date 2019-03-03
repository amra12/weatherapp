package hu.ait.weatheraport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import hu.ait.weatheraport.Model.WeatherData;
import hu.ait.weatheraport.data.City;
import io.realm.Realm;

public class WeatherDetails  extends AppCompatActivity implements GetInfo {
    private TextView cityName;
    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView humidity;
    private TextView windSpeed;
    private TextView condition;
    private ImageView appBarImage;



    public static final String TAG = MainActivity.class.getSimpleName();
    private String tempCityName;
    private String tempWeatherIcon;
    private City city = null;
    private MyCallback callback;
    private Integer caller;


    public WeatherDetails(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_detail);

        //toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        appBarImage = (ImageView) findViewById(R.id.na_icon);
        appBarImage.setImageResource(R.drawable.icon_na);



        initializeItems();

        if (getIntent().hasExtra(MainActivity.KEY_CITY_ID)) {
            String cityID = getIntent().getStringExtra(MainActivity.KEY_CITY_ID);
            initializeCity(cityID);
            caller = 1;
            tempCityName = city.getCitiesName();
            getDetails(caller);
        } else {
            Toast.makeText(WeatherDetails.this, "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
        }

    }
    private  void initializeCity(String cityID) {
        city = getRealm().where(City.class)
                .equalTo("cityID", cityID)
                .findFirst();
    }
    private void initializeItems() {
        cityName = (TextView) findViewById(R.id.cityName);
        currentTemp = (TextView) findViewById(R.id.currentTemp);
        minTemp = (TextView) findViewById(R.id.minTemp);
        maxTemp = (TextView) findViewById(R.id.maxTemp);
        condition = (TextView) findViewById(R.id.condition);
        humidity = (TextView) findViewById(R.id.humidity);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
    }



    public Realm getRealm() {
        return ((MainApplication) getApplication()).getRealmCities();
    }

    public void cityExists(String city, MyCallback callback) {
        tempCityName = city;
        this.callback = callback;
        caller = 2;
        getDetails(caller);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String formatTime(Long dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        return timeFormat.format(dateObject);
    }

    private void saveTemplatedImage(String image) {
        int myImage;

        switch (image) {
            case "01d":case "01n":
               myImage = R.drawable.daysun;
                break;
            case "02d":case "02n":
                myImage=R.drawable.fewcloud;
                break;
            case "03d":case "03n":
                myImage=R.drawable.cloud;
                break;
            case "04d":case "04n":
                myImage=R.drawable.darkcloud;
                break;
            case "09d":case "09n":
                myImage=R.drawable.showerrain;
                break;
            case "10d":case "10n":
                myImage=R.drawable.rain;
                break;
            case "11d":case "11n":
                myImage=R.drawable.thunderst;
                break;
            case "13d":case "13n":
                myImage=R.drawable.snow;
                break;
            case "50d":case "50n":
                myImage=R.drawable.mist;
                break;
            default:
                myImage = R.drawable.icon_na;
                break;
        }

        appBarImage.setImageResource(myImage);
    }

    public void getDetails(Integer caller) {
        WeatherMapModel weather = new WeatherMapModel();

        if (caller == 1) {
           weather.getCityWeather(tempCityName,this);
        } else {
            weather.getTheImageIcon(tempCityName,this);
        }
    }

    @Override
    public void getInfo(WeatherData currentWeather) {
        cityName.setText(currentWeather.getName() + ", " + currentWeather.getSys().getCountry());
        currentTemp.setText((int)currentWeather.getMain().getTemp() + "°C");
        humidity.setText(currentWeather.getMain().getHumidity()+"%");
        minTemp.setText(currentWeather.getMain().getTemp_min() + "°C");
        maxTemp.setText(currentWeather.getMain().getTemp_max() + "°C");
        windSpeed.setText(currentWeather.getWind().getSpeed()+" m/s");
        condition.setText(""+currentWeather.getWeather().get(0).getDescription());
        saveTemplatedImage(currentWeather.getWeather().get(0).getIcon());
    }

    @Override
    public void show(String name, String imgURL) {
        tempCityName = name;
        tempWeatherIcon = imgURL;
        Integer i = 1;
        if (tempWeatherIcon==null) {
            i = 2;
        }
        callback.complete(tempCityName,tempWeatherIcon,i);

    }
}