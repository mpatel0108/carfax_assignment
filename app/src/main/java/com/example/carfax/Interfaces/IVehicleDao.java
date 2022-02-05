package com.example.carfax.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carfax.Models.VehicleListing;

import java.util.List;

import javax.inject.Singleton;

@Dao
@Singleton
public interface IVehicleDao {

    @Insert
    void insert(VehicleListing vehicleListing);

    @Query("Select * from vehicle_details_table")
    List<VehicleListing> getAllVehicles();

    @Query("Select * from vehicle_details_table where vin = :vinNumber")
    VehicleListing getOneVehicle(String vinNumber);

    @Query("SELECT COUNT(*) from vehicle_details_table")
    int count();

}
