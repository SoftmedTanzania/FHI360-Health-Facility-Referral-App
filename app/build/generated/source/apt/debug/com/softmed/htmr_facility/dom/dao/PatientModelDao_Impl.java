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
import com.softmed.htmr_facility.dom.objects.Patient;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class PatientModelDao_Impl implements PatientModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPatient;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPatient;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPatient;

  public PatientModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPatient = new EntityInsertionAdapter<Patient>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Patient`(`id`,`patientId`,`patientFirstName`,`patientMiddleName`,`patientSurname`,`cbhs`,`ctcNumber`,`phone_number`,`ward`,`village`,`hamlet`,`dateOfBirth`,`gender`,`dateOfDeath`,`hivStatus`,`currentOnTbTreatment`,`careTakerName`,`careTakerPhoneNumber`,`careTakerRelationship`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Patient value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getPatientId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPatientId());
        }
        if (value.getPatientFirstName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPatientFirstName());
        }
        if (value.getPatientMiddleName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPatientMiddleName());
        }
        if (value.getPatientSurname() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPatientSurname());
        }
        if (value.getCbhs() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCbhs());
        }
        if (value.getCtcNumber() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCtcNumber());
        }
        if (value.getPhone_number() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPhone_number());
        }
        if (value.getWard() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getWard());
        }
        if (value.getVillage() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getVillage());
        }
        if (value.getHamlet() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getHamlet());
        }
        stmt.bindLong(12, value.getDateOfBirth());
        if (value.getGender() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getGender());
        }
        stmt.bindLong(14, value.getDateOfDeath());
        final int _tmp;
        _tmp = value.isHivStatus() ? 1 : 0;
        stmt.bindLong(15, _tmp);
        final int _tmp_1;
        _tmp_1 = value.isCurrentOnTbTreatment() ? 1 : 0;
        stmt.bindLong(16, _tmp_1);
        if (value.getCareTakerName() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getCareTakerName());
        }
        if (value.getCareTakerPhoneNumber() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getCareTakerPhoneNumber());
        }
        if (value.getCareTakerRelationship() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getCareTakerRelationship());
        }
      }
    };
    this.__deletionAdapterOfPatient = new EntityDeletionOrUpdateAdapter<Patient>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Patient` WHERE `patientId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Patient value) {
        if (value.getPatientId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPatientId());
        }
      }
    };
    this.__updateAdapterOfPatient = new EntityDeletionOrUpdateAdapter<Patient>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Patient` SET `id` = ?,`patientId` = ?,`patientFirstName` = ?,`patientMiddleName` = ?,`patientSurname` = ?,`cbhs` = ?,`ctcNumber` = ?,`phone_number` = ?,`ward` = ?,`village` = ?,`hamlet` = ?,`dateOfBirth` = ?,`gender` = ?,`dateOfDeath` = ?,`hivStatus` = ?,`currentOnTbTreatment` = ?,`careTakerName` = ?,`careTakerPhoneNumber` = ?,`careTakerRelationship` = ? WHERE `patientId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Patient value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getPatientId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPatientId());
        }
        if (value.getPatientFirstName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPatientFirstName());
        }
        if (value.getPatientMiddleName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPatientMiddleName());
        }
        if (value.getPatientSurname() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPatientSurname());
        }
        if (value.getCbhs() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCbhs());
        }
        if (value.getCtcNumber() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCtcNumber());
        }
        if (value.getPhone_number() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPhone_number());
        }
        if (value.getWard() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getWard());
        }
        if (value.getVillage() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getVillage());
        }
        if (value.getHamlet() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getHamlet());
        }
        stmt.bindLong(12, value.getDateOfBirth());
        if (value.getGender() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getGender());
        }
        stmt.bindLong(14, value.getDateOfDeath());
        final int _tmp;
        _tmp = value.isHivStatus() ? 1 : 0;
        stmt.bindLong(15, _tmp);
        final int _tmp_1;
        _tmp_1 = value.isCurrentOnTbTreatment() ? 1 : 0;
        stmt.bindLong(16, _tmp_1);
        if (value.getCareTakerName() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getCareTakerName());
        }
        if (value.getCareTakerPhoneNumber() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getCareTakerPhoneNumber());
        }
        if (value.getCareTakerRelationship() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getCareTakerRelationship());
        }
        if (value.getPatientId() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getPatientId());
        }
      }
    };
  }

  @Override
  public void addPatient(Patient patients) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPatient.insert(patients);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAPatient(Patient patients) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPatient.handle(patients);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updatePatient(Patient patient) {
    __db.beginTransaction();
    try {
      __updateAdapterOfPatient.handle(patient);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Patient>> getAllPatients() {
    final String _sql = "select * from Patient";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Patient>>() {
      private Observer _observer;

      @Override
      protected List<Patient> compute() {
        if (_observer == null) {
          _observer = new Observer("Patient") {
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
          final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patientId");
          final int _cursorIndexOfPatientFirstName = _cursor.getColumnIndexOrThrow("patientFirstName");
          final int _cursorIndexOfPatientMiddleName = _cursor.getColumnIndexOrThrow("patientMiddleName");
          final int _cursorIndexOfPatientSurname = _cursor.getColumnIndexOrThrow("patientSurname");
          final int _cursorIndexOfCbhs = _cursor.getColumnIndexOrThrow("cbhs");
          final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
          final int _cursorIndexOfPhoneNumber = _cursor.getColumnIndexOrThrow("phone_number");
          final int _cursorIndexOfWard = _cursor.getColumnIndexOrThrow("ward");
          final int _cursorIndexOfVillage = _cursor.getColumnIndexOrThrow("village");
          final int _cursorIndexOfHamlet = _cursor.getColumnIndexOrThrow("hamlet");
          final int _cursorIndexOfDateOfBirth = _cursor.getColumnIndexOrThrow("dateOfBirth");
          final int _cursorIndexOfGender = _cursor.getColumnIndexOrThrow("gender");
          final int _cursorIndexOfDateOfDeath = _cursor.getColumnIndexOrThrow("dateOfDeath");
          final int _cursorIndexOfHivStatus = _cursor.getColumnIndexOrThrow("hivStatus");
          final int _cursorIndexOfCurrentOnTbTreatment = _cursor.getColumnIndexOrThrow("currentOnTbTreatment");
          final int _cursorIndexOfCareTakerName = _cursor.getColumnIndexOrThrow("careTakerName");
          final int _cursorIndexOfCareTakerPhoneNumber = _cursor.getColumnIndexOrThrow("careTakerPhoneNumber");
          final int _cursorIndexOfCareTakerRelationship = _cursor.getColumnIndexOrThrow("careTakerRelationship");
          final List<Patient> _result = new ArrayList<Patient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Patient _item;
            _item = new Patient();
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpPatientId;
            _tmpPatientId = _cursor.getString(_cursorIndexOfPatientId);
            _item.setPatientId(_tmpPatientId);
            final String _tmpPatientFirstName;
            _tmpPatientFirstName = _cursor.getString(_cursorIndexOfPatientFirstName);
            _item.setPatientFirstName(_tmpPatientFirstName);
            final String _tmpPatientMiddleName;
            _tmpPatientMiddleName = _cursor.getString(_cursorIndexOfPatientMiddleName);
            _item.setPatientMiddleName(_tmpPatientMiddleName);
            final String _tmpPatientSurname;
            _tmpPatientSurname = _cursor.getString(_cursorIndexOfPatientSurname);
            _item.setPatientSurname(_tmpPatientSurname);
            final String _tmpCbhs;
            _tmpCbhs = _cursor.getString(_cursorIndexOfCbhs);
            _item.setCbhs(_tmpCbhs);
            final String _tmpCtcNumber;
            _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
            _item.setCtcNumber(_tmpCtcNumber);
            final String _tmpPhone_number;
            _tmpPhone_number = _cursor.getString(_cursorIndexOfPhoneNumber);
            _item.setPhone_number(_tmpPhone_number);
            final String _tmpWard;
            _tmpWard = _cursor.getString(_cursorIndexOfWard);
            _item.setWard(_tmpWard);
            final String _tmpVillage;
            _tmpVillage = _cursor.getString(_cursorIndexOfVillage);
            _item.setVillage(_tmpVillage);
            final String _tmpHamlet;
            _tmpHamlet = _cursor.getString(_cursorIndexOfHamlet);
            _item.setHamlet(_tmpHamlet);
            final long _tmpDateOfBirth;
            _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
            _item.setDateOfBirth(_tmpDateOfBirth);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            _item.setGender(_tmpGender);
            final long _tmpDateOfDeath;
            _tmpDateOfDeath = _cursor.getLong(_cursorIndexOfDateOfDeath);
            _item.setDateOfDeath(_tmpDateOfDeath);
            final boolean _tmpHivStatus;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHivStatus);
            _tmpHivStatus = _tmp != 0;
            _item.setHivStatus(_tmpHivStatus);
            final boolean _tmpCurrentOnTbTreatment;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfCurrentOnTbTreatment);
            _tmpCurrentOnTbTreatment = _tmp_1 != 0;
            _item.setCurrentOnTbTreatment(_tmpCurrentOnTbTreatment);
            final String _tmpCareTakerName;
            _tmpCareTakerName = _cursor.getString(_cursorIndexOfCareTakerName);
            _item.setCareTakerName(_tmpCareTakerName);
            final String _tmpCareTakerPhoneNumber;
            _tmpCareTakerPhoneNumber = _cursor.getString(_cursorIndexOfCareTakerPhoneNumber);
            _item.setCareTakerPhoneNumber(_tmpCareTakerPhoneNumber);
            final String _tmpCareTakerRelationship;
            _tmpCareTakerRelationship = _cursor.getString(_cursorIndexOfCareTakerRelationship);
            _item.setCareTakerRelationship(_tmpCareTakerRelationship);
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
  public LiveData<List<Patient>> getTbPatients(boolean flag) {
    final String _sql = "select * from Patient where currentOnTbTreatment = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final int _tmp;
    _tmp = flag ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    return new ComputableLiveData<List<Patient>>() {
      private Observer _observer;

      @Override
      protected List<Patient> compute() {
        if (_observer == null) {
          _observer = new Observer("Patient") {
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
          final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patientId");
          final int _cursorIndexOfPatientFirstName = _cursor.getColumnIndexOrThrow("patientFirstName");
          final int _cursorIndexOfPatientMiddleName = _cursor.getColumnIndexOrThrow("patientMiddleName");
          final int _cursorIndexOfPatientSurname = _cursor.getColumnIndexOrThrow("patientSurname");
          final int _cursorIndexOfCbhs = _cursor.getColumnIndexOrThrow("cbhs");
          final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
          final int _cursorIndexOfPhoneNumber = _cursor.getColumnIndexOrThrow("phone_number");
          final int _cursorIndexOfWard = _cursor.getColumnIndexOrThrow("ward");
          final int _cursorIndexOfVillage = _cursor.getColumnIndexOrThrow("village");
          final int _cursorIndexOfHamlet = _cursor.getColumnIndexOrThrow("hamlet");
          final int _cursorIndexOfDateOfBirth = _cursor.getColumnIndexOrThrow("dateOfBirth");
          final int _cursorIndexOfGender = _cursor.getColumnIndexOrThrow("gender");
          final int _cursorIndexOfDateOfDeath = _cursor.getColumnIndexOrThrow("dateOfDeath");
          final int _cursorIndexOfHivStatus = _cursor.getColumnIndexOrThrow("hivStatus");
          final int _cursorIndexOfCurrentOnTbTreatment = _cursor.getColumnIndexOrThrow("currentOnTbTreatment");
          final int _cursorIndexOfCareTakerName = _cursor.getColumnIndexOrThrow("careTakerName");
          final int _cursorIndexOfCareTakerPhoneNumber = _cursor.getColumnIndexOrThrow("careTakerPhoneNumber");
          final int _cursorIndexOfCareTakerRelationship = _cursor.getColumnIndexOrThrow("careTakerRelationship");
          final List<Patient> _result = new ArrayList<Patient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Patient _item;
            _item = new Patient();
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpPatientId;
            _tmpPatientId = _cursor.getString(_cursorIndexOfPatientId);
            _item.setPatientId(_tmpPatientId);
            final String _tmpPatientFirstName;
            _tmpPatientFirstName = _cursor.getString(_cursorIndexOfPatientFirstName);
            _item.setPatientFirstName(_tmpPatientFirstName);
            final String _tmpPatientMiddleName;
            _tmpPatientMiddleName = _cursor.getString(_cursorIndexOfPatientMiddleName);
            _item.setPatientMiddleName(_tmpPatientMiddleName);
            final String _tmpPatientSurname;
            _tmpPatientSurname = _cursor.getString(_cursorIndexOfPatientSurname);
            _item.setPatientSurname(_tmpPatientSurname);
            final String _tmpCbhs;
            _tmpCbhs = _cursor.getString(_cursorIndexOfCbhs);
            _item.setCbhs(_tmpCbhs);
            final String _tmpCtcNumber;
            _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
            _item.setCtcNumber(_tmpCtcNumber);
            final String _tmpPhone_number;
            _tmpPhone_number = _cursor.getString(_cursorIndexOfPhoneNumber);
            _item.setPhone_number(_tmpPhone_number);
            final String _tmpWard;
            _tmpWard = _cursor.getString(_cursorIndexOfWard);
            _item.setWard(_tmpWard);
            final String _tmpVillage;
            _tmpVillage = _cursor.getString(_cursorIndexOfVillage);
            _item.setVillage(_tmpVillage);
            final String _tmpHamlet;
            _tmpHamlet = _cursor.getString(_cursorIndexOfHamlet);
            _item.setHamlet(_tmpHamlet);
            final long _tmpDateOfBirth;
            _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
            _item.setDateOfBirth(_tmpDateOfBirth);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            _item.setGender(_tmpGender);
            final long _tmpDateOfDeath;
            _tmpDateOfDeath = _cursor.getLong(_cursorIndexOfDateOfDeath);
            _item.setDateOfDeath(_tmpDateOfDeath);
            final boolean _tmpHivStatus;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfHivStatus);
            _tmpHivStatus = _tmp_1 != 0;
            _item.setHivStatus(_tmpHivStatus);
            final boolean _tmpCurrentOnTbTreatment;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfCurrentOnTbTreatment);
            _tmpCurrentOnTbTreatment = _tmp_2 != 0;
            _item.setCurrentOnTbTreatment(_tmpCurrentOnTbTreatment);
            final String _tmpCareTakerName;
            _tmpCareTakerName = _cursor.getString(_cursorIndexOfCareTakerName);
            _item.setCareTakerName(_tmpCareTakerName);
            final String _tmpCareTakerPhoneNumber;
            _tmpCareTakerPhoneNumber = _cursor.getString(_cursorIndexOfCareTakerPhoneNumber);
            _item.setCareTakerPhoneNumber(_tmpCareTakerPhoneNumber);
            final String _tmpCareTakerRelationship;
            _tmpCareTakerRelationship = _cursor.getString(_cursorIndexOfCareTakerRelationship);
            _item.setCareTakerRelationship(_tmpCareTakerRelationship);
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
  public LiveData<List<Patient>> getHivClients(boolean flag) {
    final String _sql = "select * from Patient where hivStatus = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final int _tmp;
    _tmp = flag ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    return new ComputableLiveData<List<Patient>>() {
      private Observer _observer;

      @Override
      protected List<Patient> compute() {
        if (_observer == null) {
          _observer = new Observer("Patient") {
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
          final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patientId");
          final int _cursorIndexOfPatientFirstName = _cursor.getColumnIndexOrThrow("patientFirstName");
          final int _cursorIndexOfPatientMiddleName = _cursor.getColumnIndexOrThrow("patientMiddleName");
          final int _cursorIndexOfPatientSurname = _cursor.getColumnIndexOrThrow("patientSurname");
          final int _cursorIndexOfCbhs = _cursor.getColumnIndexOrThrow("cbhs");
          final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
          final int _cursorIndexOfPhoneNumber = _cursor.getColumnIndexOrThrow("phone_number");
          final int _cursorIndexOfWard = _cursor.getColumnIndexOrThrow("ward");
          final int _cursorIndexOfVillage = _cursor.getColumnIndexOrThrow("village");
          final int _cursorIndexOfHamlet = _cursor.getColumnIndexOrThrow("hamlet");
          final int _cursorIndexOfDateOfBirth = _cursor.getColumnIndexOrThrow("dateOfBirth");
          final int _cursorIndexOfGender = _cursor.getColumnIndexOrThrow("gender");
          final int _cursorIndexOfDateOfDeath = _cursor.getColumnIndexOrThrow("dateOfDeath");
          final int _cursorIndexOfHivStatus = _cursor.getColumnIndexOrThrow("hivStatus");
          final int _cursorIndexOfCurrentOnTbTreatment = _cursor.getColumnIndexOrThrow("currentOnTbTreatment");
          final int _cursorIndexOfCareTakerName = _cursor.getColumnIndexOrThrow("careTakerName");
          final int _cursorIndexOfCareTakerPhoneNumber = _cursor.getColumnIndexOrThrow("careTakerPhoneNumber");
          final int _cursorIndexOfCareTakerRelationship = _cursor.getColumnIndexOrThrow("careTakerRelationship");
          final List<Patient> _result = new ArrayList<Patient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Patient _item;
            _item = new Patient();
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpPatientId;
            _tmpPatientId = _cursor.getString(_cursorIndexOfPatientId);
            _item.setPatientId(_tmpPatientId);
            final String _tmpPatientFirstName;
            _tmpPatientFirstName = _cursor.getString(_cursorIndexOfPatientFirstName);
            _item.setPatientFirstName(_tmpPatientFirstName);
            final String _tmpPatientMiddleName;
            _tmpPatientMiddleName = _cursor.getString(_cursorIndexOfPatientMiddleName);
            _item.setPatientMiddleName(_tmpPatientMiddleName);
            final String _tmpPatientSurname;
            _tmpPatientSurname = _cursor.getString(_cursorIndexOfPatientSurname);
            _item.setPatientSurname(_tmpPatientSurname);
            final String _tmpCbhs;
            _tmpCbhs = _cursor.getString(_cursorIndexOfCbhs);
            _item.setCbhs(_tmpCbhs);
            final String _tmpCtcNumber;
            _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
            _item.setCtcNumber(_tmpCtcNumber);
            final String _tmpPhone_number;
            _tmpPhone_number = _cursor.getString(_cursorIndexOfPhoneNumber);
            _item.setPhone_number(_tmpPhone_number);
            final String _tmpWard;
            _tmpWard = _cursor.getString(_cursorIndexOfWard);
            _item.setWard(_tmpWard);
            final String _tmpVillage;
            _tmpVillage = _cursor.getString(_cursorIndexOfVillage);
            _item.setVillage(_tmpVillage);
            final String _tmpHamlet;
            _tmpHamlet = _cursor.getString(_cursorIndexOfHamlet);
            _item.setHamlet(_tmpHamlet);
            final long _tmpDateOfBirth;
            _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
            _item.setDateOfBirth(_tmpDateOfBirth);
            final String _tmpGender;
            _tmpGender = _cursor.getString(_cursorIndexOfGender);
            _item.setGender(_tmpGender);
            final long _tmpDateOfDeath;
            _tmpDateOfDeath = _cursor.getLong(_cursorIndexOfDateOfDeath);
            _item.setDateOfDeath(_tmpDateOfDeath);
            final boolean _tmpHivStatus;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfHivStatus);
            _tmpHivStatus = _tmp_1 != 0;
            _item.setHivStatus(_tmpHivStatus);
            final boolean _tmpCurrentOnTbTreatment;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfCurrentOnTbTreatment);
            _tmpCurrentOnTbTreatment = _tmp_2 != 0;
            _item.setCurrentOnTbTreatment(_tmpCurrentOnTbTreatment);
            final String _tmpCareTakerName;
            _tmpCareTakerName = _cursor.getString(_cursorIndexOfCareTakerName);
            _item.setCareTakerName(_tmpCareTakerName);
            final String _tmpCareTakerPhoneNumber;
            _tmpCareTakerPhoneNumber = _cursor.getString(_cursorIndexOfCareTakerPhoneNumber);
            _item.setCareTakerPhoneNumber(_tmpCareTakerPhoneNumber);
            final String _tmpCareTakerRelationship;
            _tmpCareTakerRelationship = _cursor.getString(_cursorIndexOfCareTakerRelationship);
            _item.setCareTakerRelationship(_tmpCareTakerRelationship);
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
  public Patient getPatientById(String id) {
    final String _sql = "select * from Patient where patientId = ?";
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
      final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patientId");
      final int _cursorIndexOfPatientFirstName = _cursor.getColumnIndexOrThrow("patientFirstName");
      final int _cursorIndexOfPatientMiddleName = _cursor.getColumnIndexOrThrow("patientMiddleName");
      final int _cursorIndexOfPatientSurname = _cursor.getColumnIndexOrThrow("patientSurname");
      final int _cursorIndexOfCbhs = _cursor.getColumnIndexOrThrow("cbhs");
      final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
      final int _cursorIndexOfPhoneNumber = _cursor.getColumnIndexOrThrow("phone_number");
      final int _cursorIndexOfWard = _cursor.getColumnIndexOrThrow("ward");
      final int _cursorIndexOfVillage = _cursor.getColumnIndexOrThrow("village");
      final int _cursorIndexOfHamlet = _cursor.getColumnIndexOrThrow("hamlet");
      final int _cursorIndexOfDateOfBirth = _cursor.getColumnIndexOrThrow("dateOfBirth");
      final int _cursorIndexOfGender = _cursor.getColumnIndexOrThrow("gender");
      final int _cursorIndexOfDateOfDeath = _cursor.getColumnIndexOrThrow("dateOfDeath");
      final int _cursorIndexOfHivStatus = _cursor.getColumnIndexOrThrow("hivStatus");
      final int _cursorIndexOfCurrentOnTbTreatment = _cursor.getColumnIndexOrThrow("currentOnTbTreatment");
      final int _cursorIndexOfCareTakerName = _cursor.getColumnIndexOrThrow("careTakerName");
      final int _cursorIndexOfCareTakerPhoneNumber = _cursor.getColumnIndexOrThrow("careTakerPhoneNumber");
      final int _cursorIndexOfCareTakerRelationship = _cursor.getColumnIndexOrThrow("careTakerRelationship");
      final Patient _result;
      if(_cursor.moveToFirst()) {
        _result = new Patient();
        final Long _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getLong(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
        final String _tmpPatientId;
        _tmpPatientId = _cursor.getString(_cursorIndexOfPatientId);
        _result.setPatientId(_tmpPatientId);
        final String _tmpPatientFirstName;
        _tmpPatientFirstName = _cursor.getString(_cursorIndexOfPatientFirstName);
        _result.setPatientFirstName(_tmpPatientFirstName);
        final String _tmpPatientMiddleName;
        _tmpPatientMiddleName = _cursor.getString(_cursorIndexOfPatientMiddleName);
        _result.setPatientMiddleName(_tmpPatientMiddleName);
        final String _tmpPatientSurname;
        _tmpPatientSurname = _cursor.getString(_cursorIndexOfPatientSurname);
        _result.setPatientSurname(_tmpPatientSurname);
        final String _tmpCbhs;
        _tmpCbhs = _cursor.getString(_cursorIndexOfCbhs);
        _result.setCbhs(_tmpCbhs);
        final String _tmpCtcNumber;
        _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
        _result.setCtcNumber(_tmpCtcNumber);
        final String _tmpPhone_number;
        _tmpPhone_number = _cursor.getString(_cursorIndexOfPhoneNumber);
        _result.setPhone_number(_tmpPhone_number);
        final String _tmpWard;
        _tmpWard = _cursor.getString(_cursorIndexOfWard);
        _result.setWard(_tmpWard);
        final String _tmpVillage;
        _tmpVillage = _cursor.getString(_cursorIndexOfVillage);
        _result.setVillage(_tmpVillage);
        final String _tmpHamlet;
        _tmpHamlet = _cursor.getString(_cursorIndexOfHamlet);
        _result.setHamlet(_tmpHamlet);
        final long _tmpDateOfBirth;
        _tmpDateOfBirth = _cursor.getLong(_cursorIndexOfDateOfBirth);
        _result.setDateOfBirth(_tmpDateOfBirth);
        final String _tmpGender;
        _tmpGender = _cursor.getString(_cursorIndexOfGender);
        _result.setGender(_tmpGender);
        final long _tmpDateOfDeath;
        _tmpDateOfDeath = _cursor.getLong(_cursorIndexOfDateOfDeath);
        _result.setDateOfDeath(_tmpDateOfDeath);
        final boolean _tmpHivStatus;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfHivStatus);
        _tmpHivStatus = _tmp != 0;
        _result.setHivStatus(_tmpHivStatus);
        final boolean _tmpCurrentOnTbTreatment;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfCurrentOnTbTreatment);
        _tmpCurrentOnTbTreatment = _tmp_1 != 0;
        _result.setCurrentOnTbTreatment(_tmpCurrentOnTbTreatment);
        final String _tmpCareTakerName;
        _tmpCareTakerName = _cursor.getString(_cursorIndexOfCareTakerName);
        _result.setCareTakerName(_tmpCareTakerName);
        final String _tmpCareTakerPhoneNumber;
        _tmpCareTakerPhoneNumber = _cursor.getString(_cursorIndexOfCareTakerPhoneNumber);
        _result.setCareTakerPhoneNumber(_tmpCareTakerPhoneNumber);
        final String _tmpCareTakerRelationship;
        _tmpCareTakerRelationship = _cursor.getString(_cursorIndexOfCareTakerRelationship);
        _result.setCareTakerRelationship(_tmpCareTakerRelationship);
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
  public String getPatientName(String patientId) {
    final String _sql = "select patientFirstName || ' ' || patientSurname from Patient where patientId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (patientId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, patientId);
    }
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
