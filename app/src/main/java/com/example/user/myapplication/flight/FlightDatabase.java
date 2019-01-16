package com.example.user.myapplication.flight;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Flight.class}, version = 1)
public abstract class FlightDatabase extends RoomDatabase {

    public abstract FlightDAO flightDAO();
    public static FlightDatabase INSTANCE;

    public static FlightDatabase getAppDatabase(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FlightDatabase.class, "flight-database").allowMainThreadQueries().build();

        }
        return INSTANCE;
    }
    public static void destroyInstance()
    {
        INSTANCE = null;
    }

}