package hu.ait.weatheraport;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hu.ait.weatheraport.adapter.CitiesRecyclerAdapter;
import hu.ait.weatheraport.data.City;
import hu.ait.weatheraport.touch.TodoItemTouchHelperCallback;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_CITY_ID = "KEY_CITY_ID";
    public static final int REQUEST_CODE_ADD = 101;
    public static final int REQUEST_CODE_VIEW = 102;

    private CitiesRecyclerAdapter citiesRecyclerAdapter;
    private RecyclerView recyclerCity;
    private CoordinatorLayout layoutContent;
    private DrawerLayout drawerLayout;



    private int positionToAdd = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = (CoordinatorLayout) findViewById(
                R.id.layoutContent);

        ((MainApplication)getApplication()).openRealm();

        setupUI();
    }

    private void setupUI() {
        setUpDrawerLayout();
        setUpAddTodoUI();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerCity = (RecyclerView) findViewById(R.id.recyclerViewPlaces);
        recyclerCity.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCity.setLayoutManager(layoutManager);

        citiesRecyclerAdapter = new CitiesRecyclerAdapter(this, (getRealm()));
        recyclerCity.setAdapter(citiesRecyclerAdapter);

        // adding touch support
        ItemTouchHelper.Callback callback = new TodoItemTouchHelperCallback(citiesRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerCity);

    }

    public Realm getRealm() {
        return ((MainApplication) getApplication()).getRealmCities();
    }

    private void setUpAddTodoUI() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAddTodoDialog();
            }
        });
    }

    private void setUpDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.action_add:
                                showAddTodoDialog();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.action_about:
                                Snackbar.make(layoutContent,
                                        getString(R.string.about_txt),
                                        Snackbar.LENGTH_LONG
                                ).setAction(R.string.action_hide, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //...
                                    }
                                }).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                        }

                        return false;
                    }
                });

        setUpToolBar();
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void showAddTodoDialog() {
        positionToAdd = 0;
        Intent intentStart = new Intent(MainActivity.this,
                AddCity.class);
        startActivityForResult(intentStart, REQUEST_CODE_ADD);
    }

    public void openEditActivity(String cityID) {

        Intent startEdit = new Intent(this, WeatherDetails.class);

        startEdit.putExtra(KEY_CITY_ID, cityID);

        startActivityForResult(startEdit, REQUEST_CODE_VIEW);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_CODE_ADD) {
                    String cityID  = data.getStringExtra(
                            AddCity.KEY_CITY);
                    City city = getRealm().where(City.class)
                            .equalTo("cityID", cityID)
                            .findFirst();
                    citiesRecyclerAdapter.refreshTheList(city);
                } else if (requestCode == REQUEST_CODE_VIEW) {

                }
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MainApplication)getApplication()).closeRealm();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
