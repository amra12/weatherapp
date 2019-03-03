package hu.ait.weatheraport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import hu.ait.weatheraport.adapter.CitiesRecyclerAdapter;
import hu.ait.weatheraport.data.City;
import io.realm.Realm;

/**
 * Created by AIT on 10/7/17.
 */

public class AddCity extends AppCompatActivity implements MyCallback {
    public static final String KEY_CITY = "KEY_PLACE";

    private EditText cityName;
    private CitiesRecyclerAdapter citiesRecyclerAdapter;
    private City cityEdit;
    private Button save;

    public AddCity(){
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        setupUI();

        citiesRecyclerAdapter = new CitiesRecyclerAdapter(this, (getRealm()));

        if (getIntent().hasExtra(MainActivity.KEY_CITY_ID)) {
            String placeID = getIntent().getStringExtra(MainActivity.KEY_CITY_ID);
            cityEdit = getRealm().where(City.class)
                    .equalTo("placeID", placeID)
                    .findFirst();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTheCity();
            }
        });

    }


    public Realm getRealm() {
        return ((MainApplication) getApplication()).getRealmCities();
    }

    private void addTheCity() {
            WeatherDetails details = new WeatherDetails();
            details.cityExists(cityName.getText().toString(), AddCity.this);
    }

    private void setupUI() {
        cityName = (EditText) findViewById(R.id.etCityName);
        save = (Button) findViewById(R.id.btnSave) ;
    }

    public void complete(String nameOfCity, String weatherOfCity, int value) {
        if (value == 2) {
            cityName.setError(nameOfCity);
        } else {
            cityName.setText(nameOfCity);
            boolean check = citiesRecyclerAdapter.checkCityExits(nameOfCity);
            if (check == true) {
                cityName.setError("City has already been added");
            } else {
                boolean existence = citiesRecyclerAdapter.checkCityExits(nameOfCity);
                if (existence == false) {
                    cityEdit = citiesRecyclerAdapter.addCity(nameOfCity, weatherOfCity);
                    Intent intentResult = new Intent();
                    intentResult.putExtra(KEY_CITY, cityEdit.getCitiesID());
                    setResult(RESULT_OK, intentResult);
                } else {
                    Toast.makeText(AddCity.this, "City has already been added", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }
}
