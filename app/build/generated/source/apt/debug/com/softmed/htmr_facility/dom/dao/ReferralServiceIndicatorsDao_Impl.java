package com.softmed.htmr_facility.dom.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.softmed.htmr_facility.dom.objects.ReferralServiceIndicators;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class ReferralServiceIndicatorsDao_Impl implements ReferralServiceIndicatorsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfReferralServiceIndicators;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfReferralServiceIndicators;

  public ReferralServiceIndicatorsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReferralServiceIndicators = new EntityInsertionAdapter<ReferralServiceIndicators>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `ReferralServiceIndicators`(`serviceId`,`serviceName`,`category`,`isActive`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ReferralServiceIndicators value) {
        stmt.bindLong(1, value.getServiceId());
        if (value.getServiceName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getServiceName());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCategory());
        }
        final int _tmp;
        _tmp = value.isActive() ? 1 : 0;
        stmt.bindLong(4, _tmp);
      }
    };
    this.__deletionAdapterOfReferralServiceIndicators = new EntityDeletionOrUpdateAdapter<ReferralServiceIndicators>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ReferralServiceIndicators` WHERE `serviceId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ReferralServiceIndicators value) {
        stmt.bindLong(1, value.getServiceId());
      }
    };
  }

  @Override
  public void addService(ReferralServiceIndicators referralServiceIndicator) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfReferralServiceIndicators.insert(referralServiceIndicator);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAPatient(ReferralServiceIndicators referralServiceIndicators) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfReferralServiceIndicators.handle(referralServiceIndicators);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<ReferralServiceIndicators> getAllServices() {
    final String _sql = "select * from ReferralServiceIndicators";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
      final int _cursorIndexOfServiceName = _cursor.getColumnIndexOrThrow("serviceName");
      final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
      final int _cursorIndexOfIsActive = _cursor.getColumnIndexOrThrow("isActive");
      final List<ReferralServiceIndicators> _result = new ArrayList<ReferralServiceIndicators>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ReferralServiceIndicators _item;
        _item = new ReferralServiceIndicators();
        final long _tmpServiceId;
        _tmpServiceId = _cursor.getLong(_cursorIndexOfServiceId);
        _item.setServiceId(_tmpServiceId);
        final String _tmpServiceName;
        _tmpServiceName = _cursor.getString(_cursorIndexOfServiceName);
        _item.setServiceName(_tmpServiceName);
        final String _tmpCategory;
        _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        _item.setCategory(_tmpCategory);
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
  public ReferralServiceIndicators getServiceById(long serviceID) {
    final String _sql = "select * from ReferralServiceIndicators where serviceId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceID);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
      final int _cursorIndexOfServiceName = _cursor.getColumnIndexOrThrow("serviceName");
      final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
      final int _cursorIndexOfIsActive = _cursor.getColumnIndexOrThrow("isActive");
      final ReferralServiceIndicators _result;
      if(_cursor.moveToFirst()) {
        _result = new ReferralServiceIndicators();
        final long _tmpServiceId;
        _tmpServiceId = _cursor.getLong(_cursorIndexOfServiceId);
        _result.setServiceId(_tmpServiceId);
        final String _tmpServiceName;
        _tmpServiceName = _cursor.getString(_cursorIndexOfServiceName);
        _result.setServiceName(_tmpServiceName);
        final String _tmpCategory;
        _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        _result.setCategory(_tmpCategory);
        final boolean _tmpIsActive;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp != 0;
        _result.setActive(_tmpIsActive);
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
  public String getServiceNameById(int serviceID) {
    final String _sql = "select serviceName || ' ' from ReferralServiceIndicators where serviceId = ?";
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
