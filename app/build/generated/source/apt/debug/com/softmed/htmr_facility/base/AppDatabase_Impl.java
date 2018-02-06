package com.softmed.htmr_facility.base;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import com.softmed.htmr_facility.dom.dao.AppDataModelDao;
import com.softmed.htmr_facility.dom.dao.AppDataModelDao_Impl;
import com.softmed.htmr_facility.dom.dao.HealthFacilitiesModelDao;
import com.softmed.htmr_facility.dom.dao.HealthFacilitiesModelDao_Impl;
import com.softmed.htmr_facility.dom.dao.PatientAppointmentModelDao;
import com.softmed.htmr_facility.dom.dao.PatientAppointmentModelDao_Impl;
import com.softmed.htmr_facility.dom.dao.PatientModelDao;
import com.softmed.htmr_facility.dom.dao.PatientModelDao_Impl;
import com.softmed.htmr_facility.dom.dao.PatientNotificationModelDao;
import com.softmed.htmr_facility.dom.dao.PatientNotificationModelDao_Impl;
import com.softmed.htmr_facility.dom.dao.PatientServicesModelDao;
import com.softmed.htmr_facility.dom.dao.PatientServicesModelDao_Impl;
import com.softmed.htmr_facility.dom.dao.PostOfficeModelDao;
import com.softmed.htmr_facility.dom.dao.PostOfficeModelDao_Impl;
import com.softmed.htmr_facility.dom.dao.ReferalModelDao;
import com.softmed.htmr_facility.dom.dao.ReferalModelDao_Impl;
import com.softmed.htmr_facility.dom.dao.ReferralIndicatorDao;
import com.softmed.htmr_facility.dom.dao.ReferralIndicatorDao_Impl;
import com.softmed.htmr_facility.dom.dao.ReferralServiceIndicatorsDao;
import com.softmed.htmr_facility.dom.dao.ReferralServiceIndicatorsDao_Impl;
import com.softmed.htmr_facility.dom.dao.TbEncounterModelDao;
import com.softmed.htmr_facility.dom.dao.TbEncounterModelDao_Impl;
import com.softmed.htmr_facility.dom.dao.TbPatientModelDao;
import com.softmed.htmr_facility.dom.dao.TbPatientModelDao_Impl;
import com.softmed.htmr_facility.dom.dao.UserDataModelDao;
import com.softmed.htmr_facility.dom.dao.UserDataModelDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.HashSet;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class AppDatabase_Impl extends AppDatabase {
  private volatile PatientModelDao _patientModelDao;

  private volatile ReferalModelDao _referalModelDao;

  private volatile PatientNotificationModelDao _patientNotificationModelDao;

  private volatile PostOfficeModelDao _postOfficeModelDao;

  private volatile AppDataModelDao _appDataModelDao;

  private volatile TbPatientModelDao _tbPatientModelDao;

  private volatile TbEncounterModelDao _tbEncounterModelDao;

  private volatile PatientAppointmentModelDao _patientAppointmentModelDao;

  private volatile PatientServicesModelDao _patientServicesModelDao;

  private volatile HealthFacilitiesModelDao _healthFacilitiesModelDao;

  private volatile UserDataModelDao _userDataModelDao;

  private volatile ReferralIndicatorDao _referralIndicatorDao;

  private volatile ReferralServiceIndicatorsDao _referralServiceIndicatorsDao;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Patient` (`id` INTEGER, `patientId` TEXT NOT NULL, `patientFirstName` TEXT, `patientMiddleName` TEXT, `patientSurname` TEXT, `cbhs` TEXT, `ctcNumber` TEXT, `phone_number` TEXT, `ward` TEXT, `village` TEXT, `hamlet` TEXT, `dateOfBirth` INTEGER NOT NULL, `gender` TEXT, `dateOfDeath` INTEGER NOT NULL, `hivStatus` INTEGER NOT NULL, `currentOnTbTreatment` INTEGER NOT NULL, `careTakerName` TEXT, `careTakerPhoneNumber` TEXT, `careTakerRelationship` TEXT, PRIMARY KEY(`patientId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Referral` (`id` INTEGER, `patient_id` TEXT, `referral_id` TEXT NOT NULL, `communityBasedHivService` TEXT, `referralReason` TEXT, `serviceId` INTEGER NOT NULL, `referralUUID` TEXT, `ctcNumber` TEXT, `serviceProviderUIID` TEXT, `serviceProviderGroup` TEXT, `villageLeader` TEXT, `referralDate` INTEGER NOT NULL, `facilityId` TEXT, `fromFacilityId` TEXT, `serviceIndicatorIds` TEXT, `referralSource` INTEGER NOT NULL, `referralType` INTEGER NOT NULL, `referralStatus` INTEGER NOT NULL, `testResults` INTEGER NOT NULL, `serviceGivenToPatient` TEXT, `otherNotesAndAdvices` TEXT, `otherClinicalInformation` TEXT, PRIMARY KEY(`referral_id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `PatientsNotification` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `patient_id` TEXT, `appointmentDate` INTEGER, `appointmentType` TEXT, `notificationTime` TEXT, `message` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `PostOffice` (`post_id` TEXT NOT NULL, `post_data_type` TEXT, `syncStatus` INTEGER NOT NULL, PRIMARY KEY(`post_id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `AppData` (`name` TEXT NOT NULL, `value` TEXT, PRIMARY KEY(`name`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `TbPatient` (`tempID` TEXT NOT NULL, `patientId` INTEGER NOT NULL, `tbPatientId` INTEGER NOT NULL, `patientType` INTEGER NOT NULL, `transferType` INTEGER NOT NULL, `referralType` INTEGER NOT NULL, `veo` TEXT, `weight` REAL NOT NULL, `xray` TEXT, `makohozi` TEXT, `otherTests` TEXT, `treatment_type` TEXT, `outcome` TEXT, `outcomeDate` INTEGER NOT NULL, `outcomeDetails` TEXT, `isPregnant` INTEGER NOT NULL, PRIMARY KEY(`patientId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `TbEncounters` (`id` TEXT, `tbPatientID` TEXT NOT NULL, `encounterMonth` INTEGER NOT NULL, `makohozi` TEXT, `appointmentId` INTEGER NOT NULL, `hasFinishedPreviousMonthMedication` INTEGER NOT NULL, `medicationStatus` INTEGER NOT NULL, `medicationDate` INTEGER NOT NULL, `scheduledDate` INTEGER NOT NULL, PRIMARY KEY(`tbPatientID`, `encounterMonth`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `PatientAppointment` (`appointmentID` INTEGER NOT NULL, `patientID` TEXT, `appointmentDate` INTEGER NOT NULL, `appointmentType` INTEGER NOT NULL, `cancelled` INTEGER NOT NULL, `status` TEXT, `appointmentEncounterMonth` TEXT, PRIMARY KEY(`appointmentID`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `HealthFacilityServices` (`id` INTEGER NOT NULL, `serviceName` TEXT, `isActive` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `HealthFacilities` (`ID` INTEGER NOT NULL, `openMRSUIID` TEXT, `facilityName` TEXT, `facilityCtcCode` TEXT, `parentOpenmrsUIID` TEXT, `parentHFRCode` TEXT, `hfrCode` TEXT, PRIMARY KEY(`ID`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UserData` (`UserUIID` TEXT NOT NULL, `userName` TEXT, `userFacilityId` TEXT, PRIMARY KEY(`UserUIID`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ReferralIndicator` (`referralServiceIndicatorId` INTEGER NOT NULL, `referralIndicatorId` INTEGER NOT NULL, `indicatorName` TEXT, `isActive` INTEGER NOT NULL, `serviceID` INTEGER NOT NULL, PRIMARY KEY(`referralServiceIndicatorId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ReferralServiceIndicators` (`serviceId` INTEGER NOT NULL, `serviceName` TEXT, `category` TEXT, `isActive` INTEGER NOT NULL, PRIMARY KEY(`serviceId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"f201adb297a85a79a52a113706112e99\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Patient`");
        _db.execSQL("DROP TABLE IF EXISTS `Referral`");
        _db.execSQL("DROP TABLE IF EXISTS `PatientsNotification`");
        _db.execSQL("DROP TABLE IF EXISTS `PostOffice`");
        _db.execSQL("DROP TABLE IF EXISTS `AppData`");
        _db.execSQL("DROP TABLE IF EXISTS `TbPatient`");
        _db.execSQL("DROP TABLE IF EXISTS `TbEncounters`");
        _db.execSQL("DROP TABLE IF EXISTS `PatientAppointment`");
        _db.execSQL("DROP TABLE IF EXISTS `HealthFacilityServices`");
        _db.execSQL("DROP TABLE IF EXISTS `HealthFacilities`");
        _db.execSQL("DROP TABLE IF EXISTS `UserData`");
        _db.execSQL("DROP TABLE IF EXISTS `ReferralIndicator`");
        _db.execSQL("DROP TABLE IF EXISTS `ReferralServiceIndicators`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPatient = new HashMap<String, TableInfo.Column>(19);
        _columnsPatient.put("id", new TableInfo.Column("id", "INTEGER", false, 0));
        _columnsPatient.put("patientId", new TableInfo.Column("patientId", "TEXT", true, 1));
        _columnsPatient.put("patientFirstName", new TableInfo.Column("patientFirstName", "TEXT", false, 0));
        _columnsPatient.put("patientMiddleName", new TableInfo.Column("patientMiddleName", "TEXT", false, 0));
        _columnsPatient.put("patientSurname", new TableInfo.Column("patientSurname", "TEXT", false, 0));
        _columnsPatient.put("cbhs", new TableInfo.Column("cbhs", "TEXT", false, 0));
        _columnsPatient.put("ctcNumber", new TableInfo.Column("ctcNumber", "TEXT", false, 0));
        _columnsPatient.put("phone_number", new TableInfo.Column("phone_number", "TEXT", false, 0));
        _columnsPatient.put("ward", new TableInfo.Column("ward", "TEXT", false, 0));
        _columnsPatient.put("village", new TableInfo.Column("village", "TEXT", false, 0));
        _columnsPatient.put("hamlet", new TableInfo.Column("hamlet", "TEXT", false, 0));
        _columnsPatient.put("dateOfBirth", new TableInfo.Column("dateOfBirth", "INTEGER", true, 0));
        _columnsPatient.put("gender", new TableInfo.Column("gender", "TEXT", false, 0));
        _columnsPatient.put("dateOfDeath", new TableInfo.Column("dateOfDeath", "INTEGER", true, 0));
        _columnsPatient.put("hivStatus", new TableInfo.Column("hivStatus", "INTEGER", true, 0));
        _columnsPatient.put("currentOnTbTreatment", new TableInfo.Column("currentOnTbTreatment", "INTEGER", true, 0));
        _columnsPatient.put("careTakerName", new TableInfo.Column("careTakerName", "TEXT", false, 0));
        _columnsPatient.put("careTakerPhoneNumber", new TableInfo.Column("careTakerPhoneNumber", "TEXT", false, 0));
        _columnsPatient.put("careTakerRelationship", new TableInfo.Column("careTakerRelationship", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPatient = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPatient = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPatient = new TableInfo("Patient", _columnsPatient, _foreignKeysPatient, _indicesPatient);
        final TableInfo _existingPatient = TableInfo.read(_db, "Patient");
        if (! _infoPatient.equals(_existingPatient)) {
          throw new IllegalStateException("Migration didn't properly handle Patient(com.softmed.htmr_facility.dom.objects.Patient).\n"
                  + " Expected:\n" + _infoPatient + "\n"
                  + " Found:\n" + _existingPatient);
        }
        final HashMap<String, TableInfo.Column> _columnsReferral = new HashMap<String, TableInfo.Column>(22);
        _columnsReferral.put("id", new TableInfo.Column("id", "INTEGER", false, 0));
        _columnsReferral.put("patient_id", new TableInfo.Column("patient_id", "TEXT", false, 0));
        _columnsReferral.put("referral_id", new TableInfo.Column("referral_id", "TEXT", true, 1));
        _columnsReferral.put("communityBasedHivService", new TableInfo.Column("communityBasedHivService", "TEXT", false, 0));
        _columnsReferral.put("referralReason", new TableInfo.Column("referralReason", "TEXT", false, 0));
        _columnsReferral.put("serviceId", new TableInfo.Column("serviceId", "INTEGER", true, 0));
        _columnsReferral.put("referralUUID", new TableInfo.Column("referralUUID", "TEXT", false, 0));
        _columnsReferral.put("ctcNumber", new TableInfo.Column("ctcNumber", "TEXT", false, 0));
        _columnsReferral.put("serviceProviderUIID", new TableInfo.Column("serviceProviderUIID", "TEXT", false, 0));
        _columnsReferral.put("serviceProviderGroup", new TableInfo.Column("serviceProviderGroup", "TEXT", false, 0));
        _columnsReferral.put("villageLeader", new TableInfo.Column("villageLeader", "TEXT", false, 0));
        _columnsReferral.put("referralDate", new TableInfo.Column("referralDate", "INTEGER", true, 0));
        _columnsReferral.put("facilityId", new TableInfo.Column("facilityId", "TEXT", false, 0));
        _columnsReferral.put("fromFacilityId", new TableInfo.Column("fromFacilityId", "TEXT", false, 0));
        _columnsReferral.put("serviceIndicatorIds", new TableInfo.Column("serviceIndicatorIds", "TEXT", false, 0));
        _columnsReferral.put("referralSource", new TableInfo.Column("referralSource", "INTEGER", true, 0));
        _columnsReferral.put("referralType", new TableInfo.Column("referralType", "INTEGER", true, 0));
        _columnsReferral.put("referralStatus", new TableInfo.Column("referralStatus", "INTEGER", true, 0));
        _columnsReferral.put("testResults", new TableInfo.Column("testResults", "INTEGER", true, 0));
        _columnsReferral.put("serviceGivenToPatient", new TableInfo.Column("serviceGivenToPatient", "TEXT", false, 0));
        _columnsReferral.put("otherNotesAndAdvices", new TableInfo.Column("otherNotesAndAdvices", "TEXT", false, 0));
        _columnsReferral.put("otherClinicalInformation", new TableInfo.Column("otherClinicalInformation", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReferral = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReferral = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReferral = new TableInfo("Referral", _columnsReferral, _foreignKeysReferral, _indicesReferral);
        final TableInfo _existingReferral = TableInfo.read(_db, "Referral");
        if (! _infoReferral.equals(_existingReferral)) {
          throw new IllegalStateException("Migration didn't properly handle Referral(com.softmed.htmr_facility.dom.objects.Referral).\n"
                  + " Expected:\n" + _infoReferral + "\n"
                  + " Found:\n" + _existingReferral);
        }
        final HashMap<String, TableInfo.Column> _columnsPatientsNotification = new HashMap<String, TableInfo.Column>(6);
        _columnsPatientsNotification.put("id", new TableInfo.Column("id", "INTEGER", false, 1));
        _columnsPatientsNotification.put("patient_id", new TableInfo.Column("patient_id", "TEXT", false, 0));
        _columnsPatientsNotification.put("appointmentDate", new TableInfo.Column("appointmentDate", "INTEGER", false, 0));
        _columnsPatientsNotification.put("appointmentType", new TableInfo.Column("appointmentType", "TEXT", false, 0));
        _columnsPatientsNotification.put("notificationTime", new TableInfo.Column("notificationTime", "TEXT", false, 0));
        _columnsPatientsNotification.put("message", new TableInfo.Column("message", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPatientsNotification = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPatientsNotification = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPatientsNotification = new TableInfo("PatientsNotification", _columnsPatientsNotification, _foreignKeysPatientsNotification, _indicesPatientsNotification);
        final TableInfo _existingPatientsNotification = TableInfo.read(_db, "PatientsNotification");
        if (! _infoPatientsNotification.equals(_existingPatientsNotification)) {
          throw new IllegalStateException("Migration didn't properly handle PatientsNotification(com.softmed.htmr_facility.dom.objects.PatientsNotification).\n"
                  + " Expected:\n" + _infoPatientsNotification + "\n"
                  + " Found:\n" + _existingPatientsNotification);
        }
        final HashMap<String, TableInfo.Column> _columnsPostOffice = new HashMap<String, TableInfo.Column>(3);
        _columnsPostOffice.put("post_id", new TableInfo.Column("post_id", "TEXT", true, 1));
        _columnsPostOffice.put("post_data_type", new TableInfo.Column("post_data_type", "TEXT", false, 0));
        _columnsPostOffice.put("syncStatus", new TableInfo.Column("syncStatus", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPostOffice = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPostOffice = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPostOffice = new TableInfo("PostOffice", _columnsPostOffice, _foreignKeysPostOffice, _indicesPostOffice);
        final TableInfo _existingPostOffice = TableInfo.read(_db, "PostOffice");
        if (! _infoPostOffice.equals(_existingPostOffice)) {
          throw new IllegalStateException("Migration didn't properly handle PostOffice(com.softmed.htmr_facility.dom.objects.PostOffice).\n"
                  + " Expected:\n" + _infoPostOffice + "\n"
                  + " Found:\n" + _existingPostOffice);
        }
        final HashMap<String, TableInfo.Column> _columnsAppData = new HashMap<String, TableInfo.Column>(2);
        _columnsAppData.put("name", new TableInfo.Column("name", "TEXT", true, 1));
        _columnsAppData.put("value", new TableInfo.Column("value", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAppData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAppData = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAppData = new TableInfo("AppData", _columnsAppData, _foreignKeysAppData, _indicesAppData);
        final TableInfo _existingAppData = TableInfo.read(_db, "AppData");
        if (! _infoAppData.equals(_existingAppData)) {
          throw new IllegalStateException("Migration didn't properly handle AppData(com.softmed.htmr_facility.dom.objects.AppData).\n"
                  + " Expected:\n" + _infoAppData + "\n"
                  + " Found:\n" + _existingAppData);
        }
        final HashMap<String, TableInfo.Column> _columnsTbPatient = new HashMap<String, TableInfo.Column>(16);
        _columnsTbPatient.put("tempID", new TableInfo.Column("tempID", "TEXT", true, 0));
        _columnsTbPatient.put("patientId", new TableInfo.Column("patientId", "INTEGER", true, 1));
        _columnsTbPatient.put("tbPatientId", new TableInfo.Column("tbPatientId", "INTEGER", true, 0));
        _columnsTbPatient.put("patientType", new TableInfo.Column("patientType", "INTEGER", true, 0));
        _columnsTbPatient.put("transferType", new TableInfo.Column("transferType", "INTEGER", true, 0));
        _columnsTbPatient.put("referralType", new TableInfo.Column("referralType", "INTEGER", true, 0));
        _columnsTbPatient.put("veo", new TableInfo.Column("veo", "TEXT", false, 0));
        _columnsTbPatient.put("weight", new TableInfo.Column("weight", "REAL", true, 0));
        _columnsTbPatient.put("xray", new TableInfo.Column("xray", "TEXT", false, 0));
        _columnsTbPatient.put("makohozi", new TableInfo.Column("makohozi", "TEXT", false, 0));
        _columnsTbPatient.put("otherTests", new TableInfo.Column("otherTests", "TEXT", false, 0));
        _columnsTbPatient.put("treatment_type", new TableInfo.Column("treatment_type", "TEXT", false, 0));
        _columnsTbPatient.put("outcome", new TableInfo.Column("outcome", "TEXT", false, 0));
        _columnsTbPatient.put("outcomeDate", new TableInfo.Column("outcomeDate", "INTEGER", true, 0));
        _columnsTbPatient.put("outcomeDetails", new TableInfo.Column("outcomeDetails", "TEXT", false, 0));
        _columnsTbPatient.put("isPregnant", new TableInfo.Column("isPregnant", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTbPatient = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTbPatient = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTbPatient = new TableInfo("TbPatient", _columnsTbPatient, _foreignKeysTbPatient, _indicesTbPatient);
        final TableInfo _existingTbPatient = TableInfo.read(_db, "TbPatient");
        if (! _infoTbPatient.equals(_existingTbPatient)) {
          throw new IllegalStateException("Migration didn't properly handle TbPatient(com.softmed.htmr_facility.dom.objects.TbPatient).\n"
                  + " Expected:\n" + _infoTbPatient + "\n"
                  + " Found:\n" + _existingTbPatient);
        }
        final HashMap<String, TableInfo.Column> _columnsTbEncounters = new HashMap<String, TableInfo.Column>(9);
        _columnsTbEncounters.put("id", new TableInfo.Column("id", "TEXT", false, 0));
        _columnsTbEncounters.put("tbPatientID", new TableInfo.Column("tbPatientID", "TEXT", true, 1));
        _columnsTbEncounters.put("encounterMonth", new TableInfo.Column("encounterMonth", "INTEGER", true, 2));
        _columnsTbEncounters.put("makohozi", new TableInfo.Column("makohozi", "TEXT", false, 0));
        _columnsTbEncounters.put("appointmentId", new TableInfo.Column("appointmentId", "INTEGER", true, 0));
        _columnsTbEncounters.put("hasFinishedPreviousMonthMedication", new TableInfo.Column("hasFinishedPreviousMonthMedication", "INTEGER", true, 0));
        _columnsTbEncounters.put("medicationStatus", new TableInfo.Column("medicationStatus", "INTEGER", true, 0));
        _columnsTbEncounters.put("medicationDate", new TableInfo.Column("medicationDate", "INTEGER", true, 0));
        _columnsTbEncounters.put("scheduledDate", new TableInfo.Column("scheduledDate", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTbEncounters = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTbEncounters = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTbEncounters = new TableInfo("TbEncounters", _columnsTbEncounters, _foreignKeysTbEncounters, _indicesTbEncounters);
        final TableInfo _existingTbEncounters = TableInfo.read(_db, "TbEncounters");
        if (! _infoTbEncounters.equals(_existingTbEncounters)) {
          throw new IllegalStateException("Migration didn't properly handle TbEncounters(com.softmed.htmr_facility.dom.objects.TbEncounters).\n"
                  + " Expected:\n" + _infoTbEncounters + "\n"
                  + " Found:\n" + _existingTbEncounters);
        }
        final HashMap<String, TableInfo.Column> _columnsPatientAppointment = new HashMap<String, TableInfo.Column>(7);
        _columnsPatientAppointment.put("appointmentID", new TableInfo.Column("appointmentID", "INTEGER", true, 1));
        _columnsPatientAppointment.put("patientID", new TableInfo.Column("patientID", "TEXT", false, 0));
        _columnsPatientAppointment.put("appointmentDate", new TableInfo.Column("appointmentDate", "INTEGER", true, 0));
        _columnsPatientAppointment.put("appointmentType", new TableInfo.Column("appointmentType", "INTEGER", true, 0));
        _columnsPatientAppointment.put("cancelled", new TableInfo.Column("cancelled", "INTEGER", true, 0));
        _columnsPatientAppointment.put("status", new TableInfo.Column("status", "TEXT", false, 0));
        _columnsPatientAppointment.put("appointmentEncounterMonth", new TableInfo.Column("appointmentEncounterMonth", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPatientAppointment = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPatientAppointment = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPatientAppointment = new TableInfo("PatientAppointment", _columnsPatientAppointment, _foreignKeysPatientAppointment, _indicesPatientAppointment);
        final TableInfo _existingPatientAppointment = TableInfo.read(_db, "PatientAppointment");
        if (! _infoPatientAppointment.equals(_existingPatientAppointment)) {
          throw new IllegalStateException("Migration didn't properly handle PatientAppointment(com.softmed.htmr_facility.dom.objects.PatientAppointment).\n"
                  + " Expected:\n" + _infoPatientAppointment + "\n"
                  + " Found:\n" + _existingPatientAppointment);
        }
        final HashMap<String, TableInfo.Column> _columnsHealthFacilityServices = new HashMap<String, TableInfo.Column>(3);
        _columnsHealthFacilityServices.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsHealthFacilityServices.put("serviceName", new TableInfo.Column("serviceName", "TEXT", false, 0));
        _columnsHealthFacilityServices.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHealthFacilityServices = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHealthFacilityServices = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHealthFacilityServices = new TableInfo("HealthFacilityServices", _columnsHealthFacilityServices, _foreignKeysHealthFacilityServices, _indicesHealthFacilityServices);
        final TableInfo _existingHealthFacilityServices = TableInfo.read(_db, "HealthFacilityServices");
        if (! _infoHealthFacilityServices.equals(_existingHealthFacilityServices)) {
          throw new IllegalStateException("Migration didn't properly handle HealthFacilityServices(com.softmed.htmr_facility.dom.objects.HealthFacilityServices).\n"
                  + " Expected:\n" + _infoHealthFacilityServices + "\n"
                  + " Found:\n" + _existingHealthFacilityServices);
        }
        final HashMap<String, TableInfo.Column> _columnsHealthFacilities = new HashMap<String, TableInfo.Column>(7);
        _columnsHealthFacilities.put("ID", new TableInfo.Column("ID", "INTEGER", true, 1));
        _columnsHealthFacilities.put("openMRSUIID", new TableInfo.Column("openMRSUIID", "TEXT", false, 0));
        _columnsHealthFacilities.put("facilityName", new TableInfo.Column("facilityName", "TEXT", false, 0));
        _columnsHealthFacilities.put("facilityCtcCode", new TableInfo.Column("facilityCtcCode", "TEXT", false, 0));
        _columnsHealthFacilities.put("parentOpenmrsUIID", new TableInfo.Column("parentOpenmrsUIID", "TEXT", false, 0));
        _columnsHealthFacilities.put("parentHFRCode", new TableInfo.Column("parentHFRCode", "TEXT", false, 0));
        _columnsHealthFacilities.put("hfrCode", new TableInfo.Column("hfrCode", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHealthFacilities = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHealthFacilities = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHealthFacilities = new TableInfo("HealthFacilities", _columnsHealthFacilities, _foreignKeysHealthFacilities, _indicesHealthFacilities);
        final TableInfo _existingHealthFacilities = TableInfo.read(_db, "HealthFacilities");
        if (! _infoHealthFacilities.equals(_existingHealthFacilities)) {
          throw new IllegalStateException("Migration didn't properly handle HealthFacilities(com.softmed.htmr_facility.dom.objects.HealthFacilities).\n"
                  + " Expected:\n" + _infoHealthFacilities + "\n"
                  + " Found:\n" + _existingHealthFacilities);
        }
        final HashMap<String, TableInfo.Column> _columnsUserData = new HashMap<String, TableInfo.Column>(3);
        _columnsUserData.put("UserUIID", new TableInfo.Column("UserUIID", "TEXT", true, 1));
        _columnsUserData.put("userName", new TableInfo.Column("userName", "TEXT", false, 0));
        _columnsUserData.put("userFacilityId", new TableInfo.Column("userFacilityId", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserData = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserData = new TableInfo("UserData", _columnsUserData, _foreignKeysUserData, _indicesUserData);
        final TableInfo _existingUserData = TableInfo.read(_db, "UserData");
        if (! _infoUserData.equals(_existingUserData)) {
          throw new IllegalStateException("Migration didn't properly handle UserData(com.softmed.htmr_facility.dom.objects.UserData).\n"
                  + " Expected:\n" + _infoUserData + "\n"
                  + " Found:\n" + _existingUserData);
        }
        final HashMap<String, TableInfo.Column> _columnsReferralIndicator = new HashMap<String, TableInfo.Column>(5);
        _columnsReferralIndicator.put("referralServiceIndicatorId", new TableInfo.Column("referralServiceIndicatorId", "INTEGER", true, 1));
        _columnsReferralIndicator.put("referralIndicatorId", new TableInfo.Column("referralIndicatorId", "INTEGER", true, 0));
        _columnsReferralIndicator.put("indicatorName", new TableInfo.Column("indicatorName", "TEXT", false, 0));
        _columnsReferralIndicator.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0));
        _columnsReferralIndicator.put("serviceID", new TableInfo.Column("serviceID", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReferralIndicator = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReferralIndicator = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReferralIndicator = new TableInfo("ReferralIndicator", _columnsReferralIndicator, _foreignKeysReferralIndicator, _indicesReferralIndicator);
        final TableInfo _existingReferralIndicator = TableInfo.read(_db, "ReferralIndicator");
        if (! _infoReferralIndicator.equals(_existingReferralIndicator)) {
          throw new IllegalStateException("Migration didn't properly handle ReferralIndicator(com.softmed.htmr_facility.dom.objects.ReferralIndicator).\n"
                  + " Expected:\n" + _infoReferralIndicator + "\n"
                  + " Found:\n" + _existingReferralIndicator);
        }
        final HashMap<String, TableInfo.Column> _columnsReferralServiceIndicators = new HashMap<String, TableInfo.Column>(4);
        _columnsReferralServiceIndicators.put("serviceId", new TableInfo.Column("serviceId", "INTEGER", true, 1));
        _columnsReferralServiceIndicators.put("serviceName", new TableInfo.Column("serviceName", "TEXT", false, 0));
        _columnsReferralServiceIndicators.put("category", new TableInfo.Column("category", "TEXT", false, 0));
        _columnsReferralServiceIndicators.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReferralServiceIndicators = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReferralServiceIndicators = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReferralServiceIndicators = new TableInfo("ReferralServiceIndicators", _columnsReferralServiceIndicators, _foreignKeysReferralServiceIndicators, _indicesReferralServiceIndicators);
        final TableInfo _existingReferralServiceIndicators = TableInfo.read(_db, "ReferralServiceIndicators");
        if (! _infoReferralServiceIndicators.equals(_existingReferralServiceIndicators)) {
          throw new IllegalStateException("Migration didn't properly handle ReferralServiceIndicators(com.softmed.htmr_facility.dom.objects.ReferralServiceIndicators).\n"
                  + " Expected:\n" + _infoReferralServiceIndicators + "\n"
                  + " Found:\n" + _existingReferralServiceIndicators);
        }
      }
    }, "f201adb297a85a79a52a113706112e99");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Patient","Referral","PatientsNotification","PostOffice","AppData","TbPatient","TbEncounters","PatientAppointment","HealthFacilityServices","HealthFacilities","UserData","ReferralIndicator","ReferralServiceIndicators");
  }

  @Override
  public PatientModelDao patientModel() {
    if (_patientModelDao != null) {
      return _patientModelDao;
    } else {
      synchronized(this) {
        if(_patientModelDao == null) {
          _patientModelDao = new PatientModelDao_Impl(this);
        }
        return _patientModelDao;
      }
    }
  }

  @Override
  public ReferalModelDao referalModel() {
    if (_referalModelDao != null) {
      return _referalModelDao;
    } else {
      synchronized(this) {
        if(_referalModelDao == null) {
          _referalModelDao = new ReferalModelDao_Impl(this);
        }
        return _referalModelDao;
      }
    }
  }

  @Override
  public PatientNotificationModelDao patientNotificationModelDao() {
    if (_patientNotificationModelDao != null) {
      return _patientNotificationModelDao;
    } else {
      synchronized(this) {
        if(_patientNotificationModelDao == null) {
          _patientNotificationModelDao = new PatientNotificationModelDao_Impl(this);
        }
        return _patientNotificationModelDao;
      }
    }
  }

  @Override
  public PostOfficeModelDao postOfficeModelDao() {
    if (_postOfficeModelDao != null) {
      return _postOfficeModelDao;
    } else {
      synchronized(this) {
        if(_postOfficeModelDao == null) {
          _postOfficeModelDao = new PostOfficeModelDao_Impl(this);
        }
        return _postOfficeModelDao;
      }
    }
  }

  @Override
  public AppDataModelDao appDataModelDao() {
    if (_appDataModelDao != null) {
      return _appDataModelDao;
    } else {
      synchronized(this) {
        if(_appDataModelDao == null) {
          _appDataModelDao = new AppDataModelDao_Impl(this);
        }
        return _appDataModelDao;
      }
    }
  }

  @Override
  public TbPatientModelDao tbPatientModelDao() {
    if (_tbPatientModelDao != null) {
      return _tbPatientModelDao;
    } else {
      synchronized(this) {
        if(_tbPatientModelDao == null) {
          _tbPatientModelDao = new TbPatientModelDao_Impl(this);
        }
        return _tbPatientModelDao;
      }
    }
  }

  @Override
  public TbEncounterModelDao tbEncounterModelDao() {
    if (_tbEncounterModelDao != null) {
      return _tbEncounterModelDao;
    } else {
      synchronized(this) {
        if(_tbEncounterModelDao == null) {
          _tbEncounterModelDao = new TbEncounterModelDao_Impl(this);
        }
        return _tbEncounterModelDao;
      }
    }
  }

  @Override
  public PatientAppointmentModelDao appointmentModelDao() {
    if (_patientAppointmentModelDao != null) {
      return _patientAppointmentModelDao;
    } else {
      synchronized(this) {
        if(_patientAppointmentModelDao == null) {
          _patientAppointmentModelDao = new PatientAppointmentModelDao_Impl(this);
        }
        return _patientAppointmentModelDao;
      }
    }
  }

  @Override
  public PatientServicesModelDao servicesModelDao() {
    if (_patientServicesModelDao != null) {
      return _patientServicesModelDao;
    } else {
      synchronized(this) {
        if(_patientServicesModelDao == null) {
          _patientServicesModelDao = new PatientServicesModelDao_Impl(this);
        }
        return _patientServicesModelDao;
      }
    }
  }

  @Override
  public HealthFacilitiesModelDao healthFacilitiesModelDao() {
    if (_healthFacilitiesModelDao != null) {
      return _healthFacilitiesModelDao;
    } else {
      synchronized(this) {
        if(_healthFacilitiesModelDao == null) {
          _healthFacilitiesModelDao = new HealthFacilitiesModelDao_Impl(this);
        }
        return _healthFacilitiesModelDao;
      }
    }
  }

  @Override
  public UserDataModelDao userDataModelDao() {
    if (_userDataModelDao != null) {
      return _userDataModelDao;
    } else {
      synchronized(this) {
        if(_userDataModelDao == null) {
          _userDataModelDao = new UserDataModelDao_Impl(this);
        }
        return _userDataModelDao;
      }
    }
  }

  @Override
  public ReferralIndicatorDao referralIndicatorDao() {
    if (_referralIndicatorDao != null) {
      return _referralIndicatorDao;
    } else {
      synchronized(this) {
        if(_referralIndicatorDao == null) {
          _referralIndicatorDao = new ReferralIndicatorDao_Impl(this);
        }
        return _referralIndicatorDao;
      }
    }
  }

  @Override
  public ReferralServiceIndicatorsDao referralServiceIndicatorsDao() {
    if (_referralServiceIndicatorsDao != null) {
      return _referralServiceIndicatorsDao;
    } else {
      synchronized(this) {
        if(_referralServiceIndicatorsDao == null) {
          _referralServiceIndicatorsDao = new ReferralServiceIndicatorsDao_Impl(this);
        }
        return _referralServiceIndicatorsDao;
      }
    }
  }
}
