package com.example.user.myapplication.Model;

import com.example.user.myapplication.APICon.FlightResource;
import com.example.user.myapplication.flight.Flight;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModelViewFlights {
    public Call<List<Flight>> getFlights()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(FlightResource.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        FlightResource api = retrofit.create(FlightResource.class);
        Call<List<Flight>> call = api.getFlights();
        return call;
    }

    public Call<List<Flight>> getFlightsPaginated(Integer start, Integer howMany, String token){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(FlightResource.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        FlightResource api = retrofit.create(FlightResource.class);
        Call<List<Flight>> call = api.getFlightsPaginated(start,howMany,token);
        return call;
    }

    public Call<Flight> addFlight(Flight flight, String token){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(FlightResource.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        FlightResource api = retrofit.create(FlightResource.class);
        Call<Flight> call = api.addFlight(flight,token);
        return call;
    }
}
