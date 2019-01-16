package com.example.user.myapplication.flight;

import java.util.List;

public class FlightRepository {
    private final FlightDAO flightDAO;
    private static FlightRepository instance;

    private FlightRepository(FlightDAO flightDAO)
    {
        this.flightDAO = flightDAO;
    }

    public static FlightRepository getInstance(FlightDAO flightDAO)
    {
        if(instance == null)
        {
            instance = new FlightRepository(flightDAO);
        }
        return instance;
    }
    public void updateFlight(Integer number, Boolean added){
        flightDAO.updateFlight(number,added);
    }
    public List<Flight> getAllFlights(){
        return flightDAO.getAllFlights();
    }


    public void addFlight(Flight flight)
    {
        flightDAO.insert(flight);
    }

    public List<Flight> getFlights(int howMany, int start){
        return flightDAO.getFlights(howMany,start);
    }
}
