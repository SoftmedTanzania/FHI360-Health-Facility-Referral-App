package com.softmed.htmr_facility.dom.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.softmed.htmr_facility.dom.objects.HealthFacilityServices;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class PatientServicesModelDao_Impl implements PatientServicesModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfHealthFacilityServices;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfHealthFacilityServices;

  public PatientServicesModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHealthFacilityServices = new EntityInsertionAdapter<HealthFacilityServices>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `HealthFacilityServices`(`id`,`serviceName`,`isActive`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HealthFacilityServices value) {
        stmt.bindLong(1, value.getId());
        if (value.getServiceName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getServiceName());
        }
        final int _tmp;
        _tmp = value.isActive() ? 1 : 0;
        stmt.bindLong(3, _tmp);
      }
    };
    this.__deletionAdapterOfHealthFacilityServices = new EntityDeletionOrUpdateAdapter<HealthFacilityServices>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `HealthFacilityServices` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HealthFacilityServices value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void addService(HealthFacilityServices service) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfHealthFacilityServices.insert(service);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteService(HealthFacilityServices service) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfHealthFacilityServices.handle(service);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<HealthFacilityServices> getAllServices() {
    final String _sql = "select * from HealthFacilityServices";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfServiceName = _cursor.getColumnIndexOrThrow("serviceName");
      final int _cursorIndexOfIsActive = _cursor.getColumnIndexOrThrow("isActive");
      final List<HealthFacilityServices> _result = new ArrayList<HealthFacilityServices>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final HealthFacilityServices _item;
        _item = new HealthFacilityServices();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpServiceName;
        _tmpServiceName = _cursor.getString(_cursorIndexOfServiceName);
        _item.setServiceName(_tmpServiceName);
        final boolean _tmpIsActive;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp != 0;
        _item.setActive(_tmpIsActive);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public String getServiceName(int serviceID) {
    final String _sql = "select serviceName || ' ' from HealthFacilityServices where id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceID);
    final Cursor _cursor = __db.query(_statement);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        final String _tmp;
        _tmp = _cursor.getString(0);
        _result = _tmp;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
