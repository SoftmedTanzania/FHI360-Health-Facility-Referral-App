package com.softmed.htmr_facility.dom.dao;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.softmed.htmr_facility.dom.objects.ReferralIndicator;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class ReferralIndicatorDao_Impl implements ReferralIndicatorDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfReferralIndicator;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfReferralIndicator;

  public ReferralIndicatorDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReferralIndicator = new EntityInsertionAdapter<ReferralIndicator>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `ReferralIndicator`(`referralServiceIndicatorId`,`referralIndicatorId`,`indicatorName`,`isActive`,`serviceID`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ReferralIndicator value) {
        stmt.bindLong(1, value.getReferralServiceIndicatorId());
        stmt.bindLong(2, value.getReferralIndicatorId());
        if (value.getIndicatorName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getIndicatorName());
        }
        final int _tmp;
        _tmp = value.isActive() ? 1 : 0;
        stmt.bindLong(4, _tmp);
        stmt.bindLong(5, value.getServiceID());
      }
    };
    this.__deletionAdapterOfReferralIndicator = new EntityDeletionOrUpdateAdapter<ReferralIndicator>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ReferralIndicator` WHERE `referralServiceIndicatorId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ReferralIndicator value) {
        stmt.bindLong(1, value.getReferralServiceIndicatorId());
      }
    };
  }

  @Override
  public void addIndicator(ReferralIndicator referralIndicator) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfReferralIndicator.insert(referralIndicator);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAPatient(ReferralIndicator referralIndicator) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfReferralIndicator.handle(referralIndicator);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<ReferralIndicator>> getAllIndicators() {
    final String _sql = "select * from ReferralIndicator";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<ReferralIndicator>>() {
      private Observer _observer;

      @Override
      protected List<ReferralIndicator> compute() {
        if (_observer == null) {
          _observer = new Observer("ReferralIndicator") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfReferralServiceIndicatorId = _cursor.getColumnIndexOrThrow("referralServiceIndicatorId");
          final int _cursorIndexOfReferralIndicatorId = _cursor.getColumnIndexOrThrow("referralIndicatorId");
          final int _cursorIndexOfIndicatorName = _cursor.getColumnIndexOrThrow("indicatorName");
          final int _cursorIndexOfIsActive = _cursor.getColumnIndexOrThrow("isActive");
          final int _cursorIndexOfServiceID = _cursor.getColumnIndexOrThrow("serviceID");
          final List<ReferralIndicator> _result = new ArrayList<ReferralIndicator>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ReferralIndicator _item;
            _item = new ReferralIndicator();
            final long _tmpReferralServiceIndicatorId;
            _tmpReferralServiceIndicatorId = _cursor.getLong(_cursorIndexOfReferralServiceIndicatorId);
            _item.setReferralServiceIndicatorId(_tmpReferralServiceIndicatorId);
            final long _tmpReferralIndicatorId;
            _tmpReferralIndicatorId = _cursor.getLong(_cursorIndexOfReferralIndicatorId);
            _item.setReferralIndicatorId(_tmpReferralIndicatorId);
            final String _tmpIndicatorName;
            _tmpIndicatorName = _cursor.getString(_cursorIndexOfIndicatorName);
            _item.setIndicatorName(_tmpIndicatorName);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            _item.setActive(_tmpIsActive);
            final long _tmpServiceID;
            _tmpServiceID = _cursor.getLong(_cursorIndexOfServiceID);
            _item.setServiceID(_tmpServiceID);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public List<ReferralIndicator> getIndicatorsByServiceId(long serviceID) {
    final String _sql = "select * from ReferralIndicator where serviceID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceID);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfReferralServiceIndicatorId = _cursor.getColumnIndexOrThrow("referralServiceIndicatorId");
      final int _cursorIndexOfReferralIndicatorId = _cursor.getColumnIndexOrThrow("referralIndicatorId");
      final int _cursorIndexOfIndicatorName = _cursor.getColumnIndexOrThrow("indicatorName");
      final int _cursorIndexOfIsActive = _cursor.getColumnIndexOrThrow("isActive");
      final int _cursorIndexOfServiceID = _cursor.getColumnIndexOrThrow("serviceID");
      final List<ReferralIndicator> _result = new ArrayList<ReferralIndicator>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ReferralIndicator _item;
        _item = new ReferralIndicator();
        final long _tmpReferralServiceIndicatorId;
        _tmpReferralServiceIndicatorId = _cursor.getLong(_cursorIndexOfReferralServiceIndicatorId);
        _item.setReferralServiceIndicatorId(_tmpReferralServiceIndicatorId);
        final long _tmpReferralIndicatorId;
        _tmpReferralIndicatorId = _cursor.getLong(_cursorIndexOfReferralIndicatorId);
        _item.setReferralIndicatorId(_tmpReferralIndicatorId);
        final String _tmpIndicatorName;
        _tmpIndicatorName = _cursor.getString(_cursorIndexOfIndicatorName);
        _item.setIndicatorName(_tmpIndicatorName);
        final boolean _tmpIsActive;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp != 0;
        _item.setActive(_tmpIsActive);
        final long _tmpServiceID;
        _tmpServiceID = _cursor.getLong(_cursorIndexOfServiceID);
        _item.setServiceID(_tmpServiceID);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ReferralIndicator getReferralIndicatorById(String referralIndicatorID) {
    final String _sql = "select * from ReferralIndicator where referralServiceIndicatorId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (referralIndicatorID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, referralIndicatorID);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfReferralServiceIndicatorId = _cursor.getColumnIndexOrThrow("referralServiceIndicatorId");
      final int _cursorIndexOfReferralIndicatorId = _cursor.getColumnIndexOrThrow("referralIndicatorId");
      final int _cursorIndexOfIndicatorName = _cursor.getColumnIndexOrThrow("indicatorName");
      final int _cursorIndexOfIsActive = _cursor.getColumnIndexOrThrow("isActive");
      final int _cursorIndexOfServiceID = _cursor.getColumnIndexOrThrow("serviceID");
      final ReferralIndicator _result;
      if(_cursor.moveToFirst()) {
        _result = new ReferralIndicator();
        final long _tmpReferralServiceIndicatorId;
        _tmpReferralServiceIndicatorId = _cursor.getLong(_cursorIndexOfReferralServiceIndicatorId);
        _result.setReferralServiceIndicatorId(_tmpReferralServiceIndicatorId);
        final long _tmpReferralIndicatorId;
        _tmpReferralIndicatorId = _cursor.getLong(_cursorIndexOfReferralIndicatorId);
        _result.setReferralIndicatorId(_tmpReferralIndicatorId);
        final String _tmpIndicatorName;
        _tmpIndicatorName = _cursor.getString(_cursorIndexOfIndicatorName);
        _result.setIndicatorName(_tmpIndicatorName);
        final boolean _tmpIsActive;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp != 0;
        _result.setActive(_tmpIsActive);
        final long _tmpServiceID;
        _tmpServiceID = _cursor.getLong(_cursorIndexOfServiceID);
        _result.setServiceID(_tmpServiceID);
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
