package com.softmed.htmr_facility.dom.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.softmed.htmr_facility.dom.objects.HealthFacilities;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class HealthFacilitiesModelDao_Impl implements HealthFacilitiesModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfHealthFacilities;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfHealthFacilities;

  public HealthFacilitiesModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHealthFacilities = new EntityInsertionAdapter<HealthFacilities>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `HealthFacilities`(`ID`,`openMRSUIID`,`facilityName`,`facilityCtcCode`,`parentOpenmrsUIID`,`parentHFRCode`,`hfrCode`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HealthFacilities value) {
        stmt.bindLong(1, value.getID());
        if (value.getOpenMRSUIID() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getOpenMRSUIID());
        }
        if (value.getFacilityName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getFacilityName());
        }
        if (value.getFacilityCtcCode() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getFacilityCtcCode());
        }
        if (value.getParentOpenmrsUIID() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getParentOpenmrsUIID());
        }
        if (value.getParentHFRCode() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getParentHFRCode());
        }
        if (value.getHfrCode() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getHfrCode());
        }
      }
    };
    this.__deletionAdapterOfHealthFacilities = new EntityDeletionOrUpdateAdapter<HealthFacilities>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `HealthFacilities` WHERE `ID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HealthFacilities value) {
        stmt.bindLong(1, value.getID());
      }
    };
  }

  @Override
  public void addHealthFacility(HealthFacilities hf) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfHealthFacilities.insert(hf);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteHealthFacility(HealthFacilities hf) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfHealthFacilities.handle(hf);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<HealthFacilities> getAllHealthFacilities() {
    final String _sql = "select * from HealthFacilities";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("ID");
      final int _cursorIndexOfOpenMRSUIID = _cursor.getColumnIndexOrThrow("openMRSUIID");
      final int _cursorIndexOfFacilityName = _cursor.getColumnIndexOrThrow("facilityName");
      final int _cursorIndexOfFacilityCtcCode = _cursor.getColumnIndexOrThrow("facilityCtcCode");
      final int _cursorIndexOfParentOpenmrsUIID = _cursor.getColumnIndexOrThrow("parentOpenmrsUIID");
      final int _cursorIndexOfParentHFRCode = _cursor.getColumnIndexOrThrow("parentHFRCode");
      final int _cursorIndexOfHfrCode = _cursor.getColumnIndexOrThrow("hfrCode");
      final List<HealthFacilities> _result = new ArrayList<HealthFacilities>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final HealthFacilities _item;
        _item = new HealthFacilities();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final String _tmpOpenMRSUIID;
        _tmpOpenMRSUIID = _cursor.getString(_cursorIndexOfOpenMRSUIID);
        _item.setOpenMRSUIID(_tmpOpenMRSUIID);
        final String _tmpFacilityName;
        _tmpFacilityName = _cursor.getString(_cursorIndexOfFacilityName);
        _item.setFacilityName(_tmpFacilityName);
        final String _tmpFacilityCtcCode;
        _tmpFacilityCtcCode = _cursor.getString(_cursorIndexOfFacilityCtcCode);
        _item.setFacilityCtcCode(_tmpFacilityCtcCode);
        final String _tmpParentOpenmrsUIID;
        _tmpParentOpenmrsUIID = _cursor.getString(_cursorIndexOfParentOpenmrsUIID);
        _item.setParentOpenmrsUIID(_tmpParentOpenmrsUIID);
        final String _tmpParentHFRCode;
        _tmpParentHFRCode = _cursor.getString(_cursorIndexOfParentHFRCode);
        _item.setParentHFRCode(_tmpParentHFRCode);
        final String _tmpHfrCode;
        _tmpHfrCode = _cursor.getString(_cursorIndexOfHfrCode);
        _item.setHfrCode(_tmpHfrCode);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
