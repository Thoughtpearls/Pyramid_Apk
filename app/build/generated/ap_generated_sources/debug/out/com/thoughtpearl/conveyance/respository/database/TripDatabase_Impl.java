package com.thoughtpearl.conveyance.respository.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.thoughtpearl.conveyance.respository.dao.TripRecordDao;
import com.thoughtpearl.conveyance.respository.dao.TripRecordDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TripDatabase_Impl extends TripDatabase {
  private volatile TripRecordDao _tripRecordDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `TripRecord` (`id` INTEGER NOT NULL, `start_time` INTEGER NOT NULL, `end_time` INTEGER NOT NULL, `total_distance` REAL NOT NULL, `status` INTEGER NOT NULL, `deviceId` TEXT, `purposeId` TEXT, `reimbursementCost` TEXT, `sanctionDistance` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `location` (`locationId` INTEGER NOT NULL, `latitude` REAL, `longitude` REAL, `serverSync` INTEGER NOT NULL, `timestamp` TEXT, `tripId` INTEGER, PRIMARY KEY(`locationId`), FOREIGN KEY(`tripId`) REFERENCES `TripRecord`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7f3e548c8ac246174f5aaf90d5e60c00')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `TripRecord`");
        _db.execSQL("DROP TABLE IF EXISTS `location`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTripRecord = new HashMap<String, TableInfo.Column>(9);
        _columnsTripRecord.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripRecord.put("start_time", new TableInfo.Column("start_time", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripRecord.put("end_time", new TableInfo.Column("end_time", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripRecord.put("total_distance", new TableInfo.Column("total_distance", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripRecord.put("status", new TableInfo.Column("status", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripRecord.put("deviceId", new TableInfo.Column("deviceId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripRecord.put("purposeId", new TableInfo.Column("purposeId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripRecord.put("reimbursementCost", new TableInfo.Column("reimbursementCost", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTripRecord.put("sanctionDistance", new TableInfo.Column("sanctionDistance", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTripRecord = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTripRecord = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTripRecord = new TableInfo("TripRecord", _columnsTripRecord, _foreignKeysTripRecord, _indicesTripRecord);
        final TableInfo _existingTripRecord = TableInfo.read(_db, "TripRecord");
        if (! _infoTripRecord.equals(_existingTripRecord)) {
          return new RoomOpenHelper.ValidationResult(false, "TripRecord(com.thoughtpearl.conveyance.respository.entity.TripRecord).\n"
                  + " Expected:\n" + _infoTripRecord + "\n"
                  + " Found:\n" + _existingTripRecord);
        }
        final HashMap<String, TableInfo.Column> _columnsLocation = new HashMap<String, TableInfo.Column>(6);
        _columnsLocation.put("locationId", new TableInfo.Column("locationId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocation.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocation.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocation.put("serverSync", new TableInfo.Column("serverSync", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocation.put("timestamp", new TableInfo.Column("timestamp", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocation.put("tripId", new TableInfo.Column("tripId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLocation = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLocation.add(new TableInfo.ForeignKey("TripRecord", "CASCADE", "NO ACTION",Arrays.asList("tripId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesLocation = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLocation = new TableInfo("location", _columnsLocation, _foreignKeysLocation, _indicesLocation);
        final TableInfo _existingLocation = TableInfo.read(_db, "location");
        if (! _infoLocation.equals(_existingLocation)) {
          return new RoomOpenHelper.ValidationResult(false, "location(com.thoughtpearl.conveyance.respository.entity.Location).\n"
                  + " Expected:\n" + _infoLocation + "\n"
                  + " Found:\n" + _existingLocation);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7f3e548c8ac246174f5aaf90d5e60c00", "c082b2d0cc4d0e1d26b80309341fc8b6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "TripRecord","location");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `TripRecord`");
      _db.execSQL("DELETE FROM `location`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TripRecordDao.class, TripRecordDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public TripRecordDao tripRecordDao() {
    if (_tripRecordDao != null) {
      return _tripRecordDao;
    } else {
      synchronized(this) {
        if(_tripRecordDao == null) {
          _tripRecordDao = new TripRecordDao_Impl(this);
        }
        return _tripRecordDao;
      }
    }
  }
}
