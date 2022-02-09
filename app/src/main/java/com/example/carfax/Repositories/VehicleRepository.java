package com.example.carfax.Repositories;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.carfax.Interfaces.IVehicleDao;
import com.example.carfax.Interfaces.IVehicleService;
import com.example.carfax.Models.VehicleListing;
import com.example.carfax.Models.VehicleResponse;
import com.example.carfax.Services.RetrofitClient;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@Singleton
public class VehicleRepository {

    IVehicleDao vehicleDao;
    androidx.lifecycle.LiveData<List<VehicleListing>> vehicleListings = new androidx.lifecycle.MutableLiveData<>();
    List<VehicleListing> list;
    IVehicleService vehicleService;
    Executor executor;

    @Inject
    public VehicleRepository(IVehicleService vehicleService, IVehicleDao vehicleDao, Executor executor) {
        this.vehicleService = vehicleService;
        this.vehicleDao = vehicleDao;
        this.executor = executor;
    }

    public LiveData<List<VehicleListing>> getVehicleListings() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (vehicleDao.count() == 0) {
                    getVehicleListingsFromService();
                } else {
                    list = vehicleDao.getAllVehicles();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ((androidx.lifecycle.MutableLiveData<List<VehicleListing>>) vehicleListings).setValue(list);
                        }
                    });
                }
            }
        });

        return vehicleListings;
    }

    private void getVehicleListingsFromService() {
        vehicleService = RetrofitClient.getClient().create(IVehicleService.class);
        vehicleService.getVehicleDetails().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VehicleResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage().contains("Unable to resolve host")) {
                            Log.d("404", "Internet not available");
                            //Internet Connection is not available.
                        } else {
                            //Unknown error occurred.
                            Log.d("401", "Unknown error occurred");
                        }
                    }

                    @Override
                    public void onNext(VehicleResponse vehicleResponse) {
                        ((MutableLiveData<List<VehicleListing>>) vehicleListings).setValue(vehicleResponse.getVehicleListings());

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                for (VehicleListing listing : vehicleListings.getValue()) {
                                    vehicleDao.insert(listing);
                                    Log.d("101", "Data Successfully Inserted");
                                }
                            }
                        });
                    }
                });
    }
}
