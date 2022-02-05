package com.example.carfax.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.carfax.Interfaces.IVehicleDao;
import com.example.carfax.Models.VehicleListing;

@Database(entities = {VehicleListing.class}, version = 1, exportSchema = false)
public abstract class VehicleRoomDatabase extends RoomDatabase {
    public abstract IVehicleDao vehicleDao();
}
