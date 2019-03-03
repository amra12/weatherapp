package hu.ait.weatheraport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import hu.ait.weatheraport.MainActivity;
import hu.ait.weatheraport.R;
import hu.ait.weatheraport.data.City;
import hu.ait.weatheraport.touch.TodoTouchHelperAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class CitiesRecyclerAdapter
        extends RecyclerView.Adapter<CitiesRecyclerAdapter.ViewHolder>
        implements TodoTouchHelperAdapter{

    private List<City> citiesList;

    private Context context;

    private Realm realmTodo;


    public CitiesRecyclerAdapter(Context context, Realm realmTodo) {
        this.context = context;
        this.realmTodo = realmTodo;

        RealmResults<City> cityResult = realmTodo.where(City.class).findAll().sort("cityName", Sort.ASCENDING);

        citiesList = new ArrayList<City>();

        for (int i = 0; i < cityResult.size(); i++) {
            citiesList.add(cityResult.get(i));

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cities_row, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvCityName.setText(citiesList.get(position).getCitiesName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).openEditActivity(
                        citiesList.get(holder.getAdapterPosition()).getCitiesID()
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }


    public City addCity(String cityTitle, String weather) {
        realmTodo.beginTransaction();
        City newCity = realmTodo.createObject(City.class, UUID.randomUUID().toString());
        newCity.setCitiesName(cityTitle);
        newCity.setWeatherIcon(weather);
        realmTodo.commitTransaction();

        return newCity;
    }

    public boolean checkCityExits(String city) {
        for(int i=0; i<citiesList.size(); ++i) {
            if (city.equals(citiesList.get(i).getCitiesName())) {
                return true;
            }
        }
        return false;
    }

    public void refreshTheList(City newCity) {
        citiesList.add(newCity);
        notifyDataSetChanged();
    }


    @Override
    public void onItemDismiss(int position) {
        realmTodo.beginTransaction();
        citiesList.get(position).deleteFromRealm();
        realmTodo.commitTransaction();
        citiesList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(citiesList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(citiesList, i, i - 1);
            }
        }


        notifyItemMoved(fromPosition, toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       private ImageView ivIcon;
        private TextView tvCityName;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCityName = (TextView) itemView.findViewById(R.id.cityName);

        }
    }

}

