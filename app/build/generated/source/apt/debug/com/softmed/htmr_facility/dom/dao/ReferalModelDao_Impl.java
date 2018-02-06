package com.softmed.htmr_facility.dom.dao;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.util.StringUtil;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.softmed.htmr_facility.dom.objects.Referral;
import com.softmed.htmr_facility.utils.ListStringConverter;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class ReferalModelDao_Impl implements ReferalModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfReferral;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfReferral;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfReferral;

  public ReferalModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReferral = new EntityInsertionAdapter<Referral>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Referral`(`id`,`patient_id`,`referral_id`,`communityBasedHivService`,`referralReason`,`serviceId`,`referralUUID`,`ctcNumber`,`serviceProviderUIID`,`serviceProviderGroup`,`villageLeader`,`referralDate`,`facilityId`,`fromFacilityId`,`serviceIndicatorIds`,`referralSource`,`referralType`,`referralStatus`,`testResults`,`serviceGivenToPatient`,`otherNotesAndAdvices`,`otherClinicalInformation`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Referral value) {
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
        if (value.getReferral_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getReferral_id());
        }
        if (value.getCommunityBasedHivService() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCommunityBasedHivService());
        }
        if (value.getReferralReason() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getReferralReason());
        }
        stmt.bindLong(6, value.getServiceId());
        if (value.getReferralUUID() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getReferralUUID());
        }
        if (value.getCtcNumber() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getCtcNumber());
        }
        if (value.getServiceProviderUIID() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getServiceProviderUIID());
        }
        if (value.getServiceProviderGroup() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getServiceProviderGroup());
        }
        if (value.getVillageLeader() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getVillageLeader());
        }
        stmt.bindLong(12, value.getReferralDate());
        if (value.getFacilityId() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getFacilityId());
        }
        if (value.getFromFacilityId() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getFromFacilityId());
        }
        final String _tmp;
        _tmp = ListStringConverter.someObjectListToString(value.getServiceIndicatorIds());
        if (_tmp == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, _tmp);
        }
        stmt.bindLong(16, value.getReferralSource());
        stmt.bindLong(17, value.getReferralType());
        stmt.bindLong(18, value.getReferralStatus());
        final int _tmp_1;
        _tmp_1 = value.isTestResults() ? 1 : 0;
        stmt.bindLong(19, _tmp_1);
        if (value.getServiceGivenToPatient() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getServiceGivenToPatient());
        }
        if (value.getOtherNotesAndAdvices() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getOtherNotesAndAdvices());
        }
        if (value.getOtherClinicalInformation() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getOtherClinicalInformation());
        }
      }
    };
    this.__deletionAdapterOfReferral = new EntityDeletionOrUpdateAdapter<Referral>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Referral` WHERE `referral_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Referral value) {
        if (value.getReferral_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getReferral_id());
        }
      }
    };
    this.__updateAdapterOfReferral = new EntityDeletionOrUpdateAdapter<Referral>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Referral` SET `id` = ?,`patient_id` = ?,`referral_id` = ?,`communityBasedHivService` = ?,`referralReason` = ?,`serviceId` = ?,`referralUUID` = ?,`ctcNumber` = ?,`serviceProviderUIID` = ?,`serviceProviderGroup` = ?,`villageLeader` = ?,`referralDate` = ?,`facilityId` = ?,`fromFacilityId` = ?,`serviceIndicatorIds` = ?,`referralSource` = ?,`referralType` = ?,`referralStatus` = ?,`testResults` = ?,`serviceGivenToPatient` = ?,`otherNotesAndAdvices` = ?,`otherClinicalInformation` = ? WHERE `referral_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Referral value) {
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
        if (value.getReferral_id() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getReferral_id());
        }
        if (value.getCommunityBasedHivService() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCommunityBasedHivService());
        }
        if (value.getReferralReason() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getReferralReason());
        }
        stmt.bindLong(6, value.getServiceId());
        if (value.getReferralUUID() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getReferralUUID());
        }
        if (value.getCtcNumber() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getCtcNumber());
        }
        if (value.getServiceProviderUIID() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getServiceProviderUIID());
        }
        if (value.getServiceProviderGroup() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getServiceProviderGroup());
        }
        if (value.getVillageLeader() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getVillageLeader());
        }
        stmt.bindLong(12, value.getReferralDate());
        if (value.getFacilityId() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getFacilityId());
        }
        if (value.getFromFacilityId() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getFromFacilityId());
        }
        final String _tmp;
        _tmp = ListStringConverter.someObjectListToString(value.getServiceIndicatorIds());
        if (_tmp == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, _tmp);
        }
        stmt.bindLong(16, value.getReferralSource());
        stmt.bindLong(17, value.getReferralType());
        stmt.bindLong(18, value.getReferralStatus());
        final int _tmp_1;
        _tmp_1 = value.isTestResults() ? 1 : 0;
        stmt.bindLong(19, _tmp_1);
        if (value.getServiceGivenToPatient() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getServiceGivenToPatient());
        }
        if (value.getOtherNotesAndAdvices() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getOtherNotesAndAdvices());
        }
        if (value.getOtherClinicalInformation() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getOtherClinicalInformation());
        }
        if (value.getReferral_id() == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindString(23, value.getReferral_id());
        }
      }
    };
  }

  @Override
  public void addReferal(Referral referral) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfReferral.insert(referral);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteReferal(Referral referral) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfReferral.handle(referral);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateReferral(Referral referral) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfReferral.handle(referral);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Referral> getAllReferralsOfThisFacility(String fromHfId) {
    final String _sql = "select * from Referral where fromFacilityId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (fromHfId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fromHfId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patient_id");
      final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
      final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
      final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
      final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
      final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
      final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
      final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
      final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
      final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
      final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
      final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
      final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
      final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
      final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
      final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
      final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
      final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
      final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
      final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
      final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
      final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Referral _item;
        _item = new Referral();
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
        final String _tmpReferral_id;
        _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
        _item.setReferral_id(_tmpReferral_id);
        final String _tmpCommunityBasedHivService;
        _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
        _item.setCommunityBasedHivService(_tmpCommunityBasedHivService);
        final String _tmpReferralReason;
        _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
        _item.setReferralReason(_tmpReferralReason);
        final int _tmpServiceId;
        _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
        _item.setServiceId(_tmpServiceId);
        final String _tmpReferralUUID;
        _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
        _item.setReferralUUID(_tmpReferralUUID);
        final String _tmpCtcNumber;
        _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
        _item.setCtcNumber(_tmpCtcNumber);
        final String _tmpServiceProviderUIID;
        _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
        _item.setServiceProviderUIID(_tmpServiceProviderUIID);
        final String _tmpServiceProviderGroup;
        _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
        _item.setServiceProviderGroup(_tmpServiceProviderGroup);
        final String _tmpVillageLeader;
        _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
        _item.setVillageLeader(_tmpVillageLeader);
        final long _tmpReferralDate;
        _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
        _item.setReferralDate(_tmpReferralDate);
        final String _tmpFacilityId;
        _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
        _item.setFacilityId(_tmpFacilityId);
        final String _tmpFromFacilityId;
        _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
        _item.setFromFacilityId(_tmpFromFacilityId);
        final List<Long> _tmpServiceIndicatorIds;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
        _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
        _item.setServiceIndicatorIds(_tmpServiceIndicatorIds);
        final int _tmpReferralSource;
        _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
        _item.setReferralSource(_tmpReferralSource);
        final int _tmpReferralType;
        _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
        _item.setReferralType(_tmpReferralType);
        final int _tmpReferralStatus;
        _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
        _item.setReferralStatus(_tmpReferralStatus);
        final boolean _tmpTestResults;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
        _tmpTestResults = _tmp_1 != 0;
        _item.setTestResults(_tmpTestResults);
        final String _tmpServiceGivenToPatient;
        _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
        _item.setServiceGivenToPatient(_tmpServiceGivenToPatient);
        final String _tmpOtherNotesAndAdvices;
        _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
        _item.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
        final String _tmpOtherClinicalInformation;
        _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
        _item.setOtherClinicalInformation(_tmpOtherClinicalInformation);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Referral>> getAllReferals(int serviceId) {
    final String _sql = "select * from Referral where serviceId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceId);
    return new ComputableLiveData<List<Referral>>() {
      private Observer _observer;

      @Override
      protected List<Referral> compute() {
        if (_observer == null) {
          _observer = new Observer("Referral") {
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
          final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
          final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
          final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
          final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
          final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
          final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
          final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
          final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
          final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
          final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
          final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
          final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
          final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
          final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
          final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
          final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
          final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
          final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
          final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
          final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
          final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Referral _item;
            _item = new Referral();
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
            final String _tmpReferral_id;
            _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
            _item.setReferral_id(_tmpReferral_id);
            final String _tmpCommunityBasedHivService;
            _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
            _item.setCommunityBasedHivService(_tmpCommunityBasedHivService);
            final String _tmpReferralReason;
            _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
            _item.setReferralReason(_tmpReferralReason);
            final int _tmpServiceId;
            _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
            _item.setServiceId(_tmpServiceId);
            final String _tmpReferralUUID;
            _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
            _item.setReferralUUID(_tmpReferralUUID);
            final String _tmpCtcNumber;
            _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
            _item.setCtcNumber(_tmpCtcNumber);
            final String _tmpServiceProviderUIID;
            _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
            _item.setServiceProviderUIID(_tmpServiceProviderUIID);
            final String _tmpServiceProviderGroup;
            _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
            _item.setServiceProviderGroup(_tmpServiceProviderGroup);
            final String _tmpVillageLeader;
            _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
            _item.setVillageLeader(_tmpVillageLeader);
            final long _tmpReferralDate;
            _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
            _item.setReferralDate(_tmpReferralDate);
            final String _tmpFacilityId;
            _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
            _item.setFacilityId(_tmpFacilityId);
            final String _tmpFromFacilityId;
            _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
            _item.setFromFacilityId(_tmpFromFacilityId);
            final List<Long> _tmpServiceIndicatorIds;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
            _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
            _item.setServiceIndicatorIds(_tmpServiceIndicatorIds);
            final int _tmpReferralSource;
            _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
            _item.setReferralSource(_tmpReferralSource);
            final int _tmpReferralType;
            _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
            _item.setReferralType(_tmpReferralType);
            final int _tmpReferralStatus;
            _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
            _item.setReferralStatus(_tmpReferralStatus);
            final boolean _tmpTestResults;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
            _tmpTestResults = _tmp_1 != 0;
            _item.setTestResults(_tmpTestResults);
            final String _tmpServiceGivenToPatient;
            _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
            _item.setServiceGivenToPatient(_tmpServiceGivenToPatient);
            final String _tmpOtherNotesAndAdvices;
            _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
            _item.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
            final String _tmpOtherClinicalInformation;
            _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
            _item.setOtherClinicalInformation(_tmpOtherClinicalInformation);
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
  public LiveData<List<Referral>> getAllReferalsBySource(int[] sourceID) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("select * from Referral where referralType in (");
    final int _inputSize = sourceID.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int _item : sourceID) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    return new ComputableLiveData<List<Referral>>() {
      private Observer _observer;

      @Override
      protected List<Referral> compute() {
        if (_observer == null) {
          _observer = new Observer("Referral") {
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
          final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
          final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
          final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
          final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
          final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
          final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
          final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
          final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
          final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
          final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
          final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
          final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
          final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
          final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
          final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
          final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
          final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
          final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
          final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
          final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
          final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Referral _item_1;
            _item_1 = new Referral();
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            _item_1.setId(_tmpId);
            final String _tmpPatient_id;
            _tmpPatient_id = _cursor.getString(_cursorIndexOfPatientId);
            _item_1.setPatient_id(_tmpPatient_id);
            final String _tmpReferral_id;
            _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
            _item_1.setReferral_id(_tmpReferral_id);
            final String _tmpCommunityBasedHivService;
            _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
            _item_1.setCommunityBasedHivService(_tmpCommunityBasedHivService);
            final String _tmpReferralReason;
            _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
            _item_1.setReferralReason(_tmpReferralReason);
            final int _tmpServiceId;
            _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
            _item_1.setServiceId(_tmpServiceId);
            final String _tmpReferralUUID;
            _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
            _item_1.setReferralUUID(_tmpReferralUUID);
            final String _tmpCtcNumber;
            _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
            _item_1.setCtcNumber(_tmpCtcNumber);
            final String _tmpServiceProviderUIID;
            _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
            _item_1.setServiceProviderUIID(_tmpServiceProviderUIID);
            final String _tmpServiceProviderGroup;
            _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
            _item_1.setServiceProviderGroup(_tmpServiceProviderGroup);
            final String _tmpVillageLeader;
            _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
            _item_1.setVillageLeader(_tmpVillageLeader);
            final long _tmpReferralDate;
            _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
            _item_1.setReferralDate(_tmpReferralDate);
            final String _tmpFacilityId;
            _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
            _item_1.setFacilityId(_tmpFacilityId);
            final String _tmpFromFacilityId;
            _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
            _item_1.setFromFacilityId(_tmpFromFacilityId);
            final List<Long> _tmpServiceIndicatorIds;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
            _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
            _item_1.setServiceIndicatorIds(_tmpServiceIndicatorIds);
            final int _tmpReferralSource;
            _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
            _item_1.setReferralSource(_tmpReferralSource);
            final int _tmpReferralType;
            _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
            _item_1.setReferralType(_tmpReferralType);
            final int _tmpReferralStatus;
            _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
            _item_1.setReferralStatus(_tmpReferralStatus);
            final boolean _tmpTestResults;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
            _tmpTestResults = _tmp_1 != 0;
            _item_1.setTestResults(_tmpTestResults);
            final String _tmpServiceGivenToPatient;
            _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
            _item_1.setServiceGivenToPatient(_tmpServiceGivenToPatient);
            final String _tmpOtherNotesAndAdvices;
            _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
            _item_1.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
            final String _tmpOtherClinicalInformation;
            _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
            _item_1.setOtherClinicalInformation(_tmpOtherClinicalInformation);
            _result.add(_item_1);
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
  public LiveData<List<Referral>> getReferralsBySourceId(int serviceId, int[] SourceID) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("select * from Referral where serviceId = ");
    _stringBuilder.append("?");
    _stringBuilder.append(" and referralType in (");
    final int _inputSize = SourceID.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 1 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceId);
    _argIndex = 2;
    for (int _item : SourceID) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    return new ComputableLiveData<List<Referral>>() {
      private Observer _observer;

      @Override
      protected List<Referral> compute() {
        if (_observer == null) {
          _observer = new Observer("Referral") {
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
          final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
          final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
          final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
          final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
          final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
          final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
          final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
          final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
          final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
          final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
          final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
          final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
          final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
          final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
          final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
          final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
          final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
          final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
          final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
          final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
          final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Referral _item_1;
            _item_1 = new Referral();
            final Long _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getLong(_cursorIndexOfId);
            }
            _item_1.setId(_tmpId);
            final String _tmpPatient_id;
            _tmpPatient_id = _cursor.getString(_cursorIndexOfPatientId);
            _item_1.setPatient_id(_tmpPatient_id);
            final String _tmpReferral_id;
            _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
            _item_1.setReferral_id(_tmpReferral_id);
            final String _tmpCommunityBasedHivService;
            _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
            _item_1.setCommunityBasedHivService(_tmpCommunityBasedHivService);
            final String _tmpReferralReason;
            _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
            _item_1.setReferralReason(_tmpReferralReason);
            final int _tmpServiceId;
            _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
            _item_1.setServiceId(_tmpServiceId);
            final String _tmpReferralUUID;
            _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
            _item_1.setReferralUUID(_tmpReferralUUID);
            final String _tmpCtcNumber;
            _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
            _item_1.setCtcNumber(_tmpCtcNumber);
            final String _tmpServiceProviderUIID;
            _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
            _item_1.setServiceProviderUIID(_tmpServiceProviderUIID);
            final String _tmpServiceProviderGroup;
            _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
            _item_1.setServiceProviderGroup(_tmpServiceProviderGroup);
            final String _tmpVillageLeader;
            _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
            _item_1.setVillageLeader(_tmpVillageLeader);
            final long _tmpReferralDate;
            _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
            _item_1.setReferralDate(_tmpReferralDate);
            final String _tmpFacilityId;
            _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
            _item_1.setFacilityId(_tmpFacilityId);
            final String _tmpFromFacilityId;
            _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
            _item_1.setFromFacilityId(_tmpFromFacilityId);
            final List<Long> _tmpServiceIndicatorIds;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
            _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
            _item_1.setServiceIndicatorIds(_tmpServiceIndicatorIds);
            final int _tmpReferralSource;
            _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
            _item_1.setReferralSource(_tmpReferralSource);
            final int _tmpReferralType;
            _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
            _item_1.setReferralType(_tmpReferralType);
            final int _tmpReferralStatus;
            _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
            _item_1.setReferralStatus(_tmpReferralStatus);
            final boolean _tmpTestResults;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
            _tmpTestResults = _tmp_1 != 0;
            _item_1.setTestResults(_tmpTestResults);
            final String _tmpServiceGivenToPatient;
            _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
            _item_1.setServiceGivenToPatient(_tmpServiceGivenToPatient);
            final String _tmpOtherNotesAndAdvices;
            _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
            _item_1.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
            final String _tmpOtherClinicalInformation;
            _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
            _item_1.setOtherClinicalInformation(_tmpOtherClinicalInformation);
            _result.add(_item_1);
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
  public LiveData<List<Referral>> getReferredClients(int serviceId, String fromFacilityId) {
    final String _sql = "select * from Referral where referralSource = ? and fromFacilityId = ? order by referralStatus desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceId);
    _argIndex = 2;
    if (fromFacilityId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fromFacilityId);
    }
    return new ComputableLiveData<List<Referral>>() {
      private Observer _observer;

      @Override
      protected List<Referral> compute() {
        if (_observer == null) {
          _observer = new Observer("Referral") {
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
          final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
          final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
          final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
          final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
          final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
          final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
          final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
          final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
          final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
          final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
          final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
          final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
          final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
          final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
          final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
          final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
          final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
          final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
          final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
          final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
          final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Referral _item;
            _item = new Referral();
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
            final String _tmpReferral_id;
            _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
            _item.setReferral_id(_tmpReferral_id);
            final String _tmpCommunityBasedHivService;
            _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
            _item.setCommunityBasedHivService(_tmpCommunityBasedHivService);
            final String _tmpReferralReason;
            _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
            _item.setReferralReason(_tmpReferralReason);
            final int _tmpServiceId;
            _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
            _item.setServiceId(_tmpServiceId);
            final String _tmpReferralUUID;
            _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
            _item.setReferralUUID(_tmpReferralUUID);
            final String _tmpCtcNumber;
            _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
            _item.setCtcNumber(_tmpCtcNumber);
            final String _tmpServiceProviderUIID;
            _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
            _item.setServiceProviderUIID(_tmpServiceProviderUIID);
            final String _tmpServiceProviderGroup;
            _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
            _item.setServiceProviderGroup(_tmpServiceProviderGroup);
            final String _tmpVillageLeader;
            _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
            _item.setVillageLeader(_tmpVillageLeader);
            final long _tmpReferralDate;
            _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
            _item.setReferralDate(_tmpReferralDate);
            final String _tmpFacilityId;
            _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
            _item.setFacilityId(_tmpFacilityId);
            final String _tmpFromFacilityId;
            _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
            _item.setFromFacilityId(_tmpFromFacilityId);
            final List<Long> _tmpServiceIndicatorIds;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
            _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
            _item.setServiceIndicatorIds(_tmpServiceIndicatorIds);
            final int _tmpReferralSource;
            _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
            _item.setReferralSource(_tmpReferralSource);
            final int _tmpReferralType;
            _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
            _item.setReferralType(_tmpReferralType);
            final int _tmpReferralStatus;
            _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
            _item.setReferralStatus(_tmpReferralStatus);
            final boolean _tmpTestResults;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
            _tmpTestResults = _tmp_1 != 0;
            _item.setTestResults(_tmpTestResults);
            final String _tmpServiceGivenToPatient;
            _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
            _item.setServiceGivenToPatient(_tmpServiceGivenToPatient);
            final String _tmpOtherNotesAndAdvices;
            _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
            _item.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
            final String _tmpOtherClinicalInformation;
            _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
            _item.setOtherClinicalInformation(_tmpOtherClinicalInformation);
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
  public int geCountPendingReferalFeedback(int serviceId, String fromFacilityId) {
    final String _sql = "select count(*) from Referral where referralStatus = 0 and serviceId = ? and fromFacilityId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceId);
    _argIndex = 2;
    if (fromFacilityId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fromFacilityId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Referral>> getUnattendedReferals(int serviceId) {
    final String _sql = "select * from Referral where referralStatus = 0 and serviceId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceId);
    return new ComputableLiveData<List<Referral>>() {
      private Observer _observer;

      @Override
      protected List<Referral> compute() {
        if (_observer == null) {
          _observer = new Observer("Referral") {
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
          final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
          final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
          final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
          final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
          final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
          final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
          final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
          final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
          final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
          final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
          final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
          final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
          final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
          final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
          final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
          final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
          final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
          final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
          final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
          final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
          final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Referral _item;
            _item = new Referral();
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
            final String _tmpReferral_id;
            _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
            _item.setReferral_id(_tmpReferral_id);
            final String _tmpCommunityBasedHivService;
            _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
            _item.setCommunityBasedHivService(_tmpCommunityBasedHivService);
            final String _tmpReferralReason;
            _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
            _item.setReferralReason(_tmpReferralReason);
            final int _tmpServiceId;
            _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
            _item.setServiceId(_tmpServiceId);
            final String _tmpReferralUUID;
            _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
            _item.setReferralUUID(_tmpReferralUUID);
            final String _tmpCtcNumber;
            _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
            _item.setCtcNumber(_tmpCtcNumber);
            final String _tmpServiceProviderUIID;
            _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
            _item.setServiceProviderUIID(_tmpServiceProviderUIID);
            final String _tmpServiceProviderGroup;
            _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
            _item.setServiceProviderGroup(_tmpServiceProviderGroup);
            final String _tmpVillageLeader;
            _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
            _item.setVillageLeader(_tmpVillageLeader);
            final long _tmpReferralDate;
            _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
            _item.setReferralDate(_tmpReferralDate);
            final String _tmpFacilityId;
            _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
            _item.setFacilityId(_tmpFacilityId);
            final String _tmpFromFacilityId;
            _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
            _item.setFromFacilityId(_tmpFromFacilityId);
            final List<Long> _tmpServiceIndicatorIds;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
            _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
            _item.setServiceIndicatorIds(_tmpServiceIndicatorIds);
            final int _tmpReferralSource;
            _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
            _item.setReferralSource(_tmpReferralSource);
            final int _tmpReferralType;
            _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
            _item.setReferralType(_tmpReferralType);
            final int _tmpReferralStatus;
            _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
            _item.setReferralStatus(_tmpReferralStatus);
            final boolean _tmpTestResults;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
            _tmpTestResults = _tmp_1 != 0;
            _item.setTestResults(_tmpTestResults);
            final String _tmpServiceGivenToPatient;
            _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
            _item.setServiceGivenToPatient(_tmpServiceGivenToPatient);
            final String _tmpOtherNotesAndAdvices;
            _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
            _item.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
            final String _tmpOtherClinicalInformation;
            _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
            _item.setOtherClinicalInformation(_tmpOtherClinicalInformation);
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
  public int getCountReferralsBySource(int[] sourceID) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("select count(*) from Referral where referralStatus = 0 and referralType in (");
    final int _inputSize = sourceID.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int _item : sourceID) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getCountReferralsByService(int service) {
    final String _sql = "select count(*) from Referral where referralStatus = 0 and referralSource = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, service);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int geCounttUnattendedReferals() {
    final String _sql = "select count(*) from Referral where referralStatus = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int geCounttUnattendedReferalsByService(int serviceId) {
    final String _sql = "select count(*) from Referral where referralStatus = 0 and serviceId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getCountReferralsByType(int serviceId, int[] referralType) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("select count(*) from Referral where referralStatus = 0 and serviceId = ");
    _stringBuilder.append("?");
    _stringBuilder.append(" and referralType in (");
    final int _inputSize = referralType.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 1 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceId);
    _argIndex = 2;
    for (int _item : referralType) {
      _statement.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Referral>> getReferalsByPatientId(String id) {
    final String _sql = "select * from Referral where patient_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    return new ComputableLiveData<List<Referral>>() {
      private Observer _observer;

      @Override
      protected List<Referral> compute() {
        if (_observer == null) {
          _observer = new Observer("Referral") {
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
          final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
          final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
          final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
          final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
          final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
          final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
          final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
          final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
          final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
          final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
          final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
          final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
          final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
          final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
          final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
          final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
          final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
          final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
          final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
          final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
          final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Referral _item;
            _item = new Referral();
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
            final String _tmpReferral_id;
            _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
            _item.setReferral_id(_tmpReferral_id);
            final String _tmpCommunityBasedHivService;
            _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
            _item.setCommunityBasedHivService(_tmpCommunityBasedHivService);
            final String _tmpReferralReason;
            _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
            _item.setReferralReason(_tmpReferralReason);
            final int _tmpServiceId;
            _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
            _item.setServiceId(_tmpServiceId);
            final String _tmpReferralUUID;
            _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
            _item.setReferralUUID(_tmpReferralUUID);
            final String _tmpCtcNumber;
            _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
            _item.setCtcNumber(_tmpCtcNumber);
            final String _tmpServiceProviderUIID;
            _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
            _item.setServiceProviderUIID(_tmpServiceProviderUIID);
            final String _tmpServiceProviderGroup;
            _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
            _item.setServiceProviderGroup(_tmpServiceProviderGroup);
            final String _tmpVillageLeader;
            _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
            _item.setVillageLeader(_tmpVillageLeader);
            final long _tmpReferralDate;
            _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
            _item.setReferralDate(_tmpReferralDate);
            final String _tmpFacilityId;
            _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
            _item.setFacilityId(_tmpFacilityId);
            final String _tmpFromFacilityId;
            _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
            _item.setFromFacilityId(_tmpFromFacilityId);
            final List<Long> _tmpServiceIndicatorIds;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
            _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
            _item.setServiceIndicatorIds(_tmpServiceIndicatorIds);
            final int _tmpReferralSource;
            _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
            _item.setReferralSource(_tmpReferralSource);
            final int _tmpReferralType;
            _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
            _item.setReferralType(_tmpReferralType);
            final int _tmpReferralStatus;
            _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
            _item.setReferralStatus(_tmpReferralStatus);
            final boolean _tmpTestResults;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
            _tmpTestResults = _tmp_1 != 0;
            _item.setTestResults(_tmpTestResults);
            final String _tmpServiceGivenToPatient;
            _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
            _item.setServiceGivenToPatient(_tmpServiceGivenToPatient);
            final String _tmpOtherNotesAndAdvices;
            _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
            _item.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
            final String _tmpOtherClinicalInformation;
            _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
            _item.setOtherClinicalInformation(_tmpOtherClinicalInformation);
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
  public Referral getReferalById(String id) {
    final String _sql = "select * from Referral where referral_id = ?";
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
      final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
      final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
      final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
      final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
      final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
      final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
      final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
      final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
      final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
      final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
      final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
      final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
      final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
      final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
      final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
      final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
      final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
      final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
      final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
      final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
      final Referral _result;
      if(_cursor.moveToFirst()) {
        _result = new Referral();
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
        final String _tmpReferral_id;
        _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
        _result.setReferral_id(_tmpReferral_id);
        final String _tmpCommunityBasedHivService;
        _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
        _result.setCommunityBasedHivService(_tmpCommunityBasedHivService);
        final String _tmpReferralReason;
        _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
        _result.setReferralReason(_tmpReferralReason);
        final int _tmpServiceId;
        _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
        _result.setServiceId(_tmpServiceId);
        final String _tmpReferralUUID;
        _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
        _result.setReferralUUID(_tmpReferralUUID);
        final String _tmpCtcNumber;
        _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
        _result.setCtcNumber(_tmpCtcNumber);
        final String _tmpServiceProviderUIID;
        _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
        _result.setServiceProviderUIID(_tmpServiceProviderUIID);
        final String _tmpServiceProviderGroup;
        _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
        _result.setServiceProviderGroup(_tmpServiceProviderGroup);
        final String _tmpVillageLeader;
        _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
        _result.setVillageLeader(_tmpVillageLeader);
        final long _tmpReferralDate;
        _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
        _result.setReferralDate(_tmpReferralDate);
        final String _tmpFacilityId;
        _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
        _result.setFacilityId(_tmpFacilityId);
        final String _tmpFromFacilityId;
        _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
        _result.setFromFacilityId(_tmpFromFacilityId);
        final List<Long> _tmpServiceIndicatorIds;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
        _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
        _result.setServiceIndicatorIds(_tmpServiceIndicatorIds);
        final int _tmpReferralSource;
        _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
        _result.setReferralSource(_tmpReferralSource);
        final int _tmpReferralType;
        _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
        _result.setReferralType(_tmpReferralType);
        final int _tmpReferralStatus;
        _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
        _result.setReferralStatus(_tmpReferralStatus);
        final boolean _tmpTestResults;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
        _tmpTestResults = _tmp_1 != 0;
        _result.setTestResults(_tmpTestResults);
        final String _tmpServiceGivenToPatient;
        _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
        _result.setServiceGivenToPatient(_tmpServiceGivenToPatient);
        final String _tmpOtherNotesAndAdvices;
        _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
        _result.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
        final String _tmpOtherClinicalInformation;
        _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
        _result.setOtherClinicalInformation(_tmpOtherClinicalInformation);
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
  public List<Referral> getFilteredReferal(String name, String lastName, int status,
      int serviceId) {
    final String _sql = "select * from Referral inner join Patient on Referral.patient_id = Patient.patientId where Referral.referralStatus = ? and Referral.serviceId = ? and Patient.patientFirstName like ? COLLATE NOCASE and Patient.patientSurname like ? COLLATE NOCASE";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, status);
    _argIndex = 2;
    _statement.bindLong(_argIndex, serviceId);
    _argIndex = 3;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    _argIndex = 4;
    if (lastName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, lastName);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patient_id");
      final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
      final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
      final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
      final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
      final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
      final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
      final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
      final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
      final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
      final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
      final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
      final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
      final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
      final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
      final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
      final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
      final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
      final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
      final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
      final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
      final int _cursorIndexOfId_1 = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfCtcNumber_1 = _cursor.getColumnIndexOrThrow("ctcNumber");
      final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Referral _item;
        _item = new Referral();
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
        final String _tmpReferral_id;
        _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
        _item.setReferral_id(_tmpReferral_id);
        final String _tmpCommunityBasedHivService;
        _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
        _item.setCommunityBasedHivService(_tmpCommunityBasedHivService);
        final String _tmpReferralReason;
        _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
        _item.setReferralReason(_tmpReferralReason);
        final int _tmpServiceId;
        _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
        _item.setServiceId(_tmpServiceId);
        final String _tmpReferralUUID;
        _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
        _item.setReferralUUID(_tmpReferralUUID);
        final String _tmpCtcNumber;
        _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
        _item.setCtcNumber(_tmpCtcNumber);
        final String _tmpServiceProviderUIID;
        _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
        _item.setServiceProviderUIID(_tmpServiceProviderUIID);
        final String _tmpServiceProviderGroup;
        _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
        _item.setServiceProviderGroup(_tmpServiceProviderGroup);
        final String _tmpVillageLeader;
        _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
        _item.setVillageLeader(_tmpVillageLeader);
        final long _tmpReferralDate;
        _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
        _item.setReferralDate(_tmpReferralDate);
        final String _tmpFacilityId;
        _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
        _item.setFacilityId(_tmpFacilityId);
        final String _tmpFromFacilityId;
        _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
        _item.setFromFacilityId(_tmpFromFacilityId);
        final List<Long> _tmpServiceIndicatorIds;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
        _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
        _item.setServiceIndicatorIds(_tmpServiceIndicatorIds);
        final int _tmpReferralSource;
        _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
        _item.setReferralSource(_tmpReferralSource);
        final int _tmpReferralType;
        _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
        _item.setReferralType(_tmpReferralType);
        final int _tmpReferralStatus;
        _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
        _item.setReferralStatus(_tmpReferralStatus);
        final boolean _tmpTestResults;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
        _tmpTestResults = _tmp_1 != 0;
        _item.setTestResults(_tmpTestResults);
        final String _tmpServiceGivenToPatient;
        _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
        _item.setServiceGivenToPatient(_tmpServiceGivenToPatient);
        final String _tmpOtherNotesAndAdvices;
        _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
        _item.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
        final String _tmpOtherClinicalInformation;
        _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
        _item.setOtherClinicalInformation(_tmpOtherClinicalInformation);
        final Long _tmpId_1;
        if (_cursor.isNull(_cursorIndexOfId_1)) {
          _tmpId_1 = null;
        } else {
          _tmpId_1 = _cursor.getLong(_cursorIndexOfId_1);
        }
        _item.setId(_tmpId_1);
        final String _tmpCtcNumber_1;
        _tmpCtcNumber_1 = _cursor.getString(_cursorIndexOfCtcNumber_1);
        _item.setCtcNumber(_tmpCtcNumber_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Referral>> getTbReferralList(int serviceId) {
    final String _sql = "select * from Referral where serviceId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, serviceId);
    return new ComputableLiveData<List<Referral>>() {
      private Observer _observer;

      @Override
      protected List<Referral> compute() {
        if (_observer == null) {
          _observer = new Observer("Referral") {
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
          final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
          final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
          final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
          final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
          final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
          final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
          final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
          final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
          final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
          final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
          final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
          final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
          final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
          final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
          final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
          final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
          final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
          final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
          final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
          final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
          final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Referral _item;
            _item = new Referral();
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
            final String _tmpReferral_id;
            _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
            _item.setReferral_id(_tmpReferral_id);
            final String _tmpCommunityBasedHivService;
            _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
            _item.setCommunityBasedHivService(_tmpCommunityBasedHivService);
            final String _tmpReferralReason;
            _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
            _item.setReferralReason(_tmpReferralReason);
            final int _tmpServiceId;
            _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
            _item.setServiceId(_tmpServiceId);
            final String _tmpReferralUUID;
            _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
            _item.setReferralUUID(_tmpReferralUUID);
            final String _tmpCtcNumber;
            _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
            _item.setCtcNumber(_tmpCtcNumber);
            final String _tmpServiceProviderUIID;
            _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
            _item.setServiceProviderUIID(_tmpServiceProviderUIID);
            final String _tmpServiceProviderGroup;
            _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
            _item.setServiceProviderGroup(_tmpServiceProviderGroup);
            final String _tmpVillageLeader;
            _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
            _item.setVillageLeader(_tmpVillageLeader);
            final long _tmpReferralDate;
            _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
            _item.setReferralDate(_tmpReferralDate);
            final String _tmpFacilityId;
            _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
            _item.setFacilityId(_tmpFacilityId);
            final String _tmpFromFacilityId;
            _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
            _item.setFromFacilityId(_tmpFromFacilityId);
            final List<Long> _tmpServiceIndicatorIds;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
            _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
            _item.setServiceIndicatorIds(_tmpServiceIndicatorIds);
            final int _tmpReferralSource;
            _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
            _item.setReferralSource(_tmpReferralSource);
            final int _tmpReferralType;
            _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
            _item.setReferralType(_tmpReferralType);
            final int _tmpReferralStatus;
            _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
            _item.setReferralStatus(_tmpReferralStatus);
            final boolean _tmpTestResults;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
            _tmpTestResults = _tmp_1 != 0;
            _item.setTestResults(_tmpTestResults);
            final String _tmpServiceGivenToPatient;
            _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
            _item.setServiceGivenToPatient(_tmpServiceGivenToPatient);
            final String _tmpOtherNotesAndAdvices;
            _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
            _item.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
            final String _tmpOtherClinicalInformation;
            _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
            _item.setOtherClinicalInformation(_tmpOtherClinicalInformation);
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
  public List<Referral> getFilteredTbReferals(String name, String lastName, int status,
      int serviceId) {
    final String _sql = "select * from Referral inner join Patient on Referral.patient_id = Patient.patientId where Referral.referralStatus = ? and Referral.serviceId = ? and Patient.patientFirstName like ? COLLATE NOCASE and Patient.patientSurname like ? COLLATE NOCASE";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, status);
    _argIndex = 2;
    _statement.bindLong(_argIndex, serviceId);
    _argIndex = 3;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    _argIndex = 4;
    if (lastName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, lastName);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfPatientId = _cursor.getColumnIndexOrThrow("patient_id");
      final int _cursorIndexOfReferralId = _cursor.getColumnIndexOrThrow("referral_id");
      final int _cursorIndexOfCommunityBasedHivService = _cursor.getColumnIndexOrThrow("communityBasedHivService");
      final int _cursorIndexOfReferralReason = _cursor.getColumnIndexOrThrow("referralReason");
      final int _cursorIndexOfServiceId = _cursor.getColumnIndexOrThrow("serviceId");
      final int _cursorIndexOfReferralUUID = _cursor.getColumnIndexOrThrow("referralUUID");
      final int _cursorIndexOfCtcNumber = _cursor.getColumnIndexOrThrow("ctcNumber");
      final int _cursorIndexOfServiceProviderUIID = _cursor.getColumnIndexOrThrow("serviceProviderUIID");
      final int _cursorIndexOfServiceProviderGroup = _cursor.getColumnIndexOrThrow("serviceProviderGroup");
      final int _cursorIndexOfVillageLeader = _cursor.getColumnIndexOrThrow("villageLeader");
      final int _cursorIndexOfReferralDate = _cursor.getColumnIndexOrThrow("referralDate");
      final int _cursorIndexOfFacilityId = _cursor.getColumnIndexOrThrow("facilityId");
      final int _cursorIndexOfFromFacilityId = _cursor.getColumnIndexOrThrow("fromFacilityId");
      final int _cursorIndexOfServiceIndicatorIds = _cursor.getColumnIndexOrThrow("serviceIndicatorIds");
      final int _cursorIndexOfReferralSource = _cursor.getColumnIndexOrThrow("referralSource");
      final int _cursorIndexOfReferralType = _cursor.getColumnIndexOrThrow("referralType");
      final int _cursorIndexOfReferralStatus = _cursor.getColumnIndexOrThrow("referralStatus");
      final int _cursorIndexOfTestResults = _cursor.getColumnIndexOrThrow("testResults");
      final int _cursorIndexOfServiceGivenToPatient = _cursor.getColumnIndexOrThrow("serviceGivenToPatient");
      final int _cursorIndexOfOtherNotesAndAdvices = _cursor.getColumnIndexOrThrow("otherNotesAndAdvices");
      final int _cursorIndexOfOtherClinicalInformation = _cursor.getColumnIndexOrThrow("otherClinicalInformation");
      final int _cursorIndexOfId_1 = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfCtcNumber_1 = _cursor.getColumnIndexOrThrow("ctcNumber");
      final List<Referral> _result = new ArrayList<Referral>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Referral _item;
        _item = new Referral();
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
        final String _tmpReferral_id;
        _tmpReferral_id = _cursor.getString(_cursorIndexOfReferralId);
        _item.setReferral_id(_tmpReferral_id);
        final String _tmpCommunityBasedHivService;
        _tmpCommunityBasedHivService = _cursor.getString(_cursorIndexOfCommunityBasedHivService);
        _item.setCommunityBasedHivService(_tmpCommunityBasedHivService);
        final String _tmpReferralReason;
        _tmpReferralReason = _cursor.getString(_cursorIndexOfReferralReason);
        _item.setReferralReason(_tmpReferralReason);
        final int _tmpServiceId;
        _tmpServiceId = _cursor.getInt(_cursorIndexOfServiceId);
        _item.setServiceId(_tmpServiceId);
        final String _tmpReferralUUID;
        _tmpReferralUUID = _cursor.getString(_cursorIndexOfReferralUUID);
        _item.setReferralUUID(_tmpReferralUUID);
        final String _tmpCtcNumber;
        _tmpCtcNumber = _cursor.getString(_cursorIndexOfCtcNumber);
        _item.setCtcNumber(_tmpCtcNumber);
        final String _tmpServiceProviderUIID;
        _tmpServiceProviderUIID = _cursor.getString(_cursorIndexOfServiceProviderUIID);
        _item.setServiceProviderUIID(_tmpServiceProviderUIID);
        final String _tmpServiceProviderGroup;
        _tmpServiceProviderGroup = _cursor.getString(_cursorIndexOfServiceProviderGroup);
        _item.setServiceProviderGroup(_tmpServiceProviderGroup);
        final String _tmpVillageLeader;
        _tmpVillageLeader = _cursor.getString(_cursorIndexOfVillageLeader);
        _item.setVillageLeader(_tmpVillageLeader);
        final long _tmpReferralDate;
        _tmpReferralDate = _cursor.getLong(_cursorIndexOfReferralDate);
        _item.setReferralDate(_tmpReferralDate);
        final String _tmpFacilityId;
        _tmpFacilityId = _cursor.getString(_cursorIndexOfFacilityId);
        _item.setFacilityId(_tmpFacilityId);
        final String _tmpFromFacilityId;
        _tmpFromFacilityId = _cursor.getString(_cursorIndexOfFromFacilityId);
        _item.setFromFacilityId(_tmpFromFacilityId);
        final List<Long> _tmpServiceIndicatorIds;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfServiceIndicatorIds);
        _tmpServiceIndicatorIds = ListStringConverter.stringToSomeObjectList(_tmp);
        _item.setServiceIndicatorIds(_tmpServiceIndicatorIds);
        final int _tmpReferralSource;
        _tmpReferralSource = _cursor.getInt(_cursorIndexOfReferralSource);
        _item.setReferralSource(_tmpReferralSource);
        final int _tmpReferralType;
        _tmpReferralType = _cursor.getInt(_cursorIndexOfReferralType);
        _item.setReferralType(_tmpReferralType);
        final int _tmpReferralStatus;
        _tmpReferralStatus = _cursor.getInt(_cursorIndexOfReferralStatus);
        _item.setReferralStatus(_tmpReferralStatus);
        final boolean _tmpTestResults;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfTestResults);
        _tmpTestResults = _tmp_1 != 0;
        _item.setTestResults(_tmpTestResults);
        final String _tmpServiceGivenToPatient;
        _tmpServiceGivenToPatient = _cursor.getString(_cursorIndexOfServiceGivenToPatient);
        _item.setServiceGivenToPatient(_tmpServiceGivenToPatient);
        final String _tmpOtherNotesAndAdvices;
        _tmpOtherNotesAndAdvices = _cursor.getString(_cursorIndexOfOtherNotesAndAdvices);
        _item.setOtherNotesAndAdvices(_tmpOtherNotesAndAdvices);
        final String _tmpOtherClinicalInformation;
        _tmpOtherClinicalInformation = _cursor.getString(_cursorIndexOfOtherClinicalInformation);
        _item.setOtherClinicalInformation(_tmpOtherClinicalInformation);
        final Long _tmpId_1;
        if (_cursor.isNull(_cursorIndexOfId_1)) {
          _tmpId_1 = null;
        } else {
          _tmpId_1 = _cursor.getLong(_cursorIndexOfId_1);
        }
        _item.setId(_tmpId_1);
        final String _tmpCtcNumber_1;
        _tmpCtcNumber_1 = _cursor.getString(_cursorIndexOfCtcNumber_1);
        _item.setCtcNumber(_tmpCtcNumber_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
