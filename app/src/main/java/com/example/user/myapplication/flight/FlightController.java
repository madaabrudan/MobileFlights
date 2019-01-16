package com.example.user.myapplication.flight;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;


public class FlightController extends ViewModel{
    private FlightRepository RepoFlight;
    public FlightController(Context context) {
        RepoFlight = FlightRepository.getInstance(FlightDatabase.getAppDatabase(context).flightDAO());
    }
    public void createFlight(Flight flight) {
        RepoFlight.addFlight(flight);
    }

    public List<Flight> getFlights(int howMany, int start){
        return RepoFlight.getFlights(howMany, start);
    }
    public List<Flight> getAllFlights(){
        return RepoFlight.getAllFlights();
    }

    public void updateFlight(Integer number, Boolean added){
        RepoFlight.updateFlight(number,added);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final Context cont;

        public Factory(Context cont) {
            this.cont = cont.getApplicationContext();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FlightController(cont);
        }
    }
}
