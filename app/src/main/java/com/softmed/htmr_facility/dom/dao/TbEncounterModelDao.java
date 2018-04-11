package com.softmed.htmr_facility.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import com.softmed.htmr_facility.dom.objects.TbEncounters;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 12/30/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Dao
public interface TbEncounterModelDao {

    @Query("select * from TbEncounters where tbPatientID = :tbPatientId")
    List<TbEncounters> getEncounterByPatientID(Long tbPatientId);

    @Query("select * from TbEncounters where id = :ID")
    List<TbEncounters> getEncounterById(String ID);

    @Query("select * from TbEncounters where localID = :localID")
    List<TbEncounters> getEncounterByLocalId(String localID);

    @Query("select * from TbEncounters")
    LiveData<List<TbEncounters>> getAllEncounters();

    @Query("select * from TbEncounters where tbPatientID = :tbPatientTempId")
    List<TbEncounters> getAllEncountersList(String tbPatientTempId);

    @Insert(onConflict = REPLACE)
    void addEncounter(TbEncounters tbEncounters);

    @Query("select * from TbEncounters where encounterMonth = :encounterMonth and tbPatientID =:healthFacilityPatientId")
    List<TbEncounters> getMonthEncounter(String encounterMonth, long healthFacilityPatientId);

    @Update
    void updatePreviousMonthMedicationStatus(TbEncounters tbEncounters);

    @Delete
    void deleteAnEncounter(TbEncounters encounters);

}
