package com.example.carfax.Presenters;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.carfax.Interfaces.DaggerVehicleComponent;
import com.example.carfax.Interfaces.VehicleComponent;
import com.example.carfax.Models.VehicleListing;
import com.example.carfax.Modules.VehicleModule;
import com.example.carfax.R;
import com.example.carfax.ViewModels.VehicleListingViewModel;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView vehicleDetailImageView;
    TextView yearMakeModelTrimTextView, priceTextView, milageTextView, locationTextView, exteriorColorTextView, interiorColorTextView;
    TextView driveTypeTextView, transmissionTextView, bodyStyleTextView, engineTextView, fuelTextView;
    Button callDealerButton;
    int position = 0;
    static final Integer CALL = 0x2;
    VehicleListingViewModel vehicleListingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initializeViews();

        position = getIntent().getExtras().getInt("position");

        VehicleComponent vehicleComponent = DaggerVehicleComponent.builder().vehicleModule(new VehicleModule(this)).build();
        vehicleListingViewModel = vehicleComponent.getVehicleListingViewModel();

        final VehicleListing vehicleListing = vehicleListingViewModel.getVehicleListingAtPosition(position);
        if (vehicleListing != null) {
            updateViews(vehicleListing);
            callDealerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InitiateCall(vehicleListing.getVehicleDealer().getPhone());
                }
            });
        } else {
            showAlertAndExit();
        }
    }

    private void showAlertAndExit() {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Error occured while fetching listing")
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void initializeViews() {
        vehicleDetailImageView = (ImageView) findViewById(R.id.vehicle_detail_image_view);
        yearMakeModelTrimTextView = (TextView) findViewById(R.id.vehicle_detail_year_make_model_trim);
        priceTextView = (TextView) findViewById(R.id.vehicle_detail_price);
        milageTextView = (TextView) findViewById(R.id.vehicle_detail_milage);
        locationTextView = (TextView) findViewById(R.id.vehicle_detail_location_value);
        exteriorColorTextView = (TextView) findViewById(R.id.vehicle_detail_exterior_color_value);
        interiorColorTextView = (TextView) findViewById(R.id.vehicle_detail_interior_color_value);
        driveTypeTextView = (TextView) findViewById(R.id.vehicle_detail_drive_type_value);
        transmissionTextView = (TextView) findViewById(R.id.vehicle_detail_transmission_value);
        bodyStyleTextView = (TextView) findViewById(R.id.vehicle_detail_body_style_value);
        engineTextView = (TextView) findViewById(R.id.vehicle_detail_engine_value);
        fuelTextView = (TextView) findViewById(R.id.vehicle_detail_fuel_value);
        callDealerButton = (Button) findViewById(R.id.vehicle_detail_call_dealer_button);
    }

    private void updateViews(VehicleListing vehicleListing) {
        String imageUrl = "https://www.carfax.com/uclassets/images/vdp-noimage.png";
        if (vehicleListing.getImages() != null) {
            imageUrl = vehicleListing.getImages().getVehicleFirstPhoto().getLarge();
        }
        Picasso.get().load(imageUrl).into(vehicleDetailImageView);

        String yearMakeModelTrim = vehicleListing.getYear() + " " + vehicleListing.getMake() + " " +
                vehicleListing.getModel() + " " + ((vehicleListing.getTrim().equals("Unspecified")) ? "" : vehicleListing.getTrim());
        yearMakeModelTrimTextView.setText(yearMakeModelTrim);
        priceTextView.setText("$ " + vehicleListing.getCurrentPrice().toString());
        milageTextView.setText(vehicleListing.getMileage().toString() + " mi");
        locationTextView.setText(vehicleListing.getVehicleDealer().getCity() +
                ", " + vehicleListing.getVehicleDealer().getState());
        exteriorColorTextView.setText(vehicleListing.getExteriorColor());
        interiorColorTextView.setText(vehicleListing.getInteriorColor());
        driveTypeTextView.setText(vehicleListing.getDrivetype());
        transmissionTextView.setText(vehicleListing.getTransmission());
        bodyStyleTextView.setText(vehicleListing.getBodytype());
        engineTextView.setText(vehicleListing.getEngine());
        fuelTextView.setText(vehicleListing.getFuel());
    }

    public void InitiateCall(String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //Permission Not granted Request Permission
            ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL);
        } else {
            //Permission has already been granted
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            if (callIntent.resolveActivity(this.getPackageManager()) != null) {
                this.startActivity(callIntent);
            }
        }
    }
}