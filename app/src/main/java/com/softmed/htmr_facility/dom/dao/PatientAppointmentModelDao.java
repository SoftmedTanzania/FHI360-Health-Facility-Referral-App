package com.softmed.htmr_facility.dom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import com.softmed.htmr_facility.dom.objects.PatientAppointment;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by issy on 1/2/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Dao
public interface PatientAppointmentModelDao {

    @Query("select * from PatientAppointment where appointmentDate<:tommorrowsDate AND appointmentDate>:todaysDate order by appointmentDate asc")
    List<PatientAppointment> getAllAppointments(long todaysDate, long tommorrowsDate);

    @Query("select count(*) from PatientAppointment order by appointmentDate asc")
    int getAllAppointmentsCount();

    @Query("select * from PatientAppointment where appointmentType = 2 and appointmentDate<:tommorrowsDate AND appointmentDate>:todaysDate order by appointmentDate asc")
    LiveData<List<PatientAppointment>> getAllTbAppointments(long todaysDate, long tommorrowsDate);

    @Query("select * from PatientAppointment where appointmentType = 1 and appointmentDate<:tommorrowsDate AND appointmentDate>:todaysDate order by appointmentDate asc")
    LiveData<List<PatientAppointment>> getAllCTCAppointments(long todaysDate, long tommorrowsDate);

    @Query("select * from PatientAppointment where patientID = :patientId")
    List<PatientAppointment> getThisPatientAppointments(String patientId);

    @Query("select * from PatientAppointment where patientID = :patientId and appointmentDate > Date(:today)")
    List<PatientAppointment> getRemainingAppointments(String patientId, String today);

    @Query("select * from PatientAppointment where patientID = :patientID and appointmentType = :type")
    List<PatientAppointment> getAppointmentsByTypeAndPatientID(int type, String patientID);

    @Query("select * from PatientAppointment where patientID = :patientID and appointmentType = :type and status = :appointmentStatus")
    List<PatientAppointment> getAppointmentsByTypeAndPatientIDAndStatus(int type, String patientID, String appointmentStatus);

    @Query("select * from PatientAppointment where encounterNumber = :encounterNumber and patientID = :patientID")
    List<PatientAppointment> getAppointmentByEncounterNumberAndPatientID(int encounterNumber, String patientID);

    @Query("select count(*) from PatientAppointment where appointmentDate between :from and :to")
    int getTotalAppointmentsByAppointmentDate(long from, long to);

    @Query("select count(*) from PatientAppointment inner join Patient on PatientAppointment.patientId = Patient.patientId " +
            "where status = :status " +
            "and Patient.gender = :gender " +
            "and appointmentDate between :from and :to")
    int getTotalAppointmentsByAppointmentDateStatusAndGender(long from, long to, int status, String gender);

    @Insert (onConflict = REPLACE)
    void addAppointment(PatientAppointment appointment);

    @Update
    void updateAppointment(PatientAppointment appointment);

    @Delete
    void deleteAppointment(PatientAppointment appointment);

    @Query("delete from PatientAppointment where patientID = :patientID")
    void deleteAppointmentByPatientID(String patientID);

}
