package com.thoughtpearl.conveyance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.thoughtpearl.conveyance.api.ApiHandler;
import com.thoughtpearl.conveyance.api.response.LocationRequest;
import com.thoughtpearl.conveyance.respository.databaseclient.DatabaseClient;
import com.thoughtpearl.conveyance.respository.entity.TripRecord;
import com.thoughtpearl.conveyance.respository.executers.AppExecutors;
import com.thoughtpearl.conveyance.ui.statistics.StatisticsFragment;
import com.thoughtpearl.conveyance.utility.TrackerUtility;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView rideAmountTextView;
    private TextView rideDateTextView;
    private TextView rideDurationTextView;
    private TextView rideDistanceTextView;
    private TextView ridePurposeTextView;
    private Button completeRideButton;
    private GoogleMap mMap;
    private MapView mapView;
    private RideDetailsResponse rideDetailsResponse;
    private String rideId;
    private Boolean isInCompleteRide;
    private Boolean isFromStatisticScreen;
    private static final int REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);

        if (!checkPermissions()) {
            requestPermissions();
        }

        rideId = getIntent().getExtras().getString("rideId", "");
        isInCompleteRide = getIntent().getExtras().getBoolean("isInCompleteRide", false);
        isFromStatisticScreen = getIntent().getExtras().getBoolean("isFromStatisticScreen", false);
        rideAmountTextView = findViewById(R.id.rideAmount);
        rideAmountTextView.setText("");
        rideDateTextView = findViewById(R.id.rideDate);
        rideDateTextView.setText("");
        rideDurationTextView = findViewById(R.id.rideDuration);
        rideDurationTextView.setText("");
        rideDistanceTextView = findViewById(R.id.rideDistance);
        rideDistanceTextView.setText("");
        ridePurposeTextView = findViewById(R.id.ridePurpose);
        ridePurposeTextView.setText("");
        completeRideButton = findViewById(R.id.completeRide);
        completeRideButton.setVisibility(View.GONE);
        if (isInCompleteRide) {
            completeRideButton.setOnClickListener(view -> {
                if (mMap != null) {
                    mMap.snapshot(bitmap -> {
                        File screenshot = TrackerUtility.takeScreen(getApplicationContext(), mapView, bitmap);
                        if (!TrackerUtility.checkConnection(getApplicationContext())) {
                            Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
                        } else {
                            updateInCompleteRide(screenshot.getAbsolutePath());
                        }
                    });
                }
            });
        }
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mapView.setEnabled(true);
        initToolbar();
    }

    private void fetchRideDetails() {
       Dialog dialog = LocationApp.showLoader(RideDetailsActivity.this);
        AppExecutors.getInstance().getDiskIO().execute(()-> {
           Call<RideDetailsResponse> rideCall = ApiHandler.getClient().getRideDetails(LocationApp.getUserName(this), LocationApp.DEVICE_ID, rideId);
           rideCall.enqueue(new Callback<RideDetailsResponse>() {
               @Override
               public void onResponse(Call<RideDetailsResponse> call, Response<RideDetailsResponse> response) {
                   if (response.code() == 200) {
                       rideDetailsResponse = response.body();
                       String sStartDate = rideDetailsResponse.getRideDate()+ " " + rideDetailsResponse.getRideTime();
                       Date startDate = TrackerUtility.convertStringToDate(sStartDate);

                       rideDateTextView.setText(TrackerUtility.getDateString(startDate, "dd MMM yyyy"));
                       if (rideDetailsResponse.getReimbursementAmount() != null) {
                           rideAmountTextView.setText("Rs " + String.valueOf(rideDetailsResponse.getReimbursementAmount()));
                       } else {
                           rideAmountTextView.setText("Waiting");
                       }
                       ridePurposeTextView.setText(rideDetailsResponse.getPurpose());
                       if (rideDetailsResponse.getDistanceTravelled() != null) {
                           rideDistanceTextView.setText(TrackerUtility.roundOffDoubleToString(rideDetailsResponse.getDistanceTravelled()) + " Km");
                       } else {
                           rideDistanceTextView.setText("0 Km");
                       }
                       rideDurationTextView.setText(rideDetailsResponse.getTotalTime());
                       /*if (isListEmptyOrNull(rideDetailsResponse.getRideLocationDTOList())) {
                           completeRideButton.setVisibility(View.GONE);
                       } else {*/
                       if (isInCompleteRide) {
                           completeRideButton.setVisibility(View.VISIBLE);
                       }
                       //}
                       reDrawTravelPathOnMap(rideDetailsResponse.getRideLocationDTOList());
                       zoomToSeeWholeTrack(rideDetailsResponse.getRideLocationDTOList());

                   } else {
                       Toast.makeText(RideDetailsActivity.this, "Failed to fetch ride details please try after sometime.", Toast.LENGTH_SHORT).show();
                   }
                   if (dialog != null && dialog.isShowing()) {
                       dialog.dismiss();
                   }
               }

               @Override
               public void onFailure(Call<RideDetailsResponse> call, Throwable t) {
                   Toast.makeText(RideDetailsActivity.this, "Failed to fetch ride details please try after sometime.", Toast.LENGTH_SHORT).show();
                   if (dialog != null && dialog.isShowing()) {
                       dialog.dismiss();
                   }
               }
           });
        });
    }

    @SuppressLint("RestrictedApi")
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            this.finishActivity(RESULT_OK);
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        if (rideDetailsResponse != null) {
            reDrawTravelPathOnMap(rideDetailsResponse.getRideLocationDTOList());
            zoomToSeeWholeTrack(rideDetailsResponse.getRideLocationDTOList());
        }
    }

    private void zoomToSeeWholeTrack(List<LocationRequest> locationList) {
        if (isListEmptyOrNull(locationList)) {
            return;
        }
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (LocationRequest locationRequest : locationList) {
            builder.include(new LatLng(locationRequest.getLatitude(), locationRequest.getLongitude()));
        }
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = (int) (getResources().getDisplayMetrics().heightPixels * 0.70f);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), widthPixels, heightPixels, (int) (heightPixels * 0.05f)));
    }

    public void reDrawTravelPathOnMap(List<LocationRequest> locationList) {
        if (isListEmptyOrNull(locationList)) {
            return;
        }
        PolylineOptions polylineOptions = new PolylineOptions();
        LatLng dest = null;
        if (locationList != null && locationList.size() > 0) {
            LatLng start = new LatLng(locationList.get(0).getLatitude(), locationList.get(0).getLongitude());
            for (LocationRequest locationRequest : locationList) {
                dest = new LatLng(locationRequest.getLatitude(), locationRequest.getLongitude());
                polylineOptions.add(dest);
            }

            MarkerOptions startMarkerOptions = new MarkerOptions();
            startMarkerOptions.title("Start point");
            startMarkerOptions.position(start);
            mMap.addMarker(startMarkerOptions);

            MarkerOptions endMarkerOptions = new MarkerOptions();
            endMarkerOptions.title("End point");
            endMarkerOptions.position(dest);
            mMap.addMarker(endMarkerOptions);
        }

        polylineOptions.color(LocationApp.POLYLINE_COLOR);
        polylineOptions.width(LocationApp.POLYLINE_WIDTH);
        polylineOptions.geodesic(true);
        mMap.addPolyline(polylineOptions);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        if (dest != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dest, LocationApp.ZOOM_LEVEL));
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (!TrackerUtility.checkConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
        } else {
            fetchRideDetails();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            //fetchRideDetails();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    public void updateInCompleteRide(String imagePath) {
        Dialog dialog = LocationApp.showLoader(this);
        AppExecutors.getInstance().getDiskIO().execute(()->{
            TripRecord tripRecord = new TripRecord();
            tripRecord.setTripId(UUID.fromString(rideId));
            if (rideDetailsResponse != null) {

                File file = new File(imagePath);
                Log.d("TRIP", "image path :" + imagePath + "file exists :" + file.exists());
                float totalDistance = 0;
                float distanceInKm = 0;
                String sDate = rideDetailsResponse.getRideDate();
                String endTime;
                if (!isListEmptyOrNull(rideDetailsResponse.getRideLocationDTOList())) {
                    totalDistance = TrackerUtility.calculateDistanceInMeter(rideDetailsResponse.getRideLocationDTOList());
                    distanceInKm = totalDistance / 1000f;
                    LocationRequest locationRequest = rideDetailsResponse.getRideLocationDTOList().get(rideDetailsResponse.getRideLocationDTOList().size() - 1);
                    sDate = sDate + " " + locationRequest.getTimeStamp();
                    endTime = locationRequest.getTimeStamp();
                } else {
                    sDate = sDate + " 00:00:00";
                    endTime = rideDetailsResponse.getRideTime();
                }
                Date date = TrackerUtility.convertStringToDate(sDate , "yyyy-MM-dd HH:mm:ss");
                tripRecord.setTotalDistance(distanceInKm);
                tripRecord.setEndTimestamp(date.getTime());
                tripRecord.setStatus(true);

                RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                RequestBody id = RequestBody.create(MediaType.parse("text/plain"), rideId);
                //RequestBody ridePurpose = RequestBody.create(MediaType.parse("text/plain"), rideDetailsResponse.getPurpose());
                RequestBody rideStartTime = RequestBody.create(MediaType.parse("text/plain"), rideDetailsResponse.getRideTime());
                RequestBody rideEndTime = RequestBody.create(MediaType.parse("text/plain"), endTime);
                RequestBody rideDate = RequestBody.create(MediaType.parse("text/plain"), rideDetailsResponse.getRideDate());
                RequestBody rideDistance = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(distanceInKm));

                Map<String, RequestBody> bodyMap = new HashMap<>();
                bodyMap.put("file\"; filename=\"pp.png\" ", fileBody);
                bodyMap.put("id", id);
                bodyMap.put("rideDate", rideDate);
                bodyMap.put("rideStartTime", rideStartTime);
                bodyMap.put("rideEndTime", rideEndTime);
                bodyMap.put("rideDistance", rideDistance);
                //bodyMap.put("ridePurpose", ridePurpose);

                Call<Void> updateRideCall = ApiHandler.getClient().updateRide(LocationApp.getUserName(this), LocationApp.DEVICE_ID, rideId, bodyMap);
                updateRideCall.enqueue(new Callback<Void>(){

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("TRIP", "Ride completed :");
                        if (response.code() == 200 || response.code() == 201) {
                            //tripRecord.setStatus(true);
                            StatisticsFragment.isRideListRefreshRequired = isFromStatisticScreen;
                            AppExecutors.getInstance().getDiskIO().execute(() -> {
                                DatabaseClient.getInstance(getApplicationContext()).getTripDatabase().tripRecordDao().updateRecord(tripRecord);
                            });
                            isInCompleteRide = false;
                            completeRideButton.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Ride Updated Successfully", Toast.LENGTH_LONG).show();
                            fetchRideDetails();
                        } else {
                            Toast.makeText(RideDetailsActivity.this, "Something went wrong while updating ride. Please try after some time", Toast.LENGTH_LONG).show();
                        }

                        AppExecutors.getInstance().getMainThread().execute(()->{
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("TRIP", "Ride not completed :" + t);
                        Toast.makeText(RideDetailsActivity.this, "D" +
                                "Something went wrong while updating ride. Please try after some time", Toast.LENGTH_LONG).show();
                        AppExecutors.getInstance().getMainThread().execute(()->{
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
        });
    }

    public boolean isListEmptyOrNull(List locations) {
        return locations == null || locations.size() == 0;
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int permissionStateWriteFile = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionStateFineLocation = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionStateCourseLocation = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            return ((permissionState == PackageManager.PERMISSION_GRANTED) &&
                    (permissionStateWriteFile == PackageManager.PERMISSION_GRANTED) &&
                    (permissionStateFineLocation == PackageManager.PERMISSION_GRANTED) &&
                    (permissionStateCourseLocation == PackageManager.PERMISSION_GRANTED));
        } else {
            return ((permissionState == PackageManager.PERMISSION_GRANTED) &&
                    (permissionStateFineLocation == PackageManager.PERMISSION_GRANTED) &&
                    (permissionStateCourseLocation == PackageManager.PERMISSION_GRANTED));
        }
    }

    private void requestPermissions() {
        boolean shouldProvideRationale = false;
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            shouldProvideRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            shouldProvideRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (shouldProvideRationale) {
            Log.i("TRIP", "Displaying permission rationale to provide additional context.");

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        } else {
            Log.i("TRIP", "Requesting permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("TRIP", "onRequestPermissionResult");
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i("TRIP", "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchRideDetails();
            } else {
                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, view -> {
                            // Build intent that displays the App settings screen.
                            Intent intent = new Intent();
                            intent.setAction(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    "com.thoughtpearl.conveyance", null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        });
            }
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                        getString(mainTextStringId),
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }
}