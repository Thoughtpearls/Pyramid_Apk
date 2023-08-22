package com.thoughtpearl.conveyance.respository.dto;

public class UnSyncRideDto {
    String tripId;
    public UnSyncRideDto(String tripId) {
        this.tripId = tripId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
