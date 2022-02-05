package com.example.carfax.Models;

import androidx.room.Embedded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleImage {

    @SerializedName("firstPhoto")
    @Expose
    @Embedded
    private VehicleFirstPhoto vehicleFirstPhoto;

    public VehicleFirstPhoto getVehicleFirstPhoto() {
        return vehicleFirstPhoto;
    }

    public void setVehicleFirstPhoto(VehicleFirstPhoto vehicleFirstPhoto) {
        this.vehicleFirstPhoto = vehicleFirstPhoto;
    }
}
