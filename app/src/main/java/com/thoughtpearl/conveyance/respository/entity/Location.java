package com.thoughtpearl.conveyance.respository.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "location", foreignKeys = {
        @ForeignKey(entity = TripRecord.class, parentColumns = "id", childColumns = "tripId", onDelete = CASCADE)
})
public class Location {
    @PrimaryKey
    @ColumnInfo(name = "locationId")
    @NonNull
    public UUID locationId;
    public Double latitude = 0.0;
    public Double longitude = 0.0;
    public boolean serverSync = false;
    public String timestamp;
    public UUID tripId;

    public Location() {
    }

    public Location(UUID locationId, Double latitude, Double longitude, boolean sync, UUID tripId, String timestamp) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.serverSync = sync;
        this.tripId = tripId;
        this.timestamp = timestamp;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isServerSync() {
        return serverSync;
    }

    public void setServerSync(boolean serverSync) {
        this.serverSync = serverSync;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }
}

/*
@Embedded
@ColumnInfo(name = "session_location")
    var sessionLocation: Location*/
