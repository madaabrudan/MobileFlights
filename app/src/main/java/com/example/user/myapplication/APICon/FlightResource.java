package com.example.user.myapplication.APICon;

import com.example.user.myapplication.flight.Flight;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.GET;


public interface FlightResource {
    String BASE_URL = AppResource.BASE_URL;
    @POST("flights")
    Call<Flight> addFlight(@Body Flight flight, @Header("Authorization") String token);
    @GET("flights")
    Call<List<Flight>> getFlights();
    @GET("flights/flightsPaginated")
    Call<List<Flight>> getFlightsPaginated(@Query("start") Integer start, @Query("howMany") Integer howMany, @Header("Authorization") String token);
}
