package com.example.carfax.Interfaces;

import com.example.carfax.Modules.VehicleModule;
import com.example.carfax.Presenters.HomeActivity;
import com.example.carfax.ViewModels.VehicleListingViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {VehicleModule.class})

public interface VehicleComponent {
    void inject(HomeActivity homeActivity);

    VehicleListingViewModel getVehicleListingViewModel();
}
