package com.example.carfax.Modules;

import android.content.Context;

import androidx.room.Room;

import com.example.carfax.Database.VehicleRoomDatabase;
import com.example.carfax.Interfaces.IVehicleDao;
import com.example.carfax.Interfaces.IVehicleService;
import com.example.carfax.Services.VehicleService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class VehicleModule {
    private Context context;

    public VehicleModule(Context context) {
        this.context = context;
    }

    @Provides
    public IVehicleService provideVehicleService(VehicleService vehicleService) {
        return vehicleService;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public VehicleRoomDatabase provideMyDatabase(Context context) {
        return Room.databaseBuilder(context, VehicleRoomDatabase.class, "vehicle_room").build();
    }

    @Provides
    public Executor provideExecutors() {
        return Executors.newSingleThreadExecutor();
    }

    @Singleton
    @Provides
    public IVehicleDao provideUserDao(VehicleRoomDatabase myDatabase) {
        return myDatabase.vehicleDao();
    }
}