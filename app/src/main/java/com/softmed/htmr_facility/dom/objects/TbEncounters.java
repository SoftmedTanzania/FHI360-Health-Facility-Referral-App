package com.softmed.htmr_facility.dom.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by issy on 12/28/17.
 *
 * @issyzac issyzac.iz@gmail.com
 * On Project HFReferralApp
 */
@Entity
public class TbEncounters implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    @SerializedName("tbPatientId")
    private long tbPatientID;

    @NonNull
    @SerializedName("encounterNumber")
    private int encounterNumber;

    @SerializedName("makohozi")
    private String makohozi;

    @SerializedName("weight")
    private String weight;

    @SerializedName("appointmentId")
    private long appointmentId;

    @SerializedName("hasFinishedPreviousMonthMedication")
    private boolean hasFinishedPreviousMonthMedication;

    @SerializedName("medicationStatus")
    private boolean medicationStatus;

    @SerializedName("medicationDate")
    private long medicationDate;

    @SerializedName("scheduledDate")
    private long scheduledDate;

    @SerializedName("encounterYear")
    private int encounterYear;

    @SerializedName("localID")
    private String localID;


    @NonNull
    public long getTbPatientID() {
        return tbPatientID;
    }

    public void setTbPatientID(@NonNull long tbPatientID) {
        this.tbPatientID = tbPatientID;
    }

    public int getEncounterYear() {
        return encounterYear;
    }

    public void setEncounterYear(int encounterYear) {
        this.encounterYear = encounterYear;
    }

    public String getMakohozi() {
        return makohozi;
    }

    public void setMakohozi(String makohozi) {
        this.makohozi = makohozi;
    }

    public boolean isMedicationStatus() {
        return medicationStatus;
    }

    public void setMedicationStatus(boolean medicationStatus) {
        this.medicationStatus = medicationStatus;
    }

    public long getMedicationDate() {
        return medicationDate;
    }

    public void setMedicationDate(long medicationDate) {
        this.medicationDate = medicationDate;
    }

    public long getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(long scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public int getEncounterNumber() {
        return encounterNumber;
    }

    public void setEncounterNumber(@NonNull int encounterNumber) {
        this.encounterNumber = encounterNumber;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public boolean isHasFinishedPreviousMonthMedication() {
        return hasFinishedPreviousMonthMedication;
    }

    public void setHasFinishedPreviousMonthMedication(boolean hasFinishedPreviousMonthMedication) {
        this.hasFinishedPreviousMonthMedication = hasFinishedPreviousMonthMedication;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLocalID() {
        return localID;
    }

    public void setLocalID(String localID) {
        this.localID = localID;
    }
}
