package com.example.user.myapplication.flight;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FlightDAO {
    @Insert
    void insert(Flight flight);

    @Query("SELECT * FROM flights LIMIT:howMany OFFSET:start")
    List<Flight> getFlights(int howMany, int start);

    @Query("SELECT * FROM flights")
    List<Flight> getAllFlights();

    @Query("UPDATE flights SET added=:added WHERE flights.number=:number")
    void updateFlight(Integer number, Boolean added);
}
