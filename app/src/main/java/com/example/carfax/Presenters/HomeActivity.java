package com.example.carfax.Presenters;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carfax.Models.VehicleListing;
import com.example.carfax.Models.VehicleResponse;
import com.example.carfax.R;
import com.example.carfax.Services.RetrofitClient;
import com.example.carfax.Services.VehicleService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    RecyclerView vehicleInfoRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<VehicleListing> vehicleListing = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        vehicleInfoRecyclerView = (RecyclerView) findViewById(R.id.vehicle_info_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        vehicleInfoRecyclerView.setLayoutManager(layoutManager);
        vehicleInfoRecyclerView.setHasFixedSize(true);

        VehicleService vehicleService = RetrofitClient.getClient().create(VehicleService.class);
        vehicleService.getVehicleDetails().enqueue(new Callback<VehicleResponse>() {
            @Override
            public void onResponse(Call<VehicleResponse> call, Response<VehicleResponse> response) {
                vehicleListing = response.body().getVehicleListings();

                adapter = new VehicleInfoRecyclerAdapter(vehicleListing, HomeActivity.this);
                vehicleInfoRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<VehicleResponse> call, Throwable t) {

            }
        });



    }
}