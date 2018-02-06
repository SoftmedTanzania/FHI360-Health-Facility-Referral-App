package com.softmed.htmr_facility.dom.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import com.softmed.htmr_facility.dom.objects.AppData;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class AppDataModelDao_Impl implements AppDataModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfAppData;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDeviceID;

  public AppDataModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAppData = new EntityInsertionAdapter<AppData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `AppData`(`name`,`value`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AppData value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
        if (value.getValue() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getValue());
        }
      }
    };
    this.__preparedStmtOfUpdateDeviceID = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "update AppData set value = ? where name = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertDeviceID(AppData appData) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfAppData.insert(appData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateDeviceID(String token, String name) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDeviceID.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (token == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, token);
      }
      _argIndex = 2;
      if (name == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, name);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateDeviceID.release(_stmt);
    }
  }

  @Override
  public String getDeviceID(String name) {
    final String _sql = "select value from AppData where name = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
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
