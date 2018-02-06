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
import com.softmed.htmr_facility.dom.objects.PatientsNotification;
import com.softmed.htmr_facility.utils.DateConverter;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class PatientNotificationModelDao_Impl implements PatientNotificationModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPatientsNotification;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPatientsNotification;

  public PatientNotificationModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPatientsNotification = new EntityInsertionAdapter<PatientsNotification>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `PatientsNotification`(`id`,`patient_id`,`appointmentDate`,`appointmentType`,`notificationTime`,`message`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PatientsNotification value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getPatient_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPatient_id());
        }
        final Long _tmp;
        _tmp = DateConverter.toTimestamp(value.getAppointmentDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        if (value.getAppointmentType() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAppointmentType());
        }
        if (value.getNotificationTime() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getNotificationTime());
        }
        if (value.getMessage() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getMessage());
        }
      }
    };
    this.__deletionAdapterOfPatientsNotification = new EntityDeletionOrUpdateAdapter<PatientsNotification>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `PatientsNotification` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PatientsNotification value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
      }
    };
  }

  @Override
  public void addPatientNotification(PatientsNotification patientsNotification) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPatientsNotification.insert(patientsNotification);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletePatientNotification(PatientsNotification patientsNotification) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPatientsNotification.handle(patientsNotification);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<PatientsNotification>> getAllNotifications() {
    final String _sql = "select * from PatientsNotification";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<PatientsNotification>>() {
      private Observer _observer;

      @Override
      protected List<PatientsNotification> compute() {
        if (_observer == null) {
          _observer = new Observer("PatientsNotification") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patient_id");
          final int _cursorIndexOfAppointmentDate = _cursor.getColumnIndexOrThrow("appointmentDate");
          final int _cursorIndexOfAppointmentType = _cursor.getColumnIndexOrThrow("appointmentType");
          final int _cursorIndexOfNotificationTime = _cursor.getColumnIndexOrThrow("notificationTime");
          final int _cursorIndexOfMessage = _cursor.getColumnIndexOrThrow("message");
          final List<PatientsNotification> _result = new ArrayList<PatientsNotification>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PatientsNotification _item;
            _item = new PatientsNotification();
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpPatient_id;
            _tmpPatient_id = _cursor.getString(_cursorIndexOfPatientId);
            _item.setPatient_id(_tmpPatient_id);
            final Date _tmpAppointmentDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfAppointmentDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfAppointmentDate);
            }
            _tmpAppointmentDate = DateConverter.toDate(_tmp);
            _item.setAppointmentDate(_tmpAppointmentDate);
            final String _tmpAppointmentType;
            _tmpAppointmentType = _cursor.getString(_cursorIndexOfAppointmentType);
            _item.setAppointmentType(_tmpAppointmentType);
            final String _tmpNotificationTime;
            _tmpNotificationTime = _cursor.getString(_cursorIndexOfNotificationTime);
            _item.setNotificationTime(_tmpNotificationTime);
            final String _tmpMessage;
            _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            _item.setMessage(_tmpMessage);
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
  public PatientsNotification getPatientNotificationByPatientId(String id) {
    final String _sql = "select * from PatientsNotification where patient_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patient_id");
      final int _cursorIndexOfAppointmentDate = _cursor.getColumnIndexOrThrow("appointmentDate");
      final int _cursorIndexOfAppointmentType = _cursor.getColumnIndexOrThrow("appointmentType");
      final int _cursorIndexOfNotificationTime = _cursor.getColumnIndexOrThrow("notificationTime");
      final int _cursorIndexOfMessage = _cursor.getColumnIndexOrThrow("message");
      final PatientsNotification _result;
      if(_cursor.moveToFirst()) {
        _result = new PatientsNotification();
        final Long _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getLong(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
        final String _tmpPatient_id;
        _tmpPatient_id = _cursor.getString(_cursorIndexOfPatientId);
        _result.setPatient_id(_tmpPatient_id);
        final Date _tmpAppointmentDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfAppointmentDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfAppointmentDate);
        }
        _tmpAppointmentDate = DateConverter.toDate(_tmp);
        _result.setAppointmentDate(_tmpAppointmentDate);
        final String _tmpAppointmentType;
        _tmpAppointmentType = _cursor.getString(_cursorIndexOfAppointmentType);
        _result.setAppointmentType(_tmpAppointmentType);
        final String _tmpNotificationTime;
        _tmpNotificationTime = _cursor.getString(_cursorIndexOfNotificationTime);
        _result.setNotificationTime(_tmpNotificationTime);
        final String _tmpMessage;
        _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
        _result.setMessage(_tmpMessage);
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
