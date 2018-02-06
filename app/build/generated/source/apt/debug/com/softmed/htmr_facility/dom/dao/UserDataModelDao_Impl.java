package com.softmed.htmr_facility.dom.dao;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.softmed.htmr_facility.dom.objects.UserData;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class UserDataModelDao_Impl implements UserDataModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUserData;

  public UserDataModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserData = new EntityInsertionAdapter<UserData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `UserData`(`UserUIID`,`userName`,`userFacilityId`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserData value) {
        if (value.getUserUIID() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUserUIID());
        }
        if (value.getUserName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserName());
        }
        if (value.getUserFacilityId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUserFacilityId());
        }
      }
    };
  }

  @Override
  public void addUserData(UserData data) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUserData.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<UserData>> getUserData() {
    final String _sql = "select * from UserData";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<UserData>>() {
      private Observer _observer;

      @Override
      protected List<UserData> compute() {
        if (_observer == null) {
          _observer = new Observer("UserData") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfUserUIID = _cursor.getColumnIndexOrThrow("UserUIID");
          final int _cursorIndexOfUserName = _cursor.getColumnIndexOrThrow("userName");
          final int _cursorIndexOfUserFacilityId = _cursor.getColumnIndexOrThrow("userFacilityId");
          final List<UserData> _result = new ArrayList<UserData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final UserData _item;
            _item = new UserData();
            final String _tmpUserUIID;
            _tmpUserUIID = _cursor.getString(_cursorIndexOfUserUIID);
            _item.setUserUIID(_tmpUserUIID);
            final String _tmpUserName;
            _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            _item.setUserName(_tmpUserName);
            final String _tmpUserFacilityId;
            _tmpUserFacilityId = _cursor.getString(_cursorIndexOfUserFacilityId);
            _item.setUserFacilityId(_tmpUserFacilityId);
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
  public UserData getUserDataByUserUIID(String useruiid) {
    final String _sql = "select * from UserData where UserUIID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (useruiid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, useruiid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserUIID = _cursor.getColumnIndexOrThrow("UserUIID");
      final int _cursorIndexOfUserName = _cursor.getColumnIndexOrThrow("userName");
      final int _cursorIndexOfUserFacilityId = _cursor.getColumnIndexOrThrow("userFacilityId");
      final UserData _result;
      if(_cursor.moveToFirst()) {
        _result = new UserData();
        final String _tmpUserUIID;
        _tmpUserUIID = _cursor.getString(_cursorIndexOfUserUIID);
        _result.setUserUIID(_tmpUserUIID);
        final String _tmpUserName;
        _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
        _result.setUserName(_tmpUserName);
        final String _tmpUserFacilityId;
        _tmpUserFacilityId = _cursor.getString(_cursorIndexOfUserFacilityId);
        _result.setUserFacilityId(_tmpUserFacilityId);
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
