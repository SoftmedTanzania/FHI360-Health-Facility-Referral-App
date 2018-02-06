package com.softmed.htmr_facility.dom.dao;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.softmed.htmr_facility.dom.objects.PatientAppointment;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class PatientAppointmentModelDao_Impl implements PatientAppointmentModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPatientAppointment;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPatientAppointment;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPatientAppointment;

  public PatientAppointmentModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPatientAppointment = new EntityInsertionAdapter<PatientAppointment>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `PatientAppointment`(`appointmentID`,`patientID`,`appointmentDate`,`appointmentType`,`cancelled`,`status`,`appointmentEncounterMonth`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PatientAppointment value) {
        if (value.getAppointmentID() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getAppointmentID());
        }
        if (value.getPatientID() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPatientID());
        }
        stmt.bindLong(3, value.getAppointmentDate());
        stmt.bindLong(4, value.getAppointmentType());
        final int _tmp;
        _tmp = value.isCancelled() ? 1 : 0;
        stmt.bindLong(5, _tmp);
        if (value.getStatus() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getStatus());
        }
        if (value.getAppointmentEncounterMonth() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getAppointmentEncounterMonth());
        }
      }
    };
    this.__deletionAdapterOfPatientAppointment = new EntityDeletionOrUpdateAdapter<PatientAppointment>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `PatientAppointment` WHERE `appointmentID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PatientAppointment value) {
        if (value.getAppointmentID() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getAppointmentID());
        }
      }
    };
    this.__updateAdapterOfPatientAppointment = new EntityDeletionOrUpdateAdapter<PatientAppointment>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `PatientAppointment` SET `appointmentID` = ?,`patientID` = ?,`appointmentDate` = ?,`appointmentType` = ?,`cancelled` = ?,`status` = ?,`appointmentEncounterMonth` = ? WHERE `appointmentID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PatientAppointment value) {
        if (value.getAppointmentID() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getAppointmentID());
        }
        if (value.getPatientID() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPatientID());
        }
        stmt.bindLong(3, value.getAppointmentDate());
        stmt.bindLong(4, value.getAppointmentType());
        final int _tmp;
        _tmp = value.isCancelled() ? 1 : 0;
        stmt.bindLong(5, _tmp);
        if (value.getStatus() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getStatus());
        }
        if (value.getAppointmentEncounterMonth() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getAppointmentEncounterMonth());
        }
        if (value.getAppointmentID() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, value.getAppointmentID());
        }
      }
    };
  }

  @Override
  public void addAppointment(PatientAppointment appointment) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPatientAppointment.insert(appointment);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAppointment(PatientAppointment appointment) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPatientAppointment.handle(appointment);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateAppointment(PatientAppointment appointment) {
    __db.beginTransaction();
    try {
      __updateAdapterOfPatientAppointment.handle(appointment);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<PatientAppointment> getAllAppointments() {
    final String _sql = "select * from PatientAppointment order by appointmentDate asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAppointmentID = _cursor.getColumnIndexOrThrow("appointmentID");
      final int _cursorIndexOfPatientID = _cursor.getColumnIndexOrThrow("patientID");
      final int _cursorIndexOfAppointmentDate = _cursor.getColumnIndexOrThrow("appointmentDate");
      final int _cursorIndexOfAppointmentType = _cursor.getColumnIndexOrThrow("appointmentType");
      final int _cursorIndexOfCancelled = _cursor.getColumnIndexOrThrow("cancelled");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfAppointmentEncounterMonth = _cursor.getColumnIndexOrThrow("appointmentEncounterMonth");
      final List<PatientAppointment> _result = new ArrayList<PatientAppointment>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PatientAppointment _item;
        _item = new PatientAppointment();
        final Long _tmpAppointmentID;
        if (_cursor.isNull(_cursorIndexOfAppointmentID)) {
          _tmpAppointmentID = null;
        } else {
          _tmpAppointmentID = _cursor.getLong(_cursorIndexOfAppointmentID);
        }
        _item.setAppointmentID(_tmpAppointmentID);
        final String _tmpPatientID;
        _tmpPatientID = _cursor.getString(_cursorIndexOfPatientID);
        _item.setPatientID(_tmpPatientID);
        final long _tmpAppointmentDate;
        _tmpAppointmentDate = _cursor.getLong(_cursorIndexOfAppointmentDate);
        _item.setAppointmentDate(_tmpAppointmentDate);
        final int _tmpAppointmentType;
        _tmpAppointmentType = _cursor.getInt(_cursorIndexOfAppointmentType);
        _item.setAppointmentType(_tmpAppointmentType);
        final boolean _tmpCancelled;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCancelled);
        _tmpCancelled = _tmp != 0;
        _item.setCancelled(_tmpCancelled);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpAppointmentEncounterMonth;
        _tmpAppointmentEncounterMonth = _cursor.getString(_cursorIndexOfAppointmentEncounterMonth);
        _item.setAppointmentEncounterMonth(_tmpAppointmentEncounterMonth);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PatientAppointment> getAllTbAppointments() {
    final String _sql = "select * from PatientAppointment where appointmentType = 2 order by appointmentDate asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAppointmentID = _cursor.getColumnIndexOrThrow("appointmentID");
      final int _cursorIndexOfPatientID = _cursor.getColumnIndexOrThrow("patientID");
      final int _cursorIndexOfAppointmentDate = _cursor.getColumnIndexOrThrow("appointmentDate");
      final int _cursorIndexOfAppointmentType = _cursor.getColumnIndexOrThrow("appointmentType");
      final int _cursorIndexOfCancelled = _cursor.getColumnIndexOrThrow("cancelled");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfAppointmentEncounterMonth = _cursor.getColumnIndexOrThrow("appointmentEncounterMonth");
      final List<PatientAppointment> _result = new ArrayList<PatientAppointment>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PatientAppointment _item;
        _item = new PatientAppointment();
        final Long _tmpAppointmentID;
        if (_cursor.isNull(_cursorIndexOfAppointmentID)) {
          _tmpAppointmentID = null;
        } else {
          _tmpAppointmentID = _cursor.getLong(_cursorIndexOfAppointmentID);
        }
        _item.setAppointmentID(_tmpAppointmentID);
        final String _tmpPatientID;
        _tmpPatientID = _cursor.getString(_cursorIndexOfPatientID);
        _item.setPatientID(_tmpPatientID);
        final long _tmpAppointmentDate;
        _tmpAppointmentDate = _cursor.getLong(_cursorIndexOfAppointmentDate);
        _item.setAppointmentDate(_tmpAppointmentDate);
        final int _tmpAppointmentType;
        _tmpAppointmentType = _cursor.getInt(_cursorIndexOfAppointmentType);
        _item.setAppointmentType(_tmpAppointmentType);
        final boolean _tmpCancelled;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCancelled);
        _tmpCancelled = _tmp != 0;
        _item.setCancelled(_tmpCancelled);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpAppointmentEncounterMonth;
        _tmpAppointmentEncounterMonth = _cursor.getString(_cursorIndexOfAppointmentEncounterMonth);
        _item.setAppointmentEncounterMonth(_tmpAppointmentEncounterMonth);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PatientAppointment> getAllCTCAppointments() {
    final String _sql = "select * from PatientAppointment where appointmentType = 1 order by appointmentDate asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAppointmentID = _cursor.getColumnIndexOrThrow("appointmentID");
      final int _cursorIndexOfPatientID = _cursor.getColumnIndexOrThrow("patientID");
      final int _cursorIndexOfAppointmentDate = _cursor.getColumnIndexOrThrow("appointmentDate");
      final int _cursorIndexOfAppointmentType = _cursor.getColumnIndexOrThrow("appointmentType");
      final int _cursorIndexOfCancelled = _cursor.getColumnIndexOrThrow("cancelled");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfAppointmentEncounterMonth = _cursor.getColumnIndexOrThrow("appointmentEncounterMonth");
      final List<PatientAppointment> _result = new ArrayList<PatientAppointment>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PatientAppointment _item;
        _item = new PatientAppointment();
        final Long _tmpAppointmentID;
        if (_cursor.isNull(_cursorIndexOfAppointmentID)) {
          _tmpAppointmentID = null;
        } else {
          _tmpAppointmentID = _cursor.getLong(_cursorIndexOfAppointmentID);
        }
        _item.setAppointmentID(_tmpAppointmentID);
        final String _tmpPatientID;
        _tmpPatientID = _cursor.getString(_cursorIndexOfPatientID);
        _item.setPatientID(_tmpPatientID);
        final long _tmpAppointmentDate;
        _tmpAppointmentDate = _cursor.getLong(_cursorIndexOfAppointmentDate);
        _item.setAppointmentDate(_tmpAppointmentDate);
        final int _tmpAppointmentType;
        _tmpAppointmentType = _cursor.getInt(_cursorIndexOfAppointmentType);
        _item.setAppointmentType(_tmpAppointmentType);
        final boolean _tmpCancelled;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCancelled);
        _tmpCancelled = _tmp != 0;
        _item.setCancelled(_tmpCancelled);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpAppointmentEncounterMonth;
        _tmpAppointmentEncounterMonth = _cursor.getString(_cursorIndexOfAppointmentEncounterMonth);
        _item.setAppointmentEncounterMonth(_tmpAppointmentEncounterMonth);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PatientAppointment> getThisPatientAppointments(String patientId) {
    final String _sql = "select * from PatientAppointment where patientID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (patientId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, patientId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAppointmentID = _cursor.getColumnIndexOrThrow("appointmentID");
      final int _cursorIndexOfPatientID = _cursor.getColumnIndexOrThrow("patientID");
      final int _cursorIndexOfAppointmentDate = _cursor.getColumnIndexOrThrow("appointmentDate");
      final int _cursorIndexOfAppointmentType = _cursor.getColumnIndexOrThrow("appointmentType");
      final int _cursorIndexOfCancelled = _cursor.getColumnIndexOrThrow("cancelled");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfAppointmentEncounterMonth = _cursor.getColumnIndexOrThrow("appointmentEncounterMonth");
      final List<PatientAppointment> _result = new ArrayList<PatientAppointment>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PatientAppointment _item;
        _item = new PatientAppointment();
        final Long _tmpAppointmentID;
        if (_cursor.isNull(_cursorIndexOfAppointmentID)) {
          _tmpAppointmentID = null;
        } else {
          _tmpAppointmentID = _cursor.getLong(_cursorIndexOfAppointmentID);
        }
        _item.setAppointmentID(_tmpAppointmentID);
        final String _tmpPatientID;
        _tmpPatientID = _cursor.getString(_cursorIndexOfPatientID);
        _item.setPatientID(_tmpPatientID);
        final long _tmpAppointmentDate;
        _tmpAppointmentDate = _cursor.getLong(_cursorIndexOfAppointmentDate);
        _item.setAppointmentDate(_tmpAppointmentDate);
        final int _tmpAppointmentType;
        _tmpAppointmentType = _cursor.getInt(_cursorIndexOfAppointmentType);
        _item.setAppointmentType(_tmpAppointmentType);
        final boolean _tmpCancelled;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCancelled);
        _tmpCancelled = _tmp != 0;
        _item.setCancelled(_tmpCancelled);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpAppointmentEncounterMonth;
        _tmpAppointmentEncounterMonth = _cursor.getString(_cursorIndexOfAppointmentEncounterMonth);
        _item.setAppointmentEncounterMonth(_tmpAppointmentEncounterMonth);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PatientAppointment> getRemainingAppointments(String patientId, String today) {
    final String _sql = "select * from PatientAppointment where patientID = ? and appointmentDate > Date(?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (patientId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, patientId);
    }
    _argIndex = 2;
    if (today == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, today);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAppointmentID = _cursor.getColumnIndexOrThrow("appointmentID");
      final int _cursorIndexOfPatientID = _cursor.getColumnIndexOrThrow("patientID");
      final int _cursorIndexOfAppointmentDate = _cursor.getColumnIndexOrThrow("appointmentDate");
      final int _cursorIndexOfAppointmentType = _cursor.getColumnIndexOrThrow("appointmentType");
      final int _cursorIndexOfCancelled = _cursor.getColumnIndexOrThrow("cancelled");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final int _cursorIndexOfAppointmentEncounterMonth = _cursor.getColumnIndexOrThrow("appointmentEncounterMonth");
      final List<PatientAppointment> _result = new ArrayList<PatientAppointment>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PatientAppointment _item;
        _item = new PatientAppointment();
        final Long _tmpAppointmentID;
        if (_cursor.isNull(_cursorIndexOfAppointmentID)) {
          _tmpAppointmentID = null;
        } else {
          _tmpAppointmentID = _cursor.getLong(_cursorIndexOfAppointmentID);
        }
        _item.setAppointmentID(_tmpAppointmentID);
        final String _tmpPatientID;
        _tmpPatientID = _cursor.getString(_cursorIndexOfPatientID);
        _item.setPatientID(_tmpPatientID);
        final long _tmpAppointmentDate;
        _tmpAppointmentDate = _cursor.getLong(_cursorIndexOfAppointmentDate);
        _item.setAppointmentDate(_tmpAppointmentDate);
        final int _tmpAppointmentType;
        _tmpAppointmentType = _cursor.getInt(_cursorIndexOfAppointmentType);
        _item.setAppointmentType(_tmpAppointmentType);
        final boolean _tmpCancelled;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCancelled);
        _tmpCancelled = _tmp != 0;
        _item.setCancelled(_tmpCancelled);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpAppointmentEncounterMonth;
        _tmpAppointmentEncounterMonth = _cursor.getString(_cursorIndexOfAppointmentEncounterMonth);
        _item.setAppointmentEncounterMonth(_tmpAppointmentEncounterMonth);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
