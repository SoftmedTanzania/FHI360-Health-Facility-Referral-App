package com.softmed.htmr_facility_staging.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import com.softmed.htmr_facility_staging.utils.DateConverter;

/**
 * Created by issy on 1/2/18.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */

@Entity
public class PatientAppointment implements Serializable{

    @PrimaryKey
    @NonNull
    @SerializedName("appointment_id")
    private Long appointmentID;

    @SerializedName("healthFacilityPatientId")
    private String patientID;

    @SerializedName("appointmentDate")
    @TypeConverters(DateConverter.class)
    private long appointmentDate;

    /**
     * CTC Appointments = 1
     * TB Appointments = 2
     */
    @SerializedName("appointmentType")
    private int appointmentType;

    @SerializedName("isCancelled")
    private boolean cancelled;

    /**
     *  Status Values
     *  0 - Pending
     *  1  - Attended
     */
    @SerializedName("status")
    private int status;

    private int encounterNumber;

    @Ignore
    @SerializedName("cancelled")
    private boolean _cancelled;

    private String appointmentEncounterMonth;

    @NonNull
    public Long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(@NonNull Long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public long getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(long appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAppointmentEncounterMonth() {
        return appointmentEncounterMonth;
    }

    public void setAppointmentEncounterMonth(String appointmentEncounterMonth) {
        this.appointmentEncounterMonth = appointmentEncounterMonth;
    }

    public boolean is_cancelled() {
        return _cancelled;
    }

    public void set_cancelled(boolean _cancelled) {
        this._cancelled = _cancelled;
    }

    public int getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(int appointmentType) {
        this.appointmentType = appointmentType;
    }

    public int getEncounterNumber() {
        return encounterNumber;
    }

    public void setEncounterNumber(int encounterNumber) {
        this.encounterNumber = encounterNumber;
    }
}
