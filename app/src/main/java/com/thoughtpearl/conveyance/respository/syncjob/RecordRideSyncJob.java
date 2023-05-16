package com.thoughtpearl.conveyance.respository.syncjob;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.thoughtpearl.conveyance.LocationApp;
import com.thoughtpearl.conveyance.api.ApiHandler;
import com.thoughtpearl.conveyance.respository.databaseclient.DatabaseClient;
import com.thoughtpearl.conveyance.respository.entity.Location;
import com.thoughtpearl.conveyance.respository.executers.AppExecutors;
import com.thoughtpearl.conveyance.utility.TrackerUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//class DatabaseServerSyncJob for handling task
public class RecordRideSyncJob extends TimerTask {
    Context context;
    int finishTaskCount = 0;
    DatabaseClient databaseClient;
    boolean isCompleted = false;
    boolean isSuccessful = false;
    boolean isRideRunning;
    public RecordRideSyncJob(Context context, boolean isRideRunning) {
        this.context = context;
        this.isRideRunning = isRideRunning;
        databaseClient = DatabaseClient.getInstance(context);
    }

    @Override
    public void run() {
        // run on another thread
        Log.d("TRIP", "Timer job is running every one minute : " + TrackerUtility.getTimeString(new Date()));
        if (TrackerUtility.checkConnection(this.context)) {
            AppExecutors.getInstance().getMainThread().execute(() -> {
                // display toast
                AtomicReference<List<Location>> unSyncList = new AtomicReference<>(new ArrayList<>());

                AppExecutors.getInstance().getDiskIO().execute(() -> {
                    unSyncList.set(databaseClient.getTripDatabase().tripRecordDao().getUnSyncServerLocations());
                    if (unSyncList.get().size() > 0) {
                        isCompleted = false;
                        updateLocationsOnServer(unSyncList.get());
                        Log.d("TRIP", "UPDATING RECORDS..");
                        finishTaskCount = 0;
                    } else {
                        if (finishTaskCount++ >= 5 || !isRideRunning) {
                            isCompleted = true;
                            cancel();
                        }
                    }
                });
            });
        } else {
            showToastMessage("Please check your network connection");
        }
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void updateLocationsOnServer(List<com.thoughtpearl.conveyance.respository.entity.Location> unSyncedLocations) {
        updateLocationsOnServer(unSyncedLocations, 0);
    }
    public void updateLocationsOnServer(List<com.thoughtpearl.conveyance.respository.entity.Location> unSyncedLocations, int retryAttemptCount) {
        if (!TrackerUtility.checkConnection(this.context)) {
            showToastMessage("Please check your network connection");
        } else {
            ArrayList<com.thoughtpearl.conveyance.api.response.LocationRequest> locationRequests = new ArrayList<>();
            unSyncedLocations.forEach(location -> {
                com.thoughtpearl.conveyance.api.response.LocationRequest request = new com.thoughtpearl.conveyance.api.response.LocationRequest("", location.getLatitude(), location.getLongitude(), location.getTripId().toString(), location.getTimestamp());
                locationRequests.add(request);
            });

            Call<List<String>> createLocationCall = ApiHandler.getClient().createLocation(LocationApp.getUserName(this.context), LocationApp.DEVICE_ID, locationRequests);
            createLocationCall.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    if (response.code() == 201) {
                        AppExecutors.getInstance().getDiskIO().execute(() -> {
                            unSyncedLocations.forEach(location -> {
                                location.serverSync = true;
                                databaseClient.getTripDatabase().tripRecordDao().updateLocationById(location.isServerSync()? 1 : 0, location.getLocationId());
                            });
                        });
                        isSuccessful = true;
                        isCompleted = true;
                    } else {
                        if (retryAttemptCount < 1) {
                            updateLocationsOnServer(unSyncedLocations, 1);
                        }
                        isCompleted = true;
                        showToastMessage("Ride data sync failed :" + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    if (retryAttemptCount < 1) {
                        updateLocationsOnServer(unSyncedLocations, 1);
                    }
                    showToastMessage("Ride data sync failed.");
                }
            });
        }
    }

    public void showToastMessage(String message) {
        if (!LocationApp.isAppInBackground()) {
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast toast = Toast.makeText(this.context, message, Toast.LENGTH_LONG);
                toast.show();
            });
        }
    }
}