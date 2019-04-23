package com.softmed.htmr_facility_staging.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import com.softmed.htmr_facility_staging.dom.objects.Patient;
import com.softmed.htmr_facility_staging.utils.DateConverter;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 11/28/17.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface PatientModelDao {

    @Query("select * from Patient order by updatedAt asc")
    LiveData<List<Patient>> getAllPatients();

    @Query("select * from Patient where currentOnTbTreatment = :flag order by updatedAt asc")
    LiveData<List<Patient>> getTbPatients(boolean flag);

    @Query("select * from Patient where hivStatus = :flag order by updatedAt asc")
    LiveData<List<Patient>> getHivClients(boolean flag);

    @Query("select * from Patient where patientId = :id")
    Patient getPatientById(String id);

    @Insert(onConflict = REPLACE)
    void addPatient(Patient patients);

    @Update
    void updatePatient(Patient patient);

    @Query("select patientFirstName || ' ' || patientSurname from Patient where patientId = :patientId")
    String getPatientName(String patientId);

    @Delete
    void deleteAPatient(Patient patients);

}
