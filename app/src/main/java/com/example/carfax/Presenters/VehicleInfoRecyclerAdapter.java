package com.example.carfax.Presenters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carfax.Models.VehicleListing;
import com.example.carfax.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VehicleInfoRecyclerAdapter extends RecyclerView.Adapter<VehicleInfoRecyclerViewHolder> {

    Context context;
    List<VehicleListing> vehicleListings;

    public VehicleInfoRecyclerAdapter(List<VehicleListing> vehicleListing, Context context) {
        this.vehicleListings = vehicleListing;
        this.context = context;
    }

    @androidx.annotation.NonNull
    @Override
    public VehicleInfoRecyclerViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_card_layout, parent, false);
        VehicleInfoRecyclerViewHolder vehicleInfoRecyclerViewHolder = new VehicleInfoRecyclerViewHolder(view);
        return vehicleInfoRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VehicleInfoRecyclerViewHolder holder, final int position) {
        final VehicleListing vehicleInfoList = vehicleListings.get(position);

        updateViews(holder, vehicleInfoList);

        holder.vehicleInfoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        holder.vehicleInfoCallDealerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = vehicleInfoList.getVehicleDealer().getPhone();
                ((HomeActivity) context).InitiateCall(number);
            }
        });
    }

    private void updateViews(VehicleInfoRecyclerViewHolder holder, VehicleListing vehicleInfoList) {
        String yearMakeModelTrim = vehicleInfoList.getYear() + " " + vehicleInfoList.getMake() + " " + vehicleInfoList.getModel() + " " + ((vehicleInfoList.getTrim().equals("Unspecified")) ? "" : vehicleInfoList.getTrim());
        holder.vehicleInfoYearMakeModelTrimTextView.setText(yearMakeModelTrim);

        String imageUrl = "https://www.carfax.com/uclassets/images/vdp-noimage.png";
        if (vehicleInfoList.getImages() != null) {
            imageUrl = vehicleInfoList.getImages().getVehicleFirstPhoto().getLarge();
        }

        Picasso.get().load(imageUrl).into(holder.vehicleInfoImageView);
        holder.vehicleInfoPriceTextView.setText("$ " + vehicleInfoList.getCurrentPrice());
        holder.vehicleInfoMilageTextView.setText(vehicleInfoList.getMileage().toString() + " mi");
        holder.vehicleInfoLocationTextView.setText(vehicleInfoList.getVehicleDealer().getCity() + ", " + vehicleInfoList.getVehicleDealer().getState());
    }

    @Override
    public int getItemCount() {
        return vehicleListings.size();
    }
}
