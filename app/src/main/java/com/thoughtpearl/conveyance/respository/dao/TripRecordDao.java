package com.thoughtpearl.conveyance.respository.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.thoughtpearl.conveyance.respository.entity.Location;
import com.thoughtpearl.conveyance.respository.entity.TripRecord;
import com.thoughtpearl.conveyance.respository.entity.TripRecordLocationRelation;

import java.util.List;
import java.util.UUID;

@Dao
public interface  TripRecordDao {

    @Query("SELECT * FROM TripRecord order by start_time desc")
    List<TripRecord> getAllTripRecord();

    @Transaction
    @Query("SELECT * FROM TripRecord order by start_time desc")
    List<TripRecordLocationRelation> getAllRides();

    @Query("SELECT distinct(id) FROM TripRecord")
    UUID[] getTotalTrips();

    @Query("SELECT * FROM TripRecord WHERE id IN (:tripIds)")
    List<TripRecord> getTripByIds(UUID[] tripIds);

    @Query("SELECT * FROM TripRecord WHERE id IN (:tripId)")
    TripRecord getTripById(UUID tripId);

    @Query("SELECT * FROM TripRecord order by start_time desc limit 1")
    TripRecord getRunningTrip();

    @Query("Select id From TripRecord order by start_time desc limit 1")
    UUID getLastTripId();

    @Transaction
    @Query("SELECT * FROM TripRecord WHERE id = :tripId")
    TripRecordLocationRelation getByTripId(UUID tripId);

    @Query("Update TripRecord Set end_time =:endTime Where id =:id")
    int updateRecord(UUID id, long endTime);

    @Update
    int updateRecord(TripRecord tripRecord);

    @Insert
    void insertAll(TripRecord... TripRecord);

    @Insert
    Long save(Location location);

    @Update
    int update(Location location);

    @Insert
    Long save(TripRecord tripRecord);

    //SELECT id FROM table_name WHERE rowid = :rowId

    @Query("Select id From TripRecord WHERE rowid =:rowId")
    UUID getLastInsertedTripId(Long rowId);

    @Insert
    void insertAll(Location... locations);

    @Delete
    void delete(TripRecord trip);

    @Query("Delete From TripRecord where id =:tripId")
    void deleteById(UUID tripId);

    @Query("Delete From TripRecord")
    void deleteAllTrips();

    @Query("Delete From location")
    void deleteAllLocation();

    @Query("SELECT * FROM location WHERE tripId IN (:tripId) AND serverSync=0")
    List<Location> getUnSyncServerLocations(String tripId);

    @Query("SELECT * FROM location WHERE serverSync=0")
    List<Location> getUnSyncServerLocations();

    @Query("SELECT * FROM location WHERE tripId IN (:tripId)")
    List<Location> getLocations(String tripId);

}
