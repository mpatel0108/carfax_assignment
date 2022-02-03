package com.example.carfax.Services;

import com.example.carfax.Models.VehicleResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VehicleService {
    @GET("/assignment.json")
    Call<VehicleResponse> getVehicleDetails();
}
