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
import com.softmed.htmr_facility.dom.objects.TbPatient;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class TbPatientModelDao_Impl implements TbPatientModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTbPatient;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfTbPatient;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfTbPatient;

  public TbPatientModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTbPatient = new EntityInsertionAdapter<TbPatient>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `TbPatient`(`tempID`,`patientId`,`tbPatientId`,`patientType`,`transferType`,`referralType`,`veo`,`weight`,`xray`,`makohozi`,`otherTests`,`treatment_type`,`outcome`,`outcomeDate`,`outcomeDetails`,`isPregnant`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TbPatient value) {
        if (value.getTempID() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getTempID());
        }
        stmt.bindLong(2, value.getPatientId());
        stmt.bindLong(3, value.getTbPatientId());
        stmt.bindLong(4, value.getPatientType());
        stmt.bindLong(5, value.getTransferType());
        stmt.bindLong(6, value.getReferralType());
        if (value.getVeo() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getVeo());
        }
        stmt.bindDouble(8, value.getWeight());
        if (value.getXray() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getXray());
        }
        if (value.getMakohozi() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getMakohozi());
        }
        if (value.getOtherTests() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getOtherTests());
        }
        if (value.getTreatment_type() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getTreatment_type());
        }
        if (value.getOutcome() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getOutcome());
        }
        stmt.bindLong(14, value.getOutcomeDate());
        if (value.getOutcomeDetails() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getOutcomeDetails());
        }
        final int _tmp;
        _tmp = value.isPregnant() ? 1 : 0;
        stmt.bindLong(16, _tmp);
      }
    };
    this.__deletionAdapterOfTbPatient = new EntityDeletionOrUpdateAdapter<TbPatient>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `TbPatient` WHERE `patientId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TbPatient value) {
        stmt.bindLong(1, value.getPatientId());
      }
    };
    this.__updateAdapterOfTbPatient = new EntityDeletionOrUpdateAdapter<TbPatient>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `TbPatient` SET `tempID` = ?,`patientId` = ?,`tbPatientId` = ?,`patientType` = ?,`transferType` = ?,`referralType` = ?,`veo` = ?,`weight` = ?,`xray` = ?,`makohozi` = ?,`otherTests` = ?,`treatment_type` = ?,`outcome` = ?,`outcomeDate` = ?,`outcomeDetails` = ?,`isPregnant` = ? WHERE `patientId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TbPatient value) {
        if (value.getTempID() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getTempID());
        }
        stmt.bindLong(2, value.getPatientId());
        stmt.bindLong(3, value.getTbPatientId());
        stmt.bindLong(4, value.getPatientType());
        stmt.bindLong(5, value.getTransferType());
        stmt.bindLong(6, value.getReferralType());
        if (value.getVeo() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getVeo());
        }
        stmt.bindDouble(8, value.getWeight());
        if (value.getXray() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getXray());
        }
        if (value.getMakohozi() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getMakohozi());
        }
        if (value.getOtherTests() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getOtherTests());
        }
        if (value.getTreatment_type() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getTreatment_type());
        }
        if (value.getOutcome() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getOutcome());
        }
        stmt.bindLong(14, value.getOutcomeDate());
        if (value.getOutcomeDetails() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getOutcomeDetails());
        }
        final int _tmp;
        _tmp = value.isPregnant() ? 1 : 0;
        stmt.bindLong(16, _tmp);
        stmt.bindLong(17, value.getPatientId());
      }
    };
  }

  @Override
  public void addPatient(TbPatient tbPatients) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTbPatient.insert(tbPatients);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAPatient(TbPatient patients) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfTbPatient.handle(patients);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateTbPatient(TbPatient patient) {
    __db.beginTransaction();
    try {
      __updateAdapterOfTbPatient.handle(patient);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<TbPatient>> getAllTbPatients() {
    final String _sql = "select * from TbPatient";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<TbPatient>>() {
      private Observer _observer;

      @Override
      protected List<TbPatient> compute() {
        if (_observer == null) {
          _observer = new Observer("TbPatient") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfTempID = _cursor.getColumnIndexOrThrow("tempID");
          final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patientId");
          final int _cursorIndexOfTbPatientId = _cursor.getColumnIndexOrThrow("tbPatientId");
          final int _cursorIndexOfPatientType = _cursor.getColumnIndexOrThrow("patientType");
          final int _cursorIndexOfTransferType = _cursor.getColumnIndexOrThrow("transferType");
          final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
          final int _cursorIndexOfVeo = _cursor.getColumnIndexOrThrow("veo");
          final int _cursorIndexOfWeight = _cursor.getColumnIndexOrThrow("weight");
          final int _cursorIndexOfXray = _cursor.getColumnIndexOrThrow("xray");
          final int _cursorIndexOfMakohozi = _cursor.getColumnIndexOrThrow("makohozi");
          final int _cursorIndexOfOtherTests = _cursor.getColumnIndexOrThrow("otherTests");
          final int _cursorIndexOfTreatmentType = _cursor.getColumnIndexOrThrow("treatment_type");
          final int _cursorIndexOfOutcome = _cursor.getColumnIndexOrThrow("outcome");
          final int _cursorIndexOfOutcomeDate = _cursor.getColumnIndexOrThrow("outcomeDate");
          final int _cursorIndexOfOutcomeDetails = _cursor.getColumnIndexOrThrow("outcomeDetails");
          final int _cursorIndexOfIsPregnant = _cursor.getColumnIndexOrThrow("isPregnant");
          final List<TbPatient> _result = new ArrayList<TbPatient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TbPatient _item;
            _item = new TbPatient();
            final String _tmpTempID;
            _tmpTempID = _cursor.getString(_cursorIndexOfTempID);
            _item.setTempID(_tmpTempID);
            final Long _tmpPatientId;
            _tmpPatientId = _cursor.getLong(_cursorIndexOfPatientId);
            _item.setPatientId(_tmpPatientId);
            final Long _tmpTbPatientId;
            _tmpTbPatientId = _cursor.getLong(_cursorIndexOfTbPatientId);
            _item.setTbPatientId(_tmpTbPatientId);
            final int _tmpPatientType;
            _tmpPatientType = _cursor.getInt(_cursorIndexOfPatientType);
            _item.setPatientType(_tmpPatientType);
            final int _tmpTransferType;
            _tmpTransferType = _cursor.getInt(_cursorIndexOfTransferType);
            _item.setTransferType(_tmpTransferType);
            final int _tmpReferralType;
            _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
            _item.setReferralType(_tmpReferralType);
            final String _tmpVeo;
            _tmpVeo = _cursor.getString(_cursorIndexOfVeo);
            _item.setVeo(_tmpVeo);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            _item.setWeight(_tmpWeight);
            final String _tmpXray;
            _tmpXray = _cursor.getString(_cursorIndexOfXray);
            _item.setXray(_tmpXray);
            final String _tmpMakohozi;
            _tmpMakohozi = _cursor.getString(_cursorIndexOfMakohozi);
            _item.setMakohozi(_tmpMakohozi);
            final String _tmpOtherTests;
            _tmpOtherTests = _cursor.getString(_cursorIndexOfOtherTests);
            _item.setOtherTests(_tmpOtherTests);
            final String _tmpTreatment_type;
            _tmpTreatment_type = _cursor.getString(_cursorIndexOfTreatmentType);
            _item.setTreatment_type(_tmpTreatment_type);
            final String _tmpOutcome;
            _tmpOutcome = _cursor.getString(_cursorIndexOfOutcome);
            _item.setOutcome(_tmpOutcome);
            final long _tmpOutcomeDate;
            _tmpOutcomeDate = _cursor.getLong(_cursorIndexOfOutcomeDate);
            _item.setOutcomeDate(_tmpOutcomeDate);
            final String _tmpOutcomeDetails;
            _tmpOutcomeDetails = _cursor.getString(_cursorIndexOfOutcomeDetails);
            _item.setOutcomeDetails(_tmpOutcomeDetails);
            final boolean _tmpIsPregnant;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPregnant);
            _tmpIsPregnant = _tmp != 0;
            _item.setPregnant(_tmpIsPregnant);
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
  public TbPatient getTbPatientById(String id) {
    final String _sql = "select * from TbPatient where patientId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfTempID = _cursor.getColumnIndexOrThrow("tempID");
      final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patientId");
      final int _cursorIndexOfTbPatientId = _cursor.getColumnIndexOrThrow("tbPatientId");
      final int _cursorIndexOfPatientType = _cursor.getColumnIndexOrThrow("patientType");
      final int _cursorIndexOfTransferType = _cursor.getColumnIndexOrThrow("transferType");
      final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
      final int _cursorIndexOfVeo = _cursor.getColumnIndexOrThrow("veo");
      final int _cursorIndexOfWeight = _cursor.getColumnIndexOrThrow("weight");
      final int _cursorIndexOfXray = _cursor.getColumnIndexOrThrow("xray");
      final int _cursorIndexOfMakohozi = _cursor.getColumnIndexOrThrow("makohozi");
      final int _cursorIndexOfOtherTests = _cursor.getColumnIndexOrThrow("otherTests");
      final int _cursorIndexOfTreatmentType = _cursor.getColumnIndexOrThrow("treatment_type");
      final int _cursorIndexOfOutcome = _cursor.getColumnIndexOrThrow("outcome");
      final int _cursorIndexOfOutcomeDate = _cursor.getColumnIndexOrThrow("outcomeDate");
      final int _cursorIndexOfOutcomeDetails = _cursor.getColumnIndexOrThrow("outcomeDetails");
      final int _cursorIndexOfIsPregnant = _cursor.getColumnIndexOrThrow("isPregnant");
      final TbPatient _result;
      if(_cursor.moveToFirst()) {
        _result = new TbPatient();
        final String _tmpTempID;
        _tmpTempID = _cursor.getString(_cursorIndexOfTempID);
        _result.setTempID(_tmpTempID);
        final Long _tmpPatientId;
        _tmpPatientId = _cursor.getLong(_cursorIndexOfPatientId);
        _result.setPatientId(_tmpPatientId);
        final Long _tmpTbPatientId;
        _tmpTbPatientId = _cursor.getLong(_cursorIndexOfTbPatientId);
        _result.setTbPatientId(_tmpTbPatientId);
        final int _tmpPatientType;
        _tmpPatientType = _cursor.getInt(_cursorIndexOfPatientType);
        _result.setPatientType(_tmpPatientType);
        final int _tmpTransferType;
        _tmpTransferType = _cursor.getInt(_cursorIndexOfTransferType);
        _result.setTransferType(_tmpTransferType);
        final int _tmpReferralType;
        _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
        _result.setReferralType(_tmpReferralType);
        final String _tmpVeo;
        _tmpVeo = _cursor.getString(_cursorIndexOfVeo);
        _result.setVeo(_tmpVeo);
        final double _tmpWeight;
        _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
        _result.setWeight(_tmpWeight);
        final String _tmpXray;
        _tmpXray = _cursor.getString(_cursorIndexOfXray);
        _result.setXray(_tmpXray);
        final String _tmpMakohozi;
        _tmpMakohozi = _cursor.getString(_cursorIndexOfMakohozi);
        _result.setMakohozi(_tmpMakohozi);
        final String _tmpOtherTests;
        _tmpOtherTests = _cursor.getString(_cursorIndexOfOtherTests);
        _result.setOtherTests(_tmpOtherTests);
        final String _tmpTreatment_type;
        _tmpTreatment_type = _cursor.getString(_cursorIndexOfTreatmentType);
        _result.setTreatment_type(_tmpTreatment_type);
        final String _tmpOutcome;
        _tmpOutcome = _cursor.getString(_cursorIndexOfOutcome);
        _result.setOutcome(_tmpOutcome);
        final long _tmpOutcomeDate;
        _tmpOutcomeDate = _cursor.getLong(_cursorIndexOfOutcomeDate);
        _result.setOutcomeDate(_tmpOutcomeDate);
        final String _tmpOutcomeDetails;
        _tmpOutcomeDetails = _cursor.getString(_cursorIndexOfOutcomeDetails);
        _result.setOutcomeDetails(_tmpOutcomeDetails);
        final boolean _tmpIsPregnant;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPregnant);
        _tmpIsPregnant = _tmp != 0;
        _result.setPregnant(_tmpIsPregnant);
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
