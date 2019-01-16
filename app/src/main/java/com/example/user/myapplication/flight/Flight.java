package com.example.user.myapplication.flight;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static com.example.user.myapplication.flight.Flight.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class Flight {
    public static final String TABLE_NAME = "flights";

    @PrimaryKey
    @NonNull
    Integer number;
    String route;
    Boolean added;

    public Flight(Integer number, String route, Boolean added)
    {
        this.number = number;
        this.route = route;
        this.added=added;
    }

    public Integer getFlightNumber() {
        return number;
    }

    public void setFlightNumber(Integer number)
    {
        this.number = number;
    }

    public String getRoute() {

        return route;
    }

    public void setRoute(String route)
    {
        this.route = route;
    }

    public Boolean getAdded()
    {
        return added;
    }

    @Override
    public String toString(){

        return this.number.toString()+" "+this.route;
    }
}
