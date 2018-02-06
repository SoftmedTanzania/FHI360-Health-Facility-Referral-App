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
import com.softmed.htmr_facility.dom.objects.TbEncounters;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class TbEncounterModelDao_Impl implements TbEncounterModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTbEncounters;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfTbEncounters;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfTbEncounters;

  public TbEncounterModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTbEncounters = new EntityInsertionAdapter<TbEncounters>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `TbEncounters`(`id`,`tbPatientID`,`encounterMonth`,`makohozi`,`appointmentId`,`hasFinishedPreviousMonthMedication`,`medicationStatus`,`medicationDate`,`scheduledDate`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TbEncounters value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getTbPatientID() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTbPatientID());
        }
        stmt.bindLong(3, value.getEncounterMonth());
        if (value.getMakohozi() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMakohozi());
        }
        stmt.bindLong(5, value.getAppointmentId());
        final int _tmp;
        _tmp = value.isHasFinishedPreviousMonthMedication() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        final int _tmp_1;
        _tmp_1 = value.isMedicationStatus() ? 1 : 0;
        stmt.bindLong(7, _tmp_1);
        stmt.bindLong(8, value.getMedicationDate());
        stmt.bindLong(9, value.getScheduledDate());
      }
    };
    this.__deletionAdapterOfTbEncounters = new EntityDeletionOrUpdateAdapter<TbEncounters>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `TbEncounters` WHERE `tbPatientID` = ? AND `encounterMonth` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TbEncounters value) {
        if (value.getTbPatientID() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getTbPatientID());
        }
        stmt.bindLong(2, value.getEncounterMonth());
      }
    };
    this.__updateAdapterOfTbEncounters = new EntityDeletionOrUpdateAdapter<TbEncounters>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `TbEncounters` SET `id` = ?,`tbPatientID` = ?,`encounterMonth` = ?,`makohozi` = ?,`appointmentId` = ?,`hasFinishedPreviousMonthMedication` = ?,`medicationStatus` = ?,`medicationDate` = ?,`scheduledDate` = ? WHERE `tbPatientID` = ? AND `encounterMonth` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TbEncounters value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getTbPatientID() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTbPatientID());
        }
        stmt.bindLong(3, value.getEncounterMonth());
        if (value.getMakohozi() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMakohozi());
        }
        stmt.bindLong(5, value.getAppointmentId());
        final int _tmp;
        _tmp = value.isHasFinishedPreviousMonthMedication() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        final int _tmp_1;
        _tmp_1 = value.isMedicationStatus() ? 1 : 0;
        stmt.bindLong(7, _tmp_1);
        stmt.bindLong(8, value.getMedicationDate());
        stmt.bindLong(9, value.getScheduledDate());
        if (value.getTbPatientID() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTbPatientID());
        }
        stmt.bindLong(11, value.getEncounterMonth());
      }
    };
  }

  @Override
  public void addEncounter(TbEncounters tbEncounters) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTbEncounters.insert(tbEncounters);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAnEncounter(TbEncounters encounters) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfTbEncounters.handle(encounters);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updatePreviousMonthMedicationStatus(TbEncounters tbEncounters) {
    __db.beginTransaction();
    try {
      __updateAdapterOfTbEncounters.handle(tbEncounters);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<TbEncounters> getEncounterByPatientID(String tbPatientId) {
    final String _sql = "select * from TbEncounters where tbPatientID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tbPatientId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tbPatientId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTbPatientID = _cursor.getColumnIndexOrThrow("tbPatientID");
      final int _cursorIndexOfEncounterMonth = _cursor.getColumnIndexOrThrow("encounterMonth");
      final int _cursorIndexOfMakohozi = _cursor.getColumnIndexOrThrow("makohozi");
      final int _cursorIndexOfAppointmentId = _cursor.getColumnIndexOrThrow("appointmentId");
      final int _cursorIndexOfHasFinishedPreviousMonthMedication = _cursor.getColumnIndexOrThrow("hasFinishedPreviousMonthMedication");
      final int _cursorIndexOfMedicationStatus = _cursor.getColumnIndexOrThrow("medicationStatus");
      final int _cursorIndexOfMedicationDate = _cursor.getColumnIndexOrThrow("medicationDate");
      final int _cursorIndexOfScheduledDate = _cursor.getColumnIndexOrThrow("scheduledDate");
      final List<TbEncounters> _result = new ArrayList<TbEncounters>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TbEncounters _item;
        _item = new TbEncounters();
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTbPatientID;
        _tmpTbPatientID = _cursor.getString(_cursorIndexOfTbPatientID);
        _item.setTbPatientID(_tmpTbPatientID);
        final int _tmpEncounterMonth;
        _tmpEncounterMonth = _cursor.getInt(_cursorIndexOfEncounterMonth);
        _item.setEncounterMonth(_tmpEncounterMonth);
        final String _tmpMakohozi;
        _tmpMakohozi = _cursor.getString(_cursorIndexOfMakohozi);
        _item.setMakohozi(_tmpMakohozi);
        final long _tmpAppointmentId;
        _tmpAppointmentId = _cursor.getLong(_cursorIndexOfAppointmentId);
        _item.setAppointmentId(_tmpAppointmentId);
        final boolean _tmpHasFinishedPreviousMonthMedication;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfHasFinishedPreviousMonthMedication);
        _tmpHasFinishedPreviousMonthMedication = _tmp != 0;
        _item.setHasFinishedPreviousMonthMedication(_tmpHasFinishedPreviousMonthMedication);
        final boolean _tmpMedicationStatus;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfMedicationStatus);
        _tmpMedicationStatus = _tmp_1 != 0;
        _item.setMedicationStatus(_tmpMedicationStatus);
        final long _tmpMedicationDate;
        _tmpMedicationDate = _cursor.getLong(_cursorIndexOfMedicationDate);
        _item.setMedicationDate(_tmpMedicationDate);
        final long _tmpScheduledDate;
        _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
        _item.setScheduledDate(_tmpScheduledDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<TbEncounters>> getAllEncounters() {
    final String _sql = "select * from TbEncounters";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<TbEncounters>>() {
      private Observer _observer;

      @Override
      protected List<TbEncounters> compute() {
        if (_observer == null) {
          _observer = new Observer("TbEncounters") {
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
          final int _cursorIndexOfTbPatientID = _cursor.getColumnIndexOrThrow("tbPatientID");
          final int _cursorIndexOfEncounterMonth = _cursor.getColumnIndexOrThrow("encounterMonth");
          final int _cursorIndexOfMakohozi = _cursor.getColumnIndexOrThrow("makohozi");
          final int _cursorIndexOfAppointmentId = _cursor.getColumnIndexOrThrow("appointmentId");
          final int _cursorIndexOfHasFinishedPreviousMonthMedication = _cursor.getColumnIndexOrThrow("hasFinishedPreviousMonthMedication");
          final int _cursorIndexOfMedicationStatus = _cursor.getColumnIndexOrThrow("medicationStatus");
          final int _cursorIndexOfMedicationDate = _cursor.getColumnIndexOrThrow("medicationDate");
          final int _cursorIndexOfScheduledDate = _cursor.getColumnIndexOrThrow("scheduledDate");
          final List<TbEncounters> _result = new ArrayList<TbEncounters>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TbEncounters _item;
            _item = new TbEncounters();
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTbPatientID;
            _tmpTbPatientID = _cursor.getString(_cursorIndexOfTbPatientID);
            _item.setTbPatientID(_tmpTbPatientID);
            final int _tmpEncounterMonth;
            _tmpEncounterMonth = _cursor.getInt(_cursorIndexOfEncounterMonth);
            _item.setEncounterMonth(_tmpEncounterMonth);
            final String _tmpMakohozi;
            _tmpMakohozi = _cursor.getString(_cursorIndexOfMakohozi);
            _item.setMakohozi(_tmpMakohozi);
            final long _tmpAppointmentId;
            _tmpAppointmentId = _cursor.getLong(_cursorIndexOfAppointmentId);
            _item.setAppointmentId(_tmpAppointmentId);
            final boolean _tmpHasFinishedPreviousMonthMedication;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasFinishedPreviousMonthMedication);
            _tmpHasFinishedPreviousMonthMedication = _tmp != 0;
            _item.setHasFinishedPreviousMonthMedication(_tmpHasFinishedPreviousMonthMedication);
            final boolean _tmpMedicationStatus;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMedicationStatus);
            _tmpMedicationStatus = _tmp_1 != 0;
            _item.setMedicationStatus(_tmpMedicationStatus);
            final long _tmpMedicationDate;
            _tmpMedicationDate = _cursor.getLong(_cursorIndexOfMedicationDate);
            _item.setMedicationDate(_tmpMedicationDate);
            final long _tmpScheduledDate;
            _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
            _item.setScheduledDate(_tmpScheduledDate);
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
  public List<TbEncounters> getAllEncountersList(String tbPatientTempId) {
    final String _sql = "select * from TbEncounters where tbPatientID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tbPatientTempId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tbPatientTempId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTbPatientID = _cursor.getColumnIndexOrThrow("tbPatientID");
      final int _cursorIndexOfEncounterMonth = _cursor.getColumnIndexOrThrow("encounterMonth");
      final int _cursorIndexOfMakohozi = _cursor.getColumnIndexOrThrow("makohozi");
      final int _cursorIndexOfAppointmentId = _cursor.getColumnIndexOrThrow("appointmentId");
      final int _cursorIndexOfHasFinishedPreviousMonthMedication = _cursor.getColumnIndexOrThrow("hasFinishedPreviousMonthMedication");
      final int _cursorIndexOfMedicationStatus = _cursor.getColumnIndexOrThrow("medicationStatus");
      final int _cursorIndexOfMedicationDate = _cursor.getColumnIndexOrThrow("medicationDate");
      final int _cursorIndexOfScheduledDate = _cursor.getColumnIndexOrThrow("scheduledDate");
      final List<TbEncounters> _result = new ArrayList<TbEncounters>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TbEncounters _item;
        _item = new TbEncounters();
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTbPatientID;
        _tmpTbPatientID = _cursor.getString(_cursorIndexOfTbPatientID);
        _item.setTbPatientID(_tmpTbPatientID);
        final int _tmpEncounterMonth;
        _tmpEncounterMonth = _cursor.getInt(_cursorIndexOfEncounterMonth);
        _item.setEncounterMonth(_tmpEncounterMonth);
        final String _tmpMakohozi;
        _tmpMakohozi = _cursor.getString(_cursorIndexOfMakohozi);
        _item.setMakohozi(_tmpMakohozi);
        final long _tmpAppointmentId;
        _tmpAppointmentId = _cursor.getLong(_cursorIndexOfAppointmentId);
        _item.setAppointmentId(_tmpAppointmentId);
        final boolean _tmpHasFinishedPreviousMonthMedication;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfHasFinishedPreviousMonthMedication);
        _tmpHasFinishedPreviousMonthMedication = _tmp != 0;
        _item.setHasFinishedPreviousMonthMedication(_tmpHasFinishedPreviousMonthMedication);
        final boolean _tmpMedicationStatus;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfMedicationStatus);
        _tmpMedicationStatus = _tmp_1 != 0;
        _item.setMedicationStatus(_tmpMedicationStatus);
        final long _tmpMedicationDate;
        _tmpMedicationDate = _cursor.getLong(_cursorIndexOfMedicationDate);
        _item.setMedicationDate(_tmpMedicationDate);
        final long _tmpScheduledDate;
        _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
        _item.setScheduledDate(_tmpScheduledDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<TbEncounters> getMonthEncounter(String encounterMonth, String tbPatientID) {
    final String _sql = "select * from TbEncounters where encounterMonth = ? and tbPatientID =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (encounterMonth == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, encounterMonth);
    }
    _argIndex = 2;
    if (tbPatientID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tbPatientID);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTbPatientID = _cursor.getColumnIndexOrThrow("tbPatientID");
      final int _cursorIndexOfEncounterMonth = _cursor.getColumnIndexOrThrow("encounterMonth");
      final int _cursorIndexOfMakohozi = _cursor.getColumnIndexOrThrow("makohozi");
      final int _cursorIndexOfAppointmentId = _cursor.getColumnIndexOrThrow("appointmentId");
      final int _cursorIndexOfHasFinishedPreviousMonthMedication = _cursor.getColumnIndexOrThrow("hasFinishedPreviousMonthMedication");
      final int _cursorIndexOfMedicationStatus = _cursor.getColumnIndexOrThrow("medicationStatus");
      final int _cursorIndexOfMedicationDate = _cursor.getColumnIndexOrThrow("medicationDate");
      final int _cursorIndexOfScheduledDate = _cursor.getColumnIndexOrThrow("scheduledDate");
      final List<TbEncounters> _result = new ArrayList<TbEncounters>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TbEncounters _item;
        _item = new TbEncounters();
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTbPatientID;
        _tmpTbPatientID = _cursor.getString(_cursorIndexOfTbPatientID);
        _item.setTbPatientID(_tmpTbPatientID);
        final int _tmpEncounterMonth;
        _tmpEncounterMonth = _cursor.getInt(_cursorIndexOfEncounterMonth);
        _item.setEncounterMonth(_tmpEncounterMonth);
        final String _tmpMakohozi;
        _tmpMakohozi = _cursor.getString(_cursorIndexOfMakohozi);
        _item.setMakohozi(_tmpMakohozi);
        final long _tmpAppointmentId;
        _tmpAppointmentId = _cursor.getLong(_cursorIndexOfAppointmentId);
        _item.setAppointmentId(_tmpAppointmentId);
        final boolean _tmpHasFinishedPreviousMonthMedication;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfHasFinishedPreviousMonthMedication);
        _tmpHasFinishedPreviousMonthMedication = _tmp != 0;
        _item.setHasFinishedPreviousMonthMedication(_tmpHasFinishedPreviousMonthMedication);
        final boolean _tmpMedicationStatus;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfMedicationStatus);
        _tmpMedicationStatus = _tmp_1 != 0;
        _item.setMedicationStatus(_tmpMedicationStatus);
        final long _tmpMedicationDate;
        _tmpMedicationDate = _cursor.getLong(_cursorIndexOfMedicationDate);
        _item.setMedicationDate(_tmpMedicationDate);
        final long _tmpScheduledDate;
        _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
        _item.setScheduledDate(_tmpScheduledDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
