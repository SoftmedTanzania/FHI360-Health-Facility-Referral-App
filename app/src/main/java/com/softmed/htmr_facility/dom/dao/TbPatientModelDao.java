package com.softmed.htmr_facility.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import com.softmed.htmr_facility.dom.objects.TbPatient;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 12/30/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Dao
public interface TbPatientModelDao  {

    @Query("select * from TbPatient")
    LiveData<List<TbPatient>> getAllTbPatients();

    @Query("select * from TbPatient where treatmentStatus = 1")
    LiveData<List<TbPatient>> getAllTbPatientsOnTreatment();

    @Query("select * from TbPatient where healthFacilityPatientId = :healthFacilityId ")
    LiveData<List<TbPatient>> getTbPatientIdsByHealthFacilityId(String healthFacilityId);

    @Query("select * from TbPatient where healthFacilityPatientId = :id")
    TbPatient getTbPatientById(String id);

    @Query("select * from TbPatient where healthFacilityPatientId = :id and treatmentStatus = 1")
    TbPatient getCurrentTbPatientByPatientId(String id);

    @Query("select * from TbPatient where healthFacilityPatientId = :id and treatmentStatus = 1")
    TbPatient getTbPatientCurrentOnTreatment(String id);

    @Query("select * from TbPatient where tbPatientId = :tbPatientID")
    TbPatient getTbPatientByTbPatientId(String tbPatientID);

    @Query("select count(*) from TbPatient " +
            "inner join Patient " +
            "on TbPatient.healthFacilityPatientId = Patient.patientId " +
            "where TbPatient.treatment_type = :treatmentType and Patient.gender = :gender ")
    int getCountUnderMedication(String treatmentType, String gender);

    @Insert(onConflict = REPLACE)
    void addPatient(TbPatient tbPatients);

    @Update
    void updateTbPatient(TbPatient patient);

    @Delete
    void deleteAPatient(TbPatient patients);

}
