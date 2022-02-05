package com.example.carfax.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.carfax.Models.VehicleListing;
import com.example.carfax.Repositories.VehicleRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VehicleListingViewModel extends ViewModel {

    private VehicleRepository vehicleRepository;
    private static androidx.lifecycle.LiveData<List<VehicleListing>> vehicleListings;

    @Inject
    public VehicleListingViewModel(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public LiveData<List<VehicleListing>> getVehicleListings() {
        vehicleListings = vehicleRepository.getVehicleListings();
        return vehicleListings;
    }

    public VehicleListing getVehicleListingAtPosition(int position) {
        if (vehicleListings != null || vehicleListings.getValue().get(position) != null) {
            return vehicleListings.getValue().get(position);
        }
        return null;
    }
}