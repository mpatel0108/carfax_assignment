package com.example.carfax.Interfaces;

import com.example.carfax.Models.VehicleResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface IVehicleService {
    @GET("/assignment.json")
    Observable<VehicleResponse> getVehicleDetails();
}