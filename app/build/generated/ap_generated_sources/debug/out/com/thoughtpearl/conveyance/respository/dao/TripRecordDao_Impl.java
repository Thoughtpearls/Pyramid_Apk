package com.thoughtpearl.conveyance.respository.dao;

import android.database.Cursor;
import androidx.collection.ArrayMap;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.thoughtpearl.conveyance.respository.converter.UUIDConverter;
import com.thoughtpearl.conveyance.respository.dto.UnSyncRideDto;
import com.thoughtpearl.conveyance.respository.entity.Location;
import com.thoughtpearl.conveyance.respository.entity.TripRecord;
import com.thoughtpearl.conveyance.respository.entity.TripRecordLocationRelation;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TripRecordDao_Impl implements TripRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TripRecord> __insertionAdapterOfTripRecord;

  private final EntityInsertionAdapter<Location> __insertionAdapterOfLocation;

  private final EntityDeletionOrUpdateAdapter<TripRecord> __deletionAdapterOfTripRecord;

  private final EntityDeletionOrUpdateAdapter<TripRecord> __updateAdapterOfTripRecord;

  private final EntityDeletionOrUpdateAdapter<Location> __updateAdapterOfLocation;

  private final SharedSQLiteStatement __preparedStmtOfUpdateRecord;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLocationById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTrips;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllLocation;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllSyncedRides;

  public TripRecordDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTripRecord = new EntityInsertionAdapter<TripRecord>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `TripRecord` (`id`,`start_time`,`end_time`,`total_distance`,`status`,`deviceId`,`purposeId`,`reimbursementCost`,`sanctionDistance`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TripRecord value) {
        final String _tmp = UUIDConverter.fromUUID(value.tripId);
        if (_tmp == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, _tmp);
        }
        stmt.bindLong(2, value.startTimestamp);
        stmt.bindLong(3, value.endTimestamp);
        stmt.bindDouble(4, value.totalDistance);
        final int _tmp_1 = value.status ? 1 : 0;
        stmt.bindLong(5, _tmp_1);
        if (value.deviceId == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.deviceId);
        }
        if (value.ridePurposeId == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.ridePurposeId);
        }
        if (value.reimbursementCost == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.reimbursementCost);
        }
        if (value.sanctionDistance == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.sanctionDistance);
        }
      }
    };
    this.__insertionAdapterOfLocation = new EntityInsertionAdapter<Location>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `location` (`locationId`,`latitude`,`longitude`,`serverSync`,`timestamp`,`tripId`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Location value) {
        final String _tmp = UUIDConverter.fromUUID(value.locationId);
        if (_tmp == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, _tmp);
        }
        if (value.latitude == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindDouble(2, value.latitude);
        }
        if (value.longitude == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindDouble(3, value.longitude);
        }
        final int _tmp_1 = value.serverSync ? 1 : 0;
        stmt.bindLong(4, _tmp_1);
        if (value.timestamp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.timestamp);
        }
        final String _tmp_2 = UUIDConverter.fromUUID(value.tripId);
        if (_tmp_2 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_2);
        }
      }
    };
    this.__deletionAdapterOfTripRecord = new EntityDeletionOrUpdateAdapter<TripRecord>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `TripRecord` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TripRecord value) {
        final String _tmp = UUIDConverter.fromUUID(value.tripId);
        if (_tmp == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, _tmp);
        }
      }
    };
    this.__updateAdapterOfTripRecord = new EntityDeletionOrUpdateAdapter<TripRecord>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `TripRecord` SET `id` = ?,`start_time` = ?,`end_time` = ?,`total_distance` = ?,`status` = ?,`deviceId` = ?,`purposeId` = ?,`reimbursementCost` = ?,`sanctionDistance` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TripRecord value) {
        final String _tmp = UUIDConverter.fromUUID(value.tripId);
        if (_tmp == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, _tmp);
        }
        stmt.bindLong(2, value.startTimestamp);
        stmt.bindLong(3, value.endTimestamp);
        stmt.bindDouble(4, value.totalDistance);
        final int _tmp_1 = value.status ? 1 : 0;
        stmt.bindLong(5, _tmp_1);
        if (value.deviceId == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.deviceId);
        }
        if (value.ridePurposeId == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.ridePurposeId);
        }
        if (value.reimbursementCost == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.reimbursementCost);
        }
        if (value.sanctionDistance == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.sanctionDistance);
        }
        final String _tmp_2 = UUIDConverter.fromUUID(value.tripId);
        if (_tmp_2 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, _tmp_2);
        }
      }
    };
    this.__updateAdapterOfLocation = new EntityDeletionOrUpdateAdapter<Location>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `location` SET `locationId` = ?,`latitude` = ?,`longitude` = ?,`serverSync` = ?,`timestamp` = ?,`tripId` = ? WHERE `locationId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Location value) {
        final String _tmp = UUIDConverter.fromUUID(value.locationId);
        if (_tmp == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, _tmp);
        }
        if (value.latitude == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindDouble(2, value.latitude);
        }
        if (value.longitude == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindDouble(3, value.longitude);
        }
        final int _tmp_1 = value.serverSync ? 1 : 0;
        stmt.bindLong(4, _tmp_1);
        if (value.timestamp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.timestamp);
        }
        final String _tmp_2 = UUIDConverter.fromUUID(value.tripId);
        if (_tmp_2 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_2);
        }
        final String _tmp_3 = UUIDConverter.fromUUID(value.locationId);
        if (_tmp_3 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_3);
        }
      }
    };
    this.__preparedStmtOfUpdateRecord = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Update TripRecord Set end_time =? Where id =?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLocationById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE location SET serversync=? WHERE locationid LIKE ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete From TripRecord where id =?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllTrips = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete From TripRecord";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllLocation = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete From location";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllSyncedRides = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete FROM TripRecord WHERE id IN (select DISTINCT(tripid) from location where tripid not IN (select DISTINCT(tripid) from location where serversync=0))";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final TripRecord... TripRecord) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTripRecord.insert(TripRecord);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final Location... locations) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfLocation.insert(locations);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Long save(final Location location) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfLocation.insertAndReturnId(location);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Long save(final TripRecord tripRecord) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfTripRecord.insertAndReturnId(tripRecord);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final TripRecord trip) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTripRecord.handle(trip);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateRecord(final TripRecord tripRecord) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfTripRecord.handle(tripRecord);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Location location) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfLocation.handle(location);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateRecord(final UUID id, final long endTime) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateRecord.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, endTime);
    _argIndex = 2;
    final String _tmp = UUIDConverter.fromUUID(id);
    if (_tmp == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, _tmp);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateRecord.release(_stmt);
    }
  }

  @Override
  public void updateLocationById(final int syncServer, final UUID locationId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLocationById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, syncServer);
    _argIndex = 2;
    final String _tmp = UUIDConverter.fromUUID(locationId);
    if (_tmp == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, _tmp);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateLocationById.release(_stmt);
    }
  }

  @Override
  public void deleteById(final UUID tripId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    final String _tmp = UUIDConverter.fromUUID(tripId);
    if (_tmp == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, _tmp);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public void deleteAllTrips() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTrips.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllTrips.release(_stmt);
    }
  }

  @Override
  public void deleteAllLocation() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllLocation.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllLocation.release(_stmt);
    }
  }

  @Override
  public void deleteAllSyncedRides() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllSyncedRides.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllSyncedRides.release(_stmt);
    }
  }

  @Override
  public List<TripRecord> getAllTripRecord() {
    final String _sql = "SELECT * FROM TripRecord order by start_time desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStartTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
      final int _cursorIndexOfEndTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfTotalDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "total_distance");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceId");
      final int _cursorIndexOfRidePurposeId = CursorUtil.getColumnIndexOrThrow(_cursor, "purposeId");
      final int _cursorIndexOfReimbursementCost = CursorUtil.getColumnIndexOrThrow(_cursor, "reimbursementCost");
      final int _cursorIndexOfSanctionDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "sanctionDistance");
      final List<TripRecord> _result = new ArrayList<TripRecord>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TripRecord _item;
        _item = new TripRecord();
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfTripId)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfTripId);
        }
        _item.tripId = UUIDConverter.uuidFromString(_tmp);
        _item.startTimestamp = _cursor.getLong(_cursorIndexOfStartTimestamp);
        _item.endTimestamp = _cursor.getLong(_cursorIndexOfEndTimestamp);
        _item.totalDistance = _cursor.getDouble(_cursorIndexOfTotalDistance);
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfStatus);
        _item.status = _tmp_1 != 0;
        if (_cursor.isNull(_cursorIndexOfDeviceId)) {
          _item.deviceId = null;
        } else {
          _item.deviceId = _cursor.getString(_cursorIndexOfDeviceId);
        }
        if (_cursor.isNull(_cursorIndexOfRidePurposeId)) {
          _item.ridePurposeId = null;
        } else {
          _item.ridePurposeId = _cursor.getString(_cursorIndexOfRidePurposeId);
        }
        if (_cursor.isNull(_cursorIndexOfReimbursementCost)) {
          _item.reimbursementCost = null;
        } else {
          _item.reimbursementCost = _cursor.getString(_cursorIndexOfReimbursementCost);
        }
        if (_cursor.isNull(_cursorIndexOfSanctionDistance)) {
          _item.sanctionDistance = null;
        } else {
          _item.sanctionDistance = _cursor.getString(_cursorIndexOfSanctionDistance);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<TripRecordLocationRelation> getAllRides() {
    final String _sql = "SELECT * FROM TripRecord order by start_time desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
        final int _cursorIndexOfStartTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
        final int _cursorIndexOfEndTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
        final int _cursorIndexOfTotalDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "total_distance");
        final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
        final int _cursorIndexOfDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceId");
        final int _cursorIndexOfRidePurposeId = CursorUtil.getColumnIndexOrThrow(_cursor, "purposeId");
        final int _cursorIndexOfReimbursementCost = CursorUtil.getColumnIndexOrThrow(_cursor, "reimbursementCost");
        final int _cursorIndexOfSanctionDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "sanctionDistance");
        final ArrayMap<String, ArrayList<Location>> _collectionLocations = new ArrayMap<String, ArrayList<Location>>();
        while (_cursor.moveToNext()) {
          if (!_cursor.isNull(_cursorIndexOfTripId)) {
            final String _tmpKey = _cursor.getString(_cursorIndexOfTripId);
            ArrayList<Location> _tmpLocationsCollection = _collectionLocations.get(_tmpKey);
            if (_tmpLocationsCollection == null) {
              _tmpLocationsCollection = new ArrayList<Location>();
              _collectionLocations.put(_tmpKey, _tmpLocationsCollection);
            }
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshiplocationAscomThoughtpearlConveyanceRespositoryEntityLocation(_collectionLocations);
        final List<TripRecordLocationRelation> _result = new ArrayList<TripRecordLocationRelation>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final TripRecordLocationRelation _item;
          final TripRecord _tmpTripRecord;
          if (! (_cursor.isNull(_cursorIndexOfTripId) && _cursor.isNull(_cursorIndexOfStartTimestamp) && _cursor.isNull(_cursorIndexOfEndTimestamp) && _cursor.isNull(_cursorIndexOfTotalDistance) && _cursor.isNull(_cursorIndexOfStatus) && _cursor.isNull(_cursorIndexOfDeviceId) && _cursor.isNull(_cursorIndexOfRidePurposeId) && _cursor.isNull(_cursorIndexOfReimbursementCost) && _cursor.isNull(_cursorIndexOfSanctionDistance))) {
            _tmpTripRecord = new TripRecord();
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTripId)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTripId);
            }
            _tmpTripRecord.tripId = UUIDConverter.uuidFromString(_tmp);
            _tmpTripRecord.startTimestamp = _cursor.getLong(_cursorIndexOfStartTimestamp);
            _tmpTripRecord.endTimestamp = _cursor.getLong(_cursorIndexOfEndTimestamp);
            _tmpTripRecord.totalDistance = _cursor.getDouble(_cursorIndexOfTotalDistance);
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfStatus);
            _tmpTripRecord.status = _tmp_1 != 0;
            if (_cursor.isNull(_cursorIndexOfDeviceId)) {
              _tmpTripRecord.deviceId = null;
            } else {
              _tmpTripRecord.deviceId = _cursor.getString(_cursorIndexOfDeviceId);
            }
            if (_cursor.isNull(_cursorIndexOfRidePurposeId)) {
              _tmpTripRecord.ridePurposeId = null;
            } else {
              _tmpTripRecord.ridePurposeId = _cursor.getString(_cursorIndexOfRidePurposeId);
            }
            if (_cursor.isNull(_cursorIndexOfReimbursementCost)) {
              _tmpTripRecord.reimbursementCost = null;
            } else {
              _tmpTripRecord.reimbursementCost = _cursor.getString(_cursorIndexOfReimbursementCost);
            }
            if (_cursor.isNull(_cursorIndexOfSanctionDistance)) {
              _tmpTripRecord.sanctionDistance = null;
            } else {
              _tmpTripRecord.sanctionDistance = _cursor.getString(_cursorIndexOfSanctionDistance);
            }
          }  else  {
            _tmpTripRecord = null;
          }
          ArrayList<Location> _tmpLocationsCollection_1 = null;
          if (!_cursor.isNull(_cursorIndexOfTripId)) {
            final String _tmpKey_1 = _cursor.getString(_cursorIndexOfTripId);
            _tmpLocationsCollection_1 = _collectionLocations.get(_tmpKey_1);
          }
          if (_tmpLocationsCollection_1 == null) {
            _tmpLocationsCollection_1 = new ArrayList<Location>();
          }
          _item = new TripRecordLocationRelation();
          _item.tripRecord = _tmpTripRecord;
          _item.locations = _tmpLocationsCollection_1;
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public UUID[] getTotalTrips() {
    final String _sql = "SELECT distinct(id) FROM TripRecord";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final UUID[] _result = new UUID[_cursor.getCount()];
      int _index = 0;
      while(_cursor.moveToNext()) {
        final UUID _item;
        final String _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(0);
        }
        _item = UUIDConverter.uuidFromString(_tmp);
        _result[_index] = _item;
        _index ++;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<TripRecord> getTripByIds(final UUID[] tripIds) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM TripRecord WHERE id IN (");
    final int _inputSize = tripIds.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (UUID _item : tripIds) {
      final String _tmp = UUIDConverter.fromUUID(_item);
      if (_tmp == null) {
        _statement.bindNull(_argIndex);
      } else {
        _statement.bindString(_argIndex, _tmp);
      }
      _argIndex ++;
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStartTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
      final int _cursorIndexOfEndTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfTotalDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "total_distance");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceId");
      final int _cursorIndexOfRidePurposeId = CursorUtil.getColumnIndexOrThrow(_cursor, "purposeId");
      final int _cursorIndexOfReimbursementCost = CursorUtil.getColumnIndexOrThrow(_cursor, "reimbursementCost");
      final int _cursorIndexOfSanctionDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "sanctionDistance");
      final List<TripRecord> _result = new ArrayList<TripRecord>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TripRecord _item_1;
        _item_1 = new TripRecord();
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfTripId)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfTripId);
        }
        _item_1.tripId = UUIDConverter.uuidFromString(_tmp_1);
        _item_1.startTimestamp = _cursor.getLong(_cursorIndexOfStartTimestamp);
        _item_1.endTimestamp = _cursor.getLong(_cursorIndexOfEndTimestamp);
        _item_1.totalDistance = _cursor.getDouble(_cursorIndexOfTotalDistance);
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfStatus);
        _item_1.status = _tmp_2 != 0;
        if (_cursor.isNull(_cursorIndexOfDeviceId)) {
          _item_1.deviceId = null;
        } else {
          _item_1.deviceId = _cursor.getString(_cursorIndexOfDeviceId);
        }
        if (_cursor.isNull(_cursorIndexOfRidePurposeId)) {
          _item_1.ridePurposeId = null;
        } else {
          _item_1.ridePurposeId = _cursor.getString(_cursorIndexOfRidePurposeId);
        }
        if (_cursor.isNull(_cursorIndexOfReimbursementCost)) {
          _item_1.reimbursementCost = null;
        } else {
          _item_1.reimbursementCost = _cursor.getString(_cursorIndexOfReimbursementCost);
        }
        if (_cursor.isNull(_cursorIndexOfSanctionDistance)) {
          _item_1.sanctionDistance = null;
        } else {
          _item_1.sanctionDistance = _cursor.getString(_cursorIndexOfSanctionDistance);
        }
        _result.add(_item_1);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public TripRecord getTripById(final UUID tripId) {
    final String _sql = "SELECT * FROM TripRecord WHERE id IN (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = UUIDConverter.fromUUID(tripId);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStartTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
      final int _cursorIndexOfEndTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfTotalDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "total_distance");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceId");
      final int _cursorIndexOfRidePurposeId = CursorUtil.getColumnIndexOrThrow(_cursor, "purposeId");
      final int _cursorIndexOfReimbursementCost = CursorUtil.getColumnIndexOrThrow(_cursor, "reimbursementCost");
      final int _cursorIndexOfSanctionDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "sanctionDistance");
      final TripRecord _result;
      if(_cursor.moveToFirst()) {
        _result = new TripRecord();
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfTripId)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfTripId);
        }
        _result.tripId = UUIDConverter.uuidFromString(_tmp_1);
        _result.startTimestamp = _cursor.getLong(_cursorIndexOfStartTimestamp);
        _result.endTimestamp = _cursor.getLong(_cursorIndexOfEndTimestamp);
        _result.totalDistance = _cursor.getDouble(_cursorIndexOfTotalDistance);
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfStatus);
        _result.status = _tmp_2 != 0;
        if (_cursor.isNull(_cursorIndexOfDeviceId)) {
          _result.deviceId = null;
        } else {
          _result.deviceId = _cursor.getString(_cursorIndexOfDeviceId);
        }
        if (_cursor.isNull(_cursorIndexOfRidePurposeId)) {
          _result.ridePurposeId = null;
        } else {
          _result.ridePurposeId = _cursor.getString(_cursorIndexOfRidePurposeId);
        }
        if (_cursor.isNull(_cursorIndexOfReimbursementCost)) {
          _result.reimbursementCost = null;
        } else {
          _result.reimbursementCost = _cursor.getString(_cursorIndexOfReimbursementCost);
        }
        if (_cursor.isNull(_cursorIndexOfSanctionDistance)) {
          _result.sanctionDistance = null;
        } else {
          _result.sanctionDistance = _cursor.getString(_cursorIndexOfSanctionDistance);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public TripRecord getRunningTrip() {
    final String _sql = "SELECT * FROM TripRecord order by start_time desc limit 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStartTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
      final int _cursorIndexOfEndTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
      final int _cursorIndexOfTotalDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "total_distance");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceId");
      final int _cursorIndexOfRidePurposeId = CursorUtil.getColumnIndexOrThrow(_cursor, "purposeId");
      final int _cursorIndexOfReimbursementCost = CursorUtil.getColumnIndexOrThrow(_cursor, "reimbursementCost");
      final int _cursorIndexOfSanctionDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "sanctionDistance");
      final TripRecord _result;
      if(_cursor.moveToFirst()) {
        _result = new TripRecord();
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfTripId)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfTripId);
        }
        _result.tripId = UUIDConverter.uuidFromString(_tmp);
        _result.startTimestamp = _cursor.getLong(_cursorIndexOfStartTimestamp);
        _result.endTimestamp = _cursor.getLong(_cursorIndexOfEndTimestamp);
        _result.totalDistance = _cursor.getDouble(_cursorIndexOfTotalDistance);
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfStatus);
        _result.status = _tmp_1 != 0;
        if (_cursor.isNull(_cursorIndexOfDeviceId)) {
          _result.deviceId = null;
        } else {
          _result.deviceId = _cursor.getString(_cursorIndexOfDeviceId);
        }
        if (_cursor.isNull(_cursorIndexOfRidePurposeId)) {
          _result.ridePurposeId = null;
        } else {
          _result.ridePurposeId = _cursor.getString(_cursorIndexOfRidePurposeId);
        }
        if (_cursor.isNull(_cursorIndexOfReimbursementCost)) {
          _result.reimbursementCost = null;
        } else {
          _result.reimbursementCost = _cursor.getString(_cursorIndexOfReimbursementCost);
        }
        if (_cursor.isNull(_cursorIndexOfSanctionDistance)) {
          _result.sanctionDistance = null;
        } else {
          _result.sanctionDistance = _cursor.getString(_cursorIndexOfSanctionDistance);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public UUID getLastTripId() {
    final String _sql = "Select id From TripRecord order by start_time desc limit 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final UUID _result;
      if(_cursor.moveToFirst()) {
        final String _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(0);
        }
        _result = UUIDConverter.uuidFromString(_tmp);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public TripRecordLocationRelation getByTripId(final UUID tripId) {
    final String _sql = "SELECT * FROM TripRecord WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = UUIDConverter.fromUUID(tripId);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
        final int _cursorIndexOfStartTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
        final int _cursorIndexOfEndTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
        final int _cursorIndexOfTotalDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "total_distance");
        final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
        final int _cursorIndexOfDeviceId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceId");
        final int _cursorIndexOfRidePurposeId = CursorUtil.getColumnIndexOrThrow(_cursor, "purposeId");
        final int _cursorIndexOfReimbursementCost = CursorUtil.getColumnIndexOrThrow(_cursor, "reimbursementCost");
        final int _cursorIndexOfSanctionDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "sanctionDistance");
        final ArrayMap<String, ArrayList<Location>> _collectionLocations = new ArrayMap<String, ArrayList<Location>>();
        while (_cursor.moveToNext()) {
          if (!_cursor.isNull(_cursorIndexOfTripId)) {
            final String _tmpKey = _cursor.getString(_cursorIndexOfTripId);
            ArrayList<Location> _tmpLocationsCollection = _collectionLocations.get(_tmpKey);
            if (_tmpLocationsCollection == null) {
              _tmpLocationsCollection = new ArrayList<Location>();
              _collectionLocations.put(_tmpKey, _tmpLocationsCollection);
            }
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshiplocationAscomThoughtpearlConveyanceRespositoryEntityLocation(_collectionLocations);
        final TripRecordLocationRelation _result;
        if(_cursor.moveToFirst()) {
          final TripRecord _tmpTripRecord;
          if (! (_cursor.isNull(_cursorIndexOfTripId) && _cursor.isNull(_cursorIndexOfStartTimestamp) && _cursor.isNull(_cursorIndexOfEndTimestamp) && _cursor.isNull(_cursorIndexOfTotalDistance) && _cursor.isNull(_cursorIndexOfStatus) && _cursor.isNull(_cursorIndexOfDeviceId) && _cursor.isNull(_cursorIndexOfRidePurposeId) && _cursor.isNull(_cursorIndexOfReimbursementCost) && _cursor.isNull(_cursorIndexOfSanctionDistance))) {
            _tmpTripRecord = new TripRecord();
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfTripId)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfTripId);
            }
            _tmpTripRecord.tripId = UUIDConverter.uuidFromString(_tmp_1);
            _tmpTripRecord.startTimestamp = _cursor.getLong(_cursorIndexOfStartTimestamp);
            _tmpTripRecord.endTimestamp = _cursor.getLong(_cursorIndexOfEndTimestamp);
            _tmpTripRecord.totalDistance = _cursor.getDouble(_cursorIndexOfTotalDistance);
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfStatus);
            _tmpTripRecord.status = _tmp_2 != 0;
            if (_cursor.isNull(_cursorIndexOfDeviceId)) {
              _tmpTripRecord.deviceId = null;
            } else {
              _tmpTripRecord.deviceId = _cursor.getString(_cursorIndexOfDeviceId);
            }
            if (_cursor.isNull(_cursorIndexOfRidePurposeId)) {
              _tmpTripRecord.ridePurposeId = null;
            } else {
              _tmpTripRecord.ridePurposeId = _cursor.getString(_cursorIndexOfRidePurposeId);
            }
            if (_cursor.isNull(_cursorIndexOfReimbursementCost)) {
              _tmpTripRecord.reimbursementCost = null;
            } else {
              _tmpTripRecord.reimbursementCost = _cursor.getString(_cursorIndexOfReimbursementCost);
            }
            if (_cursor.isNull(_cursorIndexOfSanctionDistance)) {
              _tmpTripRecord.sanctionDistance = null;
            } else {
              _tmpTripRecord.sanctionDistance = _cursor.getString(_cursorIndexOfSanctionDistance);
            }
          }  else  {
            _tmpTripRecord = null;
          }
          ArrayList<Location> _tmpLocationsCollection_1 = null;
          if (!_cursor.isNull(_cursorIndexOfTripId)) {
            final String _tmpKey_1 = _cursor.getString(_cursorIndexOfTripId);
            _tmpLocationsCollection_1 = _collectionLocations.get(_tmpKey_1);
          }
          if (_tmpLocationsCollection_1 == null) {
            _tmpLocationsCollection_1 = new ArrayList<Location>();
          }
          _result = new TripRecordLocationRelation();
          _result.tripRecord = _tmpTripRecord;
          _result.locations = _tmpLocationsCollection_1;
        } else {
          _result = null;
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public UUID getLastInsertedTripId(final Long rowId) {
    final String _sql = "Select id From TripRecord WHERE rowid =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (rowId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, rowId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final UUID _result;
      if(_cursor.moveToFirst()) {
        final String _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(0);
        }
        _result = UUIDConverter.uuidFromString(_tmp);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Location> getUnSyncServerLocations(final String tripId) {
    final String _sql = "SELECT * FROM location WHERE tripId IN (?) AND serverSync=0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "locationId");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfServerSync = CursorUtil.getColumnIndexOrThrow(_cursor, "serverSync");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
      final List<Location> _result = new ArrayList<Location>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Location _item;
        _item = new Location();
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfLocationId);
        }
        _item.locationId = UUIDConverter.uuidFromString(_tmp);
        if (_cursor.isNull(_cursorIndexOfLatitude)) {
          _item.latitude = null;
        } else {
          _item.latitude = _cursor.getDouble(_cursorIndexOfLatitude);
        }
        if (_cursor.isNull(_cursorIndexOfLongitude)) {
          _item.longitude = null;
        } else {
          _item.longitude = _cursor.getDouble(_cursorIndexOfLongitude);
        }
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfServerSync);
        _item.serverSync = _tmp_1 != 0;
        if (_cursor.isNull(_cursorIndexOfTimestamp)) {
          _item.timestamp = null;
        } else {
          _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
        }
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfTripId)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfTripId);
        }
        _item.tripId = UUIDConverter.uuidFromString(_tmp_2);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Location> getUnSyncServerLocations() {
    final String _sql = "SELECT * FROM location WHERE serverSync=0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "locationId");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfServerSync = CursorUtil.getColumnIndexOrThrow(_cursor, "serverSync");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
      final List<Location> _result = new ArrayList<Location>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Location _item;
        _item = new Location();
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfLocationId);
        }
        _item.locationId = UUIDConverter.uuidFromString(_tmp);
        if (_cursor.isNull(_cursorIndexOfLatitude)) {
          _item.latitude = null;
        } else {
          _item.latitude = _cursor.getDouble(_cursorIndexOfLatitude);
        }
        if (_cursor.isNull(_cursorIndexOfLongitude)) {
          _item.longitude = null;
        } else {
          _item.longitude = _cursor.getDouble(_cursorIndexOfLongitude);
        }
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfServerSync);
        _item.serverSync = _tmp_1 != 0;
        if (_cursor.isNull(_cursorIndexOfTimestamp)) {
          _item.timestamp = null;
        } else {
          _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
        }
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfTripId)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfTripId);
        }
        _item.tripId = UUIDConverter.uuidFromString(_tmp_2);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getUnSyncRidesCount() {
    final String _sql = "SELECT count(DISTINCT(tripid)) from location where serversync=0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<UnSyncRideDto> getUnSyncRidesList() {
    final String _sql = "SELECT DISTINCT(tripid) from location where serversync=0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTripId = 0;
      final List<UnSyncRideDto> _result = new ArrayList<UnSyncRideDto>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UnSyncRideDto _item;
        final String _tmpTripId;
        if (_cursor.isNull(_cursorIndexOfTripId)) {
          _tmpTripId = null;
        } else {
          _tmpTripId = _cursor.getString(_cursorIndexOfTripId);
        }
        _item = new UnSyncRideDto(_tmpTripId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Location> getLocations(final String tripId) {
    final String _sql = "SELECT * FROM location WHERE tripId IN (?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "locationId");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfServerSync = CursorUtil.getColumnIndexOrThrow(_cursor, "serverSync");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
      final List<Location> _result = new ArrayList<Location>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Location _item;
        _item = new Location();
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfLocationId)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfLocationId);
        }
        _item.locationId = UUIDConverter.uuidFromString(_tmp);
        if (_cursor.isNull(_cursorIndexOfLatitude)) {
          _item.latitude = null;
        } else {
          _item.latitude = _cursor.getDouble(_cursorIndexOfLatitude);
        }
        if (_cursor.isNull(_cursorIndexOfLongitude)) {
          _item.longitude = null;
        } else {
          _item.longitude = _cursor.getDouble(_cursorIndexOfLongitude);
        }
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfServerSync);
        _item.serverSync = _tmp_1 != 0;
        if (_cursor.isNull(_cursorIndexOfTimestamp)) {
          _item.timestamp = null;
        } else {
          _item.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
        }
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfTripId)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfTripId);
        }
        _item.tripId = UUIDConverter.uuidFromString(_tmp_2);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshiplocationAscomThoughtpearlConveyanceRespositoryEntityLocation(
      final ArrayMap<String, ArrayList<Location>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      ArrayMap<String, ArrayList<Location>> _tmpInnerMap = new ArrayMap<String, ArrayList<Location>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiplocationAscomThoughtpearlConveyanceRespositoryEntityLocation(_tmpInnerMap);
          _tmpInnerMap = new ArrayMap<String, ArrayList<Location>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiplocationAscomThoughtpearlConveyanceRespositoryEntityLocation(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `locationId`,`latitude`,`longitude`,`serverSync`,`timestamp`,`tripId` FROM `location` WHERE `tripId` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "tripId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfLocationId = 0;
      final int _cursorIndexOfLatitude = 1;
      final int _cursorIndexOfLongitude = 2;
      final int _cursorIndexOfServerSync = 3;
      final int _cursorIndexOfTimestamp = 4;
      final int _cursorIndexOfTripId = 5;
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final String _tmpKey = _cursor.getString(_itemKeyIndex);
          ArrayList<Location> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final Location _item_1;
            _item_1 = new Location();
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfLocationId)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfLocationId);
            }
            _item_1.locationId = UUIDConverter.uuidFromString(_tmp);
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _item_1.latitude = null;
            } else {
              _item_1.latitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _item_1.longitude = null;
            } else {
              _item_1.longitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfServerSync);
            _item_1.serverSync = _tmp_1 != 0;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _item_1.timestamp = null;
            } else {
              _item_1.timestamp = _cursor.getString(_cursorIndexOfTimestamp);
            }
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfTripId)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfTripId);
            }
            _item_1.tripId = UUIDConverter.uuidFromString(_tmp_2);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
