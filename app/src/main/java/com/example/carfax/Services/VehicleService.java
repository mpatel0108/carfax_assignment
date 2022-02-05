package com.example.carfax.Services;

import com.example.carfax.Interfaces.IVehicleService;
import com.example.carfax.Models.VehicleResponse;

import javax.inject.Inject;

import rx.Observable;

public class VehicleService implements IVehicleService {

    @Inject
    public VehicleService() {
    }

    @Override
    public Observable<VehicleResponse> getVehicleDetails() {
        return null;
    }
}
